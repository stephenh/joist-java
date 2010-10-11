package joist.sourcegen;

import java.util.ArrayList;
import java.util.List;

import joist.util.Interpolate;
import joist.util.Join;
import joist.util.StringBuilderr;

public class GMethod {

  public final StringBuilderr body = new StringBuilderr();
  private final GClass gclass;
  private final String name;
  private final List<Argument> arguments = new ArrayList<Argument>();
  private final List<String> annotations = new ArrayList<String>();
  private final List<String> exceptions = new ArrayList<String>();
  private String returnClassName = "void";
  private String constructorFor = null;
  private Access access = Access.PUBLIC;
  private boolean isStatic;
  private String typeParameters = null;

  public GMethod(GClass gclass, String methodName) {
    this.gclass = gclass;
    this.name = methodName;
  }

  public GMethod returnType(Class<?> type) {
    return this.returnType(type.getName());
  }

  public GMethod returnType(String returnClassName, Object... args) {
    this.returnClassName = this.gclass.stripAndImportPackageIfPossible(Interpolate.string(returnClassName, args));
    return this;
  }

  public GMethod argument(String typeClassName, String name) {
    typeClassName = this.gclass.stripAndImportPackageIfPossible(typeClassName);
    this.arguments.add(new Argument(typeClassName, name));
    return this;
  }

  /** @param typeAndNames could be either "Foo f, Bar b" or "Foo f", "Bar b" */
  public GMethod arguments(String... typeAndNames) {
    String line = Join.commaSpace(typeAndNames);
    line = this.gclass.stripAndImportPackageIfPossible(line);
    this.arguments.addAll(Argument.split(line));
    return this;
  }

  public GMethod arguments(List<Argument> args) {
    this.arguments.addAll(args);
    return this;
  }

  public GMethod constructorFor(String shortClassName) {
    this.constructorFor = shortClassName;
    return this;
  }

  public void setBody(String body) {
    this.body.append(body.replace("'", "\""));
  }

  public String toCode() {
    StringBuilderr sb = new StringBuilderr();

    for (String annotation : this.annotations) {
      sb.line(annotation);
    }

    sb.append(this.access.asPrefix());
    if (this.isStatic) {
      sb.append("static ");
    }
    if (this.typeParameters != null) {
      sb.append("<{}> ", this.typeParameters);
    }
    if (this.constructorFor != null) {
      sb.append(this.constructorFor);
    } else {
      sb.append("{} {}", this.returnClassName, this.getName());
    }
    sb.append("({})", Join.commaSpace(this.arguments));
    if (this.exceptions.size() > 0) {
      sb.append(" throws ");
      List<String> exceptionTypes = new ArrayList<String>();
      for (String exception : this.exceptions) {
        exceptionTypes.add(this.gclass.stripAndImportPackageIfPossible(exception));
      }
      sb.append(Join.commaSpace(exceptionTypes));
    }
    if (this.gclass.isInterface) {
      sb.line(";");
    } else {
      sb.line(" {");
      sb.append(1, this.body.toString());
      sb.lineIfNeeded(); // The body may or may not have a trailing new line on it
      sb.line(0, "}");
    }
    return sb.toString();
  }

  public String getName() {
    return this.name;
  }

  public GMethod setPrivate() {
    this.access = Access.PRIVATE;
    return this;
  }

  public GMethod setProtected() {
    this.access = Access.PROTECTED;
    return this;
  }

  public GMethod setAccess(Access access) {
    this.access = access;
    return this;
  }

  public GMethod setStatic() {
    this.isStatic = true;
    return this;
  }

  /** @return if we have the same arguments (based on types, not named) */
  public boolean hasSameArguments(String... typeAndNames) {
    return this.hasSameArguments(Argument.split(typeAndNames));
  }

  /** @return if we have the same arguments (based on types, not named) */
  public boolean hasSameArguments(List<Argument> other) {
    if (other.size() != this.arguments.size()) {
      return false;
    }
    for (int i = 0; i < other.size(); i++) {
      if (!(other.get(i).type.equals(this.arguments.get(i).type))) {
        return false;
      }
    }
    return true;
  }

  public GMethod addAnnotation(String annotation, Object... args) {
    this.annotations.add(Interpolate.string(annotation, args));
    return this;
  }

  public GMethod addThrows(String exception) {
    this.exceptions.add(exception);
    return this;
  }

  /** @param typeParameters e.g. <code>T, U</code> **/
  public GMethod typeParameters(String typeParameters) {
    this.typeParameters = typeParameters;
    return this;
  }

}
