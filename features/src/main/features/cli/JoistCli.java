package features.cli;

import joist.domain.codegen.AbstractJoistCli;

public class JoistCli extends AbstractJoistCli {

    public JoistCli() {
        super("features");
        this.databaseSaPassword = "postgresql_chimera";
    }

}
