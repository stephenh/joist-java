import org.exigencecorp.bd.java.Lib;
import org.exigencecorp.domainobjects.codegen.tasks.DomainObjectBuilder;

public class Build {

    public Lib lib = new Lib("lib");
    public DomainObjectBuilder dobj = new DomainObjectBuilder("features");

    public Build() {
        this.lib.updateFromHomeCache();
        this.dobj.databaseSaPassword = "postgresql_chimera";
    }

}
