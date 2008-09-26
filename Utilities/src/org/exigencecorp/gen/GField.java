package org.exigencecorp.gen;

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
        return this;
    }

    public GField typeInPackage(String packageName, String simpleName, Object... args) {
        this.typeClassName = ToString.interpolate(simpleName, args);
        this.gclass.addImports(packageName + "." + this.typeClassName);
        return this;
    }

    public GField initialValue(String initialValue, Object... args) {
        this.initialValue = ToString.interpolate(initialValue, args);
        return this;
    }

    public GClass initialAnonymousClass() {
        GClass gc = new GClass(this.typeClassName).isAnonymous();
        this.initialAnonymousClass = gc;
        return gc;
    }

    public GField isStatic() {
        this.isStatic = true;
        return this;
    }

    public GField isFinal() {
        this.isFinal = true;
        return this;
    }

    public GField isPublic() {
        this.access = "public";
        return this;
    }

    public String toString() {
        return this.toCode();
    }

}
