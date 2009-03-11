import joist.domain.codegen.JoistTask;

import org.exigencecorp.bd.java.Lib;

public class Build {

    public Lib lib = new Lib("lib");
    public JoistTask dobj = new JoistTask("features");

    public Build() {
        this.lib.updateFromHomeCache();
        this.dobj.databaseSaPassword = "postgresql_chimera";
    }

}
