package org.exigencecorp.gen;

import java.util.ArrayList;
import java.util.List;

import org.exigencecorp.util.Interpolate;
import org.exigencecorp.util.Join;
import org.exigencecorp.util.StringBuilderr;

public class GMethod {

    public final StringBuilderr body = new StringBuilderr();
    private final GClass gclass;
    private final String name;
    private final List<String> arguments = new ArrayList<String>();
    private final List<String> annotations = new ArrayList<String>();
    private final List<String> exceptions = new ArrayList<String>();
    private String returnClassName = "void";
    private String constructorFor = null;
    private String access = "public ";
    private boolean isStatic;

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
        this.arguments.add(typeClassName + " " + name);
        return this;
    }

    public GMethod arguments(String... typeAndNames) {
        for (String typeAndName : typeAndNames) {
            String[] parts = typeAndName.split(" ");
            this.argument(parts[0], parts[1]);
        }
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

        sb.append(this.access);
        if (this.isStatic) {
            sb.append("static ");
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
        sb.line(" {");
        sb.append(1, this.body.toString());
        sb.lineIfNeeded(); // The body may or may not have a trailing new line on it
        sb.line(0, "}");
        return sb.toString();
    }

    public String getName() {
        return this.name;
    }

    public GMethod setPrivate() {
        this.access = "private ";
        return this;
    }

    public GMethod setProtected() {
        this.access = "protected ";
        return this;
    }

    public GMethod setStatic() {
        this.isStatic = true;
        return this;
    }

    public boolean hasSameArguments(String... typeAndNames) {
        return Join.comma(typeAndNames).equals(Join.comma(this.arguments));
    }

    public GMethod addAnnotation(String annotation) {
        this.annotations.add(annotation);
        return this;
    }

    public GMethod addThrows(String exception) {
        this.exceptions.add(exception);
        return this;
    }

}
