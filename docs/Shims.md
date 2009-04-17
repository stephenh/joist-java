
Shims are what I used instead of bytecode generation to get private data
out of the class.

At the bottom of codegen classes, there is a list of static "Shim"
instances, one for each column in the class, that can get/set the
private data.

Besides being able to get at the private data, the shims are instances,
so they can be put in lists and passed around to the data mapper for
meta data-based mapping.

Example:

    public static class Shims {
        ...
        public static final Shim<Child, java.lang.String> name = new Shim<Child, java.lang.String>() {
            public void set(Child instance, java.lang.String name) {
                ((ChildCodegen) instance).name = name;
            }
            public String get(Child instance) {
                return ((ChildCodegen) instance).name;
            }
        };
        ...
    }

So, by putting the ChildCodegen.Shims.name instance in a list (or
associating it with an AliasColumn in the Alias class), I can know
easily get/set the value without hitting either reflection or the
public getters/setters (which have side effects of marking stuff
dirty/etc.)
