package joist.codegen.passes;

import joist.codegen.Codegen;
import joist.codegen.dtos.Entity;
import joist.sourcegen.GClass;
import joist.sourcegen.GMethod;

public class GenerateBuildersClassPass implements Pass {

  public void pass(Codegen codegen) {
    GClass builders = codegen.getOutputSourceDirectory().getClass(codegen.getConfig().getBuildersPackage() + ".Builders");

    for (Entity entity : codegen.getEntities().values()) {
      if (entity.isCodeEntity()) {
        continue;
      }

      GMethod m = builders.getMethod("a" + entity.getClassName()).returnType(entity.getBuilderClassName()).setStatic();
      m.body.line("return new {}(new {}());", entity.getBuilderClassName(), entity.getClassName());
      builders.addImports(entity.getFullClassName());
    }
  }
}
