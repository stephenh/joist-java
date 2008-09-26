package org.exigencecorp.gen;

import java.util.ArrayList;
import java.util.List;

import org.exigencecorp.util.Join;
import org.exigencecorp.util.StringBuilderr;
import org.exigencecorp.util.ToString;

public class GMethod {

    private final String name;
    private final List<String> arguments = new ArrayList<String>();
    public final StringBuilderr body = new StringBuilderr();
    private String returnClassName = "void";
    private String constructorFor = null;

    public GMethod(GClass gclass, String methodName) {
        this.name = methodName;
    }

    public GMethod returnType(Class<?> type) {
        return this.returnType(type.getName());
    }

    public GMethod returnType(String returnClassName, Object... args) {
        this.returnClassName = ToString.interpolate(returnClassName, args);
        return this;
    }

    public GMethod argument(String typeClassName, String name) {
        this.arguments.add(typeClassName + " " + name);
        return this;
    }

    public GMethod arguments(String... typeAndNames) {
        for (String typeAndName : typeAndNames) {
            this.arguments.add(typeAndName);
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
        if (this.constructorFor != null) {
            sb.line(0, "public {}({}) {", this.constructorFor, Join.commaSpace(this.arguments));
        } else {
            sb.line(0, "public {} {}({}) {", this.returnClassName, this.getName(), Join.commaSpace(this.arguments));
        }
        sb.append(1, this.body.toString());
        sb.lineIfNeeded(); // The body may or may not have a trailing new line on it
        sb.line(0, "}");
        return sb.toString();
    }

    public String getName() {
        return this.name;
    }

    public boolean hasSameArguments(String... typeAndNames) {
        return Join.comma(typeAndNames).equals(Join.comma(this.arguments));
    }

}
