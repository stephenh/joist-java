package joist.codegen.passes;

import java.util.ArrayList;
import java.util.List;

import joist.codegen.Codegen;
import joist.codegen.dtos.Entity;
import joist.codegen.dtos.ManyToOneProperty;
import joist.codegen.dtos.OneToManyProperty;
import joist.codegen.dtos.PrimitiveProperty;
import joist.domain.builders.AbstractBuilder;
import joist.sourcegen.Argument;
import joist.sourcegen.GClass;
import joist.sourcegen.GMethod;
import joist.util.Inflector;
import joist.util.MapToList;

import org.apache.commons.lang.StringUtils;

public class GenerateBuilderCodegenPass implements Pass {

  public void pass(Codegen codegen) {
    for (Entity entity : codegen.getEntities().values()) {
      if (entity.isCodeEntity()) {
        continue;
      }

      GClass builderCodegen = codegen.getOutputCodegenDirectory().getClass(entity.getFullBuilderCodegenClassName());
      builderCodegen.setAbstract();
      if (entity.isRoot()) {
        builderCodegen.baseClassName("AbstractBuilder<{}>", entity.getFullClassName());
        builderCodegen.addImports(AbstractBuilder.class);
      } else {
        builderCodegen.baseClassName(entity.getParentClassName() + "Builder");
      }
      builderCodegen.addAnnotation("@SuppressWarnings(\"all\")");

      this.constructor(builderCodegen, entity);
      this.primitiveProperties(builderCodegen, entity);
      this.manyToOneProperties(builderCodegen, entity);
      this.oneToManyProperties(builderCodegen, entity);
      this.overrideGet(builderCodegen, entity);
    }
  }

  private void constructor(GClass builderCodegen, Entity entity) {
    GMethod m = builderCodegen.getConstructor(entity.getFullClassName() + " instance");
    m.body.line("super(instance);");
  }

  private void overrideGet(GClass builderCodegen, Entity entity) {
    builderCodegen.getMethod("get").returnType(entity.getFullClassName()).body.line("return ({}) super.get();", entity.getFullClassName());
  }

  private void primitiveProperties(GClass c, Entity entity) {
    // first pass to get the types
    MapToList<String, String> perType = new MapToList<String, String>();
    for (PrimitiveProperty p : entity.getPrimitiveProperties()) {
      perType.get(p.getJavaType()).add(p.getVariableName());
    }

    for (PrimitiveProperty p : entity.getPrimitiveProperties()) {
      if (p.getVariableName().equals("version")) {
        continue;
      }
      // regular foo() getter
      this.addFluentGetter(c, p.getVariableName(), p.getJavaType());
      // regular foo(value) setter
      this.addFluentSetter(c, entity, p.getVariableName(), p.getVariableName(), p.getJavaType());
      // overload with(value) setter
      if (perType.get(p.getJavaType()).size() == 1) {
        this.addFluentSetter(c, entity, "with", p.getVariableName(), p.getJavaType());
      }
    }
  }

  private void oneToManyProperties(GClass c, Entity entity) {
    for (OneToManyProperty otom : entity.getOneToManyProperties()) {
      if (otom.isCollectionSkipped()) {
        continue;
      }
      if (otom.isOneToOne()) {
        // child() -> ChildBuilder
        GMethod m = c.getMethod(otom.getVariableNameSingular());
        m.returnType("{}Builder", otom.getTargetJavaType());
        m.body.line("if (get().get{}() == null) {", otom.getCapitalVariableNameSingular());
        m.body.line("_   return null;");
        m.body.line("}");
        m.body.line("return Builders.existing(get().get{}());", otom.getCapitalVariableNameSingular());
      } else {
        // childs() -> List<ChildBuilder>
        {
          GMethod m = c.getMethod(otom.getVariableName());
          m.returnType("List<{}Builder>", otom.getTargetJavaType());
          m.body.line("List<{}Builder> b = new ArrayList<{}Builder>();", otom.getTargetJavaType(), otom.getTargetJavaType());
          m.body.line("for ({} e : get().get{}()) {", otom.getTargetJavaType(), otom.getCapitalVariableName());
          m.body.line("_   b.add(Builders.existing(e));");
          m.body.line("}");
          m.body.line("return b;");
          c.addImports(List.class, ArrayList.class);
          c.addImports(otom.getManySide().getFullClassName());
        }

        // child(i) -> ChildBuilder
        {
          GMethod m = c.getMethod(StringUtils.uncapitalize(otom.getCapitalVariableNameSingular()), Argument.arg("int", "i"));
          m.returnType("{}Builder", otom.getTargetJavaType());
          m.body.line("return Builders.existing(get().get{}().get(i));", otom.getCapitalVariableName());
        }
      }
    }
  }

  private void manyToOneProperties(GClass c, Entity entity) {
    // first pass to get the types so we can add "with(Type)" if there is only 1 property of Type
    MapToList<String, String> perType = new MapToList<String, String>();
    for (ManyToOneProperty mtop : entity.getManyToOneProperties()) {
      perType.get(mtop.getJavaType()).add(mtop.getVariableName());
    }

    for (ManyToOneProperty mtop : entity.getManyToOneProperties()) {
      // regular foo() getter
      if (mtop.getOneSide().isCodeEntity()) {
        this.addFluentGetter(c, mtop.getVariableName(), mtop.getJavaType());
      } else {
        this.addFluentBuilderGetter(c, mtop.getVariableName(), mtop.getJavaType());
      }

      // regular foo(value) setter
      this.addFluentSetter(c, entity, mtop.getVariableName(), mtop.getVariableName(), mtop.getOneSide().getFullClassName());
      // overload with(value) setter
      if (perType.get(mtop.getJavaType()).size() == 1) {
        this.addFluentSetter(c, entity, "with", mtop.getVariableName(), mtop.getOneSide().getFullClassName());
      }

      if (!mtop.getOneSide().isCodeEntity()) {
        // regular foo(valueBuilder) setter
        this.addFluentBuilderSetter(c, entity, mtop.getVariableName(), mtop.getVariableName(), mtop.getOneSide().getBuilderClassName());
        // overload with(valueBuilder) setter
        if (perType.get(mtop.getJavaType()).size() == 1) {
          this.addFluentBuilderSetter(c, entity, "with", mtop.getVariableName(), mtop.getOneSide().getBuilderClassName());
        }
      }
    }
  }

  private void addFluentSetter(GClass builderCodegen, Entity entity, String methodName, String variableName, String javaType) {
    GMethod m = builderCodegen.getMethod(methodName, Argument.arg(javaType, variableName));
    m.returnType(entity.getBuilderClassName());
    m.body.line("get().set{}({});", Inflector.capitalize(variableName), variableName);
    m.body.line("return ({}) this;", entity.getBuilderClassName());
  }

  private void addFluentBuilderSetter(GClass builderCodegen, Entity entity, String methodName, String variableName, String javaType) {
    GMethod m = builderCodegen.getMethod(methodName, Argument.arg(javaType, variableName));
    m.returnType(entity.getBuilderClassName());
    m.body.line("get().set{}({}.get());", Inflector.capitalize(variableName), variableName);
    m.body.line("return ({}) this;", entity.getBuilderClassName());
  }

  private void addFluentGetter(GClass builderCodegen, String variableName, String javaType) {
    GMethod m = builderCodegen.getMethod(variableName);
    m.returnType(javaType);
    m.body.line("return get().get{}();", Inflector.capitalize(variableName));
  }

  private void addFluentBuilderGetter(GClass builderCodegen, String variableName, String javaType) {
    GMethod m = builderCodegen.getMethod(variableName);
    m.returnType(javaType + "Builder");
    m.body.line("if (get().get{}() == null) {", Inflector.capitalize(variableName));
    m.body.line("_   return null;");
    m.body.line("}");
    m.body.line("return Builders.existing(get().get{}());", Inflector.capitalize(variableName));
  }

}
