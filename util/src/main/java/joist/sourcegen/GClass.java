package joist.sourcegen;

import static joist.sourcegen.Argument.arg;
import static joist.util.Inflector.capitalize;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import joist.util.Copy;
import joist.util.Function1;
import joist.util.Interpolate;
import joist.util.Join;
import joist.util.StringBuilderr;

public class GClass {

  private static final Pattern classNameWithoutGenerics = Pattern.compile("(([a-z][a-zA-Z0-9_]*\\.)*)([A-Z][a-zA-Z0-9_]+)");

  public final StringBuilderr staticInitializer = new StringBuilderr();
  private final GDirectory directory;
  private final ParsedName name;
  private final List<GField> fields = new ArrayList<GField>();
  private final List<GMethod> methods = new ArrayList<GMethod>();
  private final List<GClass> innerClasses = new ArrayList<GClass>();
  private final Set<String> imports = new TreeSet<String>();
  private final List<GMethod> constructors = new ArrayList<GMethod>();
  private final List<String> enumValues = new ArrayList<String>();
  private final List<String> implementsInterfaces = new ArrayList<String>();
  private final List<String> annotations = new ArrayList<String>();
  private Access access = Access.PUBLIC;
  private boolean isAbstract = false;
  private boolean isInnerClass = false;
  private boolean isStaticInnerClass = false;
  private boolean isAnonymous = false;
  private boolean isEnum = false;
  protected boolean isInterface = false;
  private String baseClassName = null;
  private GClass outerClass;

  // this overload is purposefully kept for backwards compatibility
  public GClass(String fullClassName) {
    this(null, fullClassName);
  }

  public GClass(GDirectory directory, String fullClassName) {
    this.directory = directory;
    this.name = ParsedName.parse(fullClassName);
  }

  public GDirectory getDirectory() {
    return this.directory;
  }

  public GClass setEnum() {
    this.isEnum = true;
    return this;
  }

  public GClass setInterface() {
    this.isInterface = true;
    return this;
  }

  public GClass addEnumValue(String value, Object... args) {
    this.enumValues.add(Interpolate.string(value, args));
    return this;
  }

  public GClass addGetterSetter(String type, String name) {
    this.getField(name).type(type);
    this.getMethod("get{}", capitalize(name)).returnType(type).body.line("return {};", name);
    this.getMethod("set" + capitalize(name), arg(type, name)).body.line("this.{} = {};", name, name);
    return this;
  }

  public boolean isSameClass(String fullNameWithOrWithoutGenerics) {
    // ignore generics when considering whether it's the same class
    ParsedName otherName = ParsedName.parse(fullNameWithOrWithoutGenerics);
    return this.name.getFullName().equals(otherName.getFullName());
  }

  public GClass getInnerClass(String name, Object... args) {
    name = Interpolate.string(name, args);
    for (GClass gc : this.innerClasses) {
      if (gc.isSameClass(name)) {
        return gc;
      }
    }
    GClass gc = new GClass(this.directory, name);
    gc.isInnerClass = true;
    gc.isStaticInnerClass = true;
    gc.outerClass = this;
    this.innerClasses.add(gc);
    return gc;
  }

  public GClass notStatic() {
    this.isStaticInnerClass = false;
    return this;
  }

  public GMethod getConstructor(String... typeAndNames) {
    for (GMethod constructor : this.constructors) {
      if (constructor.hasSameArguments(typeAndNames)) {
        return constructor;
      }
    }
    GMethod constructor = new GMethod(this, "constructor");
    constructor.arguments(typeAndNames).constructorFor(this.getSimpleName());
    this.constructors.add(constructor);
    return constructor;
  }

  /** Takes arg0 + _args to differentiate from getConstructor(String... typeAndNames) */
  public GMethod getConstructor(Argument arg0, Argument... _args) {
    return this.getConstructor(Copy.list(arg0).with(_args));
  }

  public GMethod getConstructor(List<Argument> _args) {
    List<Argument> args = Copy.list(_args).map(new Function1<Argument, Argument>() {
      public Argument apply(Argument p1) {
        return p1.importIfPossible(GClass.this);
      }
    });
    for (GMethod cstr : this.constructors) {
      if (cstr.hasSameArguments(args)) {
        return cstr;
      }
    }
    GMethod cstr = new GMethod(this, "constructor").constructorFor(this.getSimpleName());
    cstr.arguments(args);
    this.constructors.add(cstr);
    return cstr;
  }

  public boolean hasMethod(String name) {
    for (GMethod method : this.methods) {
      if (method.getName().equals(name)) {
        return true;
      }
    }
    return false;
  }

  // this is weird, but you can call getMethod("methodFoo(String arg1)") and we'll try to do the right thing
  public GMethod getMethod(String name, Object... args) {
    name = Interpolate.string(name, args);
    if (name.indexOf('(') == -1) {
      for (GMethod method : this.methods) {
        if (method.getName().equals(name)) {
          return method;
        }
      }
      GMethod method = new GMethod(this, name);
      this.methods.add(method);
      return method;
    } else {
      String typesAndNames = this.stripAndImportPackageIfPossible(name.substring(name.indexOf('(') + 1, name.length() - 1));
      name = name.substring(0, name.indexOf('('));
      for (GMethod method : this.methods) {
        if (method.getName().equals(name) && method.hasSameArguments(typesAndNames)) {
          return method;
        }
      }
      GMethod method = new GMethod(this, name);
      method.arguments(typesAndNames);
      this.methods.add(method);
      return method;
    }
  }

  // use arg0 + _args to differentiate between this and getMethod(String, Object...) with only 1 arg
  public GMethod getMethod(String name, Argument arg0, Argument... _args) {
    return this.getMethod(name, Copy.list(arg0).with(_args));
  }

  public GMethod getMethod(String name, List<Argument> _args) {
    List<Argument> args = Copy.list(_args).map(new Function1<Argument, Argument>() {
      public Argument apply(Argument p1) {
        return p1.importIfPossible(GClass.this);
      }
    });
    for (GMethod method : this.methods) {
      if (method.getName().equals(name) && method.hasSameArguments(args)) {
        return method;
      }
    }
    GMethod method = new GMethod(this, name);
    method.arguments(args);
    this.methods.add(method);
    return method;
  }

  public GField getField(String name, Object... args) {
    name = Interpolate.string(name, args);
    for (GField field : this.fields) {
      if (field.getName().equals(name)) {
        return field;
      }
    }
    GField field = new GField(this, name);
    this.fields.add(field);
    return field;
  }

  public String toCode() {
    StringBuilderr sb = new StringBuilderr();
    if (this.name.packageName != null) {
      sb.line("package {};", this.name.packageName);
      sb.line();
    }

    if (this.isAnonymous) {
      sb.line("new {}() {", this.name.simpleNameWithGenerics);
    } else {
      // need to calc this before outputing the imports
      if (this.baseClassName != null) {
        this.stripAndImportPackageIfPossible(this.baseClassName);
      }

      if (this.imports.size() > 0) {
        for (String importClassName : this.imports) {
          sb.line("import {};", importClassName);
        }
        sb.line();
      }

      for (String annotation : this.annotations) {
        sb.line(annotation);
      }

      sb.append(this.access.asPrefix());
      if (this.isStaticInnerClass) {
        sb.append("static ");
      }
      if (this.isAbstract) {
        sb.append("abstract ");
      }
      if (this.isEnum) {
        sb.append("enum ");
      } else if (this.isInterface) {
        sb.append("interface ");
      } else {
        sb.append("class ");
      }
      sb.append(this.name.simpleNameWithGenerics);
      sb.append(" ");
      if (this.baseClassName != null) {
        sb.append("extends {} ", this.stripAndImportPackageIfPossible(this.baseClassName));
      }
      if (this.implementsInterfaces.size() > 0) {
        sb.append("implements {} ", Join.commaSpace(this.implementsInterfaces));
      }
      sb.line("{");
    }

    if (this.isEnum && this.enumValues.size() > 0) {
      boolean hasMore = this.fields.size() > 0 || this.methods.size() > 0 || this.constructors.size() > 0;
      this.lineIfNotAnonymousOrInner(sb);
      for (Iterator<String> i = this.enumValues.iterator(); i.hasNext();) {
        sb.append(1, i.next());
        sb.append(i.hasNext() ? "," : hasMore ? ";" : "");
        sb.line();
      }
    }

    if (this.fields.size() > 0) {
      this.lineIfNotAnonymousOrInner(sb);
      for (GField field : this.fields) {
        sb.append(1, field.toCode());
      }
    }

    if (this.staticInitializer.toString().length() > 0) {
      sb.line();
      sb.line(1, "static {");
      sb.append(2, this.staticInitializer.toString());
      sb.lineIfNeeded();
      sb.line(1, "}");
    }

    for (GMethod constructor : this.constructors) {
      this.lineIfNotAnonymousOrInner(sb);
      sb.append(1, constructor.toCode());
    }

    if (this.methods.size() > 0) {
      for (GMethod method : this.methods) {
        this.lineIfNotAnonymousOrInner(sb);
        sb.append(1, method.toCode());
      }
    }

    if (this.innerClasses.size() > 0) {
      for (GClass gc : this.innerClasses) {
        sb.line();
        sb.append(1, gc.toCode());
      }
    }

    this.lineIfNotAnonymousOrInner(sb);
    sb.line("}");
    return sb.toString();
  }

  public GClass setAbstract() {
    this.isAbstract = true;
    return this;
  }

  public GClass setPackagePrivate() {
    this.access = Access.PACKAGE;
    return this;
  }

  public GClass setPrivate() {
    this.access = Access.PRIVATE;
    return this;
  }

  public GClass setAccess(Access access) {
    this.access = access;
    return this;
  }

  public GClass setAnonymous() {
    this.isAnonymous = true;
    return this;
  }

  public GClass addImports(Class<?>... types) {
    if (this.outerClass != null) {
      this.outerClass.addImports(types);
      return this;
    }
    for (Class<?> type : types) {
      this.addImports(type.getName());
    }
    return this;
  }

  public GClass addImports(String... importClassNames) {
    if (this.outerClass != null) {
      this.outerClass.addImports(importClassNames);
      return this;
    }
    for (String importClassName : importClassNames) {
      ParsedName name = ParsedName.parse(importClassName);
      String packageName = name.packageName;
      if (packageName == null || packageName.equals(this.name.packageName) || "java.lang".equals(packageName)) {
        continue;
      }
      this.imports.add(name.packageName + "." + name.simpleName);
    }
    return this;
  }

  public String getBaseClassName() {
    return this.baseClassName;
  }

  public GClass baseClass(Class<?> type) {
    return this.baseClassName(type.getName());
  }

  public GClass baseClassName(String baseClassName, Object... args) {
    String _baseClassName = Interpolate.string(baseClassName, args);
    if (!_baseClassName.contains(".")) {
      this.baseClassName = this.getPackageName() + "." + _baseClassName;
    } else {
      this.baseClassName = _baseClassName;
    }
    return this;
  }

  /** @return the short name if this was importable or else the full name if a name collision would have happened */
  public String stripAndImportPackageIfPossible(String fullClassName) {
    Matcher m = GClass.classNameWithoutGenerics.matcher(fullClassName);
    while (m.find()) {
      String packageName = m.group(1).replaceAll("\\.$", "");
      String simpleName = m.group(3);
      if (!"".equals(packageName) && !this.isImportAlreadyTakenByDifferentPackage(packageName, simpleName)) {
        fullClassName = fullClassName.replaceFirst(packageName + "\\." + simpleName, simpleName);
        this.addImports(packageName + "." + simpleName);
      }
    }
    return fullClassName;
  }

  private boolean isImportAlreadyTakenByDifferentPackage(String packageName, String simpleName) {
    for (String existingImport : this.imports) {
      if (!existingImport.equals(packageName + "." + simpleName) && existingImport.endsWith("." + simpleName)) {
        return true;
      }
    }
    if (simpleName.equals(this.name.simpleName) && !packageName.equals(this.name.packageName)) {
      return true;
    }
    return false;
  }

  private void lineIfNotAnonymousOrInner(StringBuilderr sb) {
    if (!this.isAnonymous && !this.isInnerClass) {
      sb.line();
    }
  }

  public List<GMethod> getConstructors() {
    return this.constructors;
  }

  public String toString() {
    return this.getFullName();
  }

  /** @return the package name */
  public String getPackageName() {
    return this.name.packageName;
  }

  /** @return the simple name without generics */
  public String getSimpleName() {
    return this.name.simpleName;
  }

  /** @return the package + simple name without generics */
  public String getFullName() {
    return this.name.getFullName();
  }

  /** @return the relative file name, e.g. {@code com/foo/Bar.java}. */
  public String getFileName() {
    return this.getFullName().replace(".", File.separator) + ".java";
  }

  public GClass implementsInterface(Class<?> interfaceClass) {
    this.implementsInterfaces.add(this.stripAndImportPackageIfPossible(interfaceClass.getName()));
    return this;
  }

  public GClass implementsInterface(String interfaceFullName, Object... args) {
    interfaceFullName = Interpolate.string(interfaceFullName, args);
    this.implementsInterfaces.add(this.stripAndImportPackageIfPossible(interfaceFullName));
    return this;
  }

  public GClass addAnnotation(String annotation, Object... args) {
    this.annotations.add(Interpolate.string(annotation, args));
    return this;
  }

  public GClass addEquals() {
    return this.addEquals(this.getFieldNames());
  }

  public GClass addEquals(String... fieldNames) {
    return this.addEquals(Arrays.asList(fieldNames));
  }

  public GClass addEquals(Collection<String> fieldNames) {
    GMethod equals = this.getMethod("equals", arg("Object", "other")).returnType("boolean").addAnnotation("@Override");
    if (this.name.hasGenerics()) {
      equals.addAnnotation("@SuppressWarnings(\"unchecked\")");
    }
    equals.body.line("if (other != null && other instanceof {}) {", this.name.simpleName);
    if (this.fields.size() == 0) {
      equals.body.line("_   return true;");
    } else {
      equals.body.line("_   final {} o = ({}) other;", this.name.simpleNameWithGenerics, this.name.simpleNameWithGenerics);
      equals.body.line("_   return true"); // leave open
      for (String fieldName : fieldNames) {
        GField field = this.findField(fieldName);
        if (Primitives.isPrimitive(field.getTypeClassName())) {
          equals.body.line("_   _   && o.{} == this.{}", field.getName(), field.getName());
        } else if (field.getTypeClassName().endsWith("[]")) {
          equals.body.line("_   _   && java.util.Arrays.deepEquals(o.{}, this.{})", field.getName(), field.getName());
        } else {
          equals.body.line(
            "_   _   && ((o.{} == null && this.{} == null) || (o.{} != null && o.{}.equals(this.{})))",
            field.getName(),
            field.getName(),
            field.getName(),
            field.getName(),
            field.getName());
        }
      }
      equals.body.line("_   ;"); // finally close
    }
    equals.body.line("}");
    equals.body.line("return false;");
    return this;
  }

  public GClass addHashCode() {
    return this.addHashCode(this.getFieldNames());
  }

  public GClass addHashCode(String... fieldNames) {
    return this.addHashCode(Arrays.asList(fieldNames));
  }

  public GClass addHashCode(Collection<String> fieldNames) {
    GMethod hashCode = this.getMethod("hashCode").returnType("int").addAnnotation("@Override");
    hashCode.body.line("int hashCode = 23;");
    hashCode.body.line("hashCode = (hashCode * 37) + getClass().hashCode();");
    for (String fieldName : fieldNames) {
      GField field = this.findField(fieldName);
      String prefix = "hashCode = (hashCode * 37) + ";
      if (Primitives.isPrimitive(field.getTypeClassName())) {
        hashCode.body.line(prefix + "new {}({}).hashCode();", Primitives.getWrapper(field.getTypeClassName()), field.getName());
      } else if (field.getTypeClassName().endsWith("[]")) {
        hashCode.body.line(prefix + "java.util.Arrays.deepHashCode({});", field.getName());
      } else {
        hashCode.body.line(prefix + "({} == null ? 1 : {}.hashCode());", field.getName(), field.getName());
      }
    }
    hashCode.body.line("return hashCode;");
    return this;
  }

  public GClass addToString() {
    return this.addToString(this.getFieldNames());
  }

  public GClass addToString(String... fieldNames) {
    return this.addToString(Arrays.asList(fieldNames));
  }

  public GClass addToString(Collection<String> fieldNames) {
    GMethod tos = this.getMethod("toString").returnType("String").addAnnotation("@Override");
    tos.body.line("return \"{}[\"", this.getSimpleName());
    int i = 0;
    for (String fieldName : fieldNames) {
      GField field = this.findField(fieldName);
      if (field.getTypeClassName().endsWith("[]")) {
        tos.body.line("_   + java.util.Arrays.toString({})", field.getName());
      } else {
        tos.body.line("_   + {}", field.getName());
      }
      if (++i < fieldNames.size()) {
        tos.body.line("_    + \", \"");
      }
    }
    tos.body.line("_    + \"]\";");
    return this;
  }

  private GField findField(String name) {
    for (GField field : this.fields) {
      if (field.getName().equals(name)) {
        return field;
      }
    }
    if (this.directory != null) {
      String currentBaseName = this.baseClassName;
      while (currentBaseName != null) {
        GClass currentBase = this.directory.getClass(currentBaseName);
        for (GField field : currentBase.fields) {
          if (field.getName().equals(name)) {
            return field;
          }
        }
        currentBaseName = currentBase.baseClassName;
      }
    }
    throw new IllegalArgumentException("Could not find field " + name);
  }

  private List<String> getFieldNames() {
    List<String> fieldNames = new ArrayList<String>();
    for (GField field : this.fields) {
      fieldNames.add(field.getName());
    }
    return fieldNames;
  }

}
