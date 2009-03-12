import joist.domain.codegen.JoistTask;

import org.exigencecorp.bd.resources.java.Lib;

public class Build {

    public Lib lib = new Lib("lib");
    public JoistTask joist = new JoistTask("features");

    public Build() {
        this.lib.updateFromHomeCache();
        this.joist.databaseSaPassword = "postgresql_chimera";
    }

}
