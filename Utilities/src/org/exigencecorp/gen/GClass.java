package org.exigencecorp.gen;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.lang.StringUtils;
import org.exigencecorp.util.Interpolate;
import org.exigencecorp.util.Join;
import org.exigencecorp.util.StringBuilderr;

public class GClass {

    public final StringBuilderr staticInitializer = new StringBuilderr();
    private final String packageName;
    private final String shortName;
    private final List<GField> fields = new ArrayList<GField>();
    private final List<GMethod> methods = new ArrayList<GMethod>();
    private final List<GClass> innerClasses = new ArrayList<GClass>();
    private final Set<String> imports = new TreeSet<String>();
    private final List<GMethod> constructors = new ArrayList<GMethod>();
    private final List<String> enumValues = new ArrayList<String>();
    private final List<String> implementsInterfaces = new ArrayList<String>();
    private String access = "public ";
    private boolean isAbstract = false;
    private boolean isStaticInnerClass = false;
    private boolean isAnonymous = false;
    private boolean isEnum = false;
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

    public GClass setEnum() {
        this.isEnum = true;
        return this;
    }

    public GClass addEnumValue(String value, Object... args) {
        this.enumValues.add(Interpolate.string(value, args));
        return this;
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

    public GClass getInnerClass(String name, Object... args) {
        name = Interpolate.string(name, args);
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

    public GClass getNonStaticInnerClass(String name, Object... args) {
        GClass gc = this.getInnerClass(name, args);
        gc.isStaticInnerClass = false;
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

    public boolean hasMethod(String name) {
        for (GMethod method : this.methods) {
            if (method.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

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
            String typesAndNames = name.substring(name.indexOf('(') + 1, name.length() - 1);
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

            sb.append(this.access);
            if (this.isStaticInnerClass) {
                sb.append("static ");
            }
            if (this.isAbstract) {
                sb.append("abstract ");
            }
            if (this.isEnum) {
                sb.append("enum ");
            } else {
                sb.append("class ");
            }
            sb.append(this.shortName);
            sb.append(" ");
            if (this.baseClassName != null) {
                sb.append("extends {} ", this.baseClassName);
            }
            if (this.implementsInterfaces.size() > 0) {
                sb.append("implements {} ", Join.commaSpace(this.implementsInterfaces));
            }
            sb.line("{");
        }

        if (this.staticInitializer.toString().length() > 0) {
            sb.line();
            sb.line(1, "static {");
            sb.append(2, this.staticInitializer.toString());
            sb.lineIfNeeded();
            sb.line(1, "}");
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
        this.access = "";
        return this;
    }

    public GClass setAnonymous() {
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
            this.imports.add(importClassName.replaceAll("<.+>", "")); // prune generics
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
        this.baseClassName = this.importAndStripPackage(Interpolate.string(baseClassName, args));
        return this;
    }

    private String importAndStripPackage(String fullClassName) {
        if (fullClassName.indexOf(".") > -1) {
            String simpleClassName = StringUtils.substringAfterLast(fullClassName, ".");
            String packageName = StringUtils.remove(fullClassName, "." + simpleClassName);
            if (!packageName.equals(this.getPackageName())) {
                this.addImports(fullClassName);
            }
            return simpleClassName;
        }
        return fullClassName;
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

    public GClass implementsInterface(Class<?> interfaceClass) {
        this.implementsInterfaces.add(interfaceClass.getName());
        return this;
    }

}
