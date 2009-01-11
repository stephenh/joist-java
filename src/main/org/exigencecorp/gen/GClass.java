package org.exigencecorp.gen;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    private boolean isInnerClass = false;
    private boolean isStaticInnerClass = false;
    private boolean isAnonymous = false;
    private boolean isEnum = false;
    private String baseClassName = null;
    private GClass outerClass;
    private static final Pattern classNameWithoutGenerics = Pattern.compile("((\\w+\\.)*)(\\w+)");

    public GClass(String fullClassName) {
        int firstPeriod = fullClassName.indexOf('.');
        int firstBracket = fullClassName.indexOf('<');
        if (firstPeriod != -1 && (firstPeriod <= firstBracket || firstBracket == -1)) {
            if (firstBracket == -1) {
                firstBracket = fullClassName.length();
            }
            this.packageName = StringUtils.substringBeforeLast(fullClassName.substring(0, firstBracket), ".");
            this.shortName = StringUtils.removeStart(fullClassName, this.packageName + ".");
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

    public String getSimpleClassName() {
        return this.shortName;
    }

    public String getSimpleClassNameWithoutGeneric() {
        int firstBracket = this.shortName.indexOf('<');
        if (firstBracket != -1) {
            return this.shortName.substring(0, firstBracket);
        }
        return this.shortName;
    }

    public String getFullClassName() {
        if (this.packageName == null) {
            return this.shortName;
        }
        return this.packageName + "." + this.shortName;
    }

    public String getFullClassNameWithoutGeneric() {
        if (this.packageName == null) {
            return this.getSimpleClassNameWithoutGeneric();
        }
        return this.packageName + "." + this.getSimpleClassNameWithoutGeneric();
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
        constructor.arguments(typeAndNames).constructorFor(this.getSimpleClassNameWithoutGeneric());
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
        if (this.outerClass != null) {
            this.outerClass.addImports(types);
            return this;
        }
        for (Class<?> type : types) {
            this.imports.add(type.getName());
        }
        return this;
    }

    public GClass addImports(String... importClassNames) {
        if (this.outerClass != null) {
            this.outerClass.addImports(importClassNames);
            return this;
        }
        for (String importClassName : importClassNames) {
            importClassName = importClassName.replaceAll("<.+>", ""); // prune generics
            String simpleClassName = StringUtils.substringAfterLast(importClassName, ".");
            String packageName = StringUtils.remove(importClassName, "." + simpleClassName);
            if (StringUtils.equals(this.packageName, packageName) || "java.lang".equals(packageName)) {
                continue; // same package
            }
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
        this.baseClassName = this.stripAndImportPackageIfPossible(Interpolate.string(baseClassName, args));
        return this;
    }

    /** @return the short name if this was importable or else the full name if a name collision would have happened */
    public String stripAndImportPackageIfPossible(String fullClassName) {
        Matcher m = GClass.classNameWithoutGenerics.matcher(fullClassName);
        while (m.find()) {
            String packageName = StringUtils.removeEnd(m.group(1), ".");
            String simpleName = m.group(3);
            if (StringUtils.isNotBlank(packageName) && !this.isImportAlreadyTakenByDifferentPackage(packageName, simpleName)) {
                fullClassName = StringUtils.replaceOnce(fullClassName, packageName + "." + simpleName, simpleName);
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
        if (simpleName.equals(this.shortName) && !packageName.equals(this.packageName)) {
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
        return this.getFullClassName();
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

}
