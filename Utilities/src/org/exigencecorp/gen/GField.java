package org.exigencecorp.gen;

import org.apache.commons.lang.StringUtils;
import org.exigencecorp.util.StringBuilderr;
import org.exigencecorp.util.ToString;

public class GField {

    private final GClass gclass;
    private final String name;
    private String typeClassName;
    private String initialValue;
    private GClass initialAnonymousClass;
    private String access = "private";
    private boolean isStatic;
    private boolean isFinal;

    public GField(GClass gclass, String name) {
        this.gclass = gclass;
        this.name = name;
    }

    public String toCode() {
        StringBuilderr s = new StringBuilderr();
        s.append(this.access);
        if (this.isStatic) {
            s.append(" static");
        }
        if (this.isFinal) {
            s.append(" final");
        }
        s.append(" {}", this.typeClassName);
        s.append(" {}", this.name);
        if (this.initialValue != null) {
            s.append(" = {}", this.initialValue);
        }
        if (this.initialAnonymousClass != null) {
            s.append(" = {}", this.initialAnonymousClass.toCode());
            s.stripTrailingNewLine();
        }
        s.line(";");
        return s.toString();
    }

    public String getName() {
        return this.name;
    }

    public GField type(Class<?> type) {
        return this.type(type.getName());
    }

    public GField type(String fullClassName, Object... args) {
        this.typeClassName = ToString.interpolate(fullClassName, args);
        if (this.typeClassName.startsWith("java.lang.") && this.typeClassName.lastIndexOf('.') == 9) {

            this.typeClassName = this.typeClassName.substring("java.lang.".length());
        }
        return this;
    }

    public GField typeInPackage(String packageName, String simpleName, Object... args) {
        this.type(simpleName, args);
        this.typeClassName = ToString.interpolate(simpleName, args);
        this.gclass.addImports(packageName + "." + this.typeClassName);
        return this;
    }

    public GField initialValue(String initialValue, Object... args) {
        this.initialValue = ToString.interpolate(initialValue, args);
        return this;
    }

    public GClass initialAnonymousClass() {
        GClass gc = new GClass(this.typeClassName).setAnonymous();
        this.initialAnonymousClass = gc;
        return gc;
    }

    public GField setStatic() {
        this.isStatic = true;
        return this;
    }

    public GField setFinal() {
        this.isFinal = true;
        return this;
    }

    public GField setPublic() {
        this.access = "public";
        return this;
    }

    public String toString() {
        return this.toCode();
    }

    public GMethod makeGetter() {
        GMethod getter = this.gclass.getMethod("get" + StringUtils.capitalize(this.name));
        getter.returnType(this.typeClassName);
        // getter.body.line("this.{} = {};", this.name, this.name);
        getter.body.line("return this.{};", this.name);
        return getter;
    }

}
