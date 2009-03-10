import joist.domain.codegen.tasks.DomainObjectBuilder;

import org.exigencecorp.bd.java.Lib;

public class Build {

    public Lib lib = new Lib("lib");
    public DomainObjectBuilder dobj = new DomainObjectBuilder("features");

    public Build() {
        this.lib.updateFromHomeCache();
        this.dobj.databaseSaPassword = "postgresql_chimera";
    }

}
