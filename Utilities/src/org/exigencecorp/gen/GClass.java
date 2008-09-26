package org.exigencecorp.gen;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.lang.StringUtils;
import org.exigencecorp.util.StringBuilderr;
import org.exigencecorp.util.ToString;

public class GClass {

    private final String packageName;
    private final String shortName;
    private final List<GField> fields = new ArrayList<GField>();
    private final List<GMethod> methods = new ArrayList<GMethod>();
    private final List<GClass> innerClasses = new ArrayList<GClass>();
    private final Set<String> imports = new TreeSet<String>();
    private final List<GMethod> constructors = new ArrayList<GMethod>();
    private boolean isAbstract = false;
    private boolean isStaticInnerClass = false;
    private boolean isAnonymous = false;
    private String baseClassName = null;

    public GClass(String fullClassName) {
        int firstPeriod = fullClassName.indexOf('.');
        int firstBracket = fullClassName.indexOf('<');
        if (firstPeriod != -1 && (firstPeriod <= firstBracket || firstBracket == -1)) {
            this.packageName = StringUtils.substringBeforeLast(fullClassName, ".");
            this.shortName = StringUtils.substringAfterLast(fullClassName, ".");
        } else {
            this.packageName = null;
            this.shortName = fullClassName;
        }
    }

    public String getFullClassName() {
        if (this.packageName == null) {
            return this.shortName;
        }
        return this.packageName + "." + this.shortName;
    }

    public String getPackageName() {
        return this.packageName;
    }

    public GClass getInnerClass(String name) {
        for (GClass gc : this.innerClasses) {
            if (gc.shortName.equals(name)) {
                return gc;
            }
        }
        GClass gc = new GClass(name);
        gc.isStaticInnerClass = true;
        this.innerClasses.add(gc);
        return gc;
    }

    public GMethod getConstructor(String... typeAndNames) {
        for (GMethod constructor : this.constructors) {
            if (constructor.hasSameArguments(typeAndNames)) {
                return constructor;
            }
        }
        GMethod constructor = new GMethod(this, "constructor");
        constructor.arguments(typeAndNames).constructorFor(this.shortName);
        this.constructors.add(constructor);
        return constructor;
    }

    public GMethod getMethod(String name, Object... args) {
        name = ToString.interpolate(name, args);
        for (GMethod method : this.methods) {
            if (method.getName().equals(name)) {
                return method;
            }
        }
        GMethod method = new GMethod(this, name);
        this.methods.add(method);
        return method;
    }

    public GField getField(String name, Object... args) {
        name = ToString.interpolate(name, args);
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
        if (this.packageName != null) {
            sb.line("package {};", this.packageName);
            sb.line();
        }

        if (this.isAnonymous) {
            sb.line("new {}() {", this.shortName);
        } else {
            if (this.imports.size() > 0) {
                for (String importClassName : this.imports) {
                    sb.line("import {};", importClassName);
                }
                sb.line();
            }

            sb.append("public ");
            if (this.isStaticInnerClass) {
                sb.append("static ");
            }
            if (this.isAbstract) {
                sb.append("abstract ");
            }
            sb.append("class {} ", this.shortName);
            if (this.baseClassName != null) {
                sb.append("extends {} ", this.baseClassName);
            }
            sb.line("{");
        }

        if (this.fields.size() > 0) {
            this.lineIfNotAnonymousOrInner(sb);
            for (GField field : this.fields) {
                sb.append(1, field.toCode());
            }
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

    public GClass isAbstract() {
        this.isAbstract = true;
        return this;
    }

    public GClass isAnonymous() {
        this.isAnonymous = true;
        return this;
    }

    public GClass addImports(Class<?>... types) {
        for (Class<?> type : types) {
            this.imports.add(type.getName());
        }
        return this;
    }

    public GClass addImports(String... importClassNames) {
        for (String importClassName : importClassNames) {
            this.imports.add(importClassName);
        }
        return this;
    }

    public String getBaseClassName() {
        return this.baseClassName;
    }

    public GClass baseClass(Class<?> type) {
        this.baseClassName = type.getName();
        return this;
    }

    public GClass baseClassName(String baseClassName, Object... args) {
        baseClassName = ToString.interpolate(baseClassName, args);
        if (baseClassName.indexOf(".") > -1) {
            this.addImports(baseClassName);
            baseClassName = StringUtils.substringAfterLast(baseClassName, ".");
        }
        this.baseClassName = baseClassName;
        return this;
    }

    private void lineIfNotAnonymousOrInner(StringBuilderr sb) {
        if (!this.isAnonymous && !this.isStaticInnerClass) {
            sb.line();
        }
    }

    public List<GMethod> getConstructors() {
        return this.constructors;
    }

    public String toString() {
        return this.getFullClassName();
    }

}
