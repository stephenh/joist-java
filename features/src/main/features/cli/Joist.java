package features.cli;

import joist.domain.codegen.JoistTask;

public class Joist extends JoistTask {

    public Joist() {
        super("features");
        this.databaseSaPassword = "postgresql_chimera";
    }

}
