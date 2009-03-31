package features.cli;

import joist.domain.codegen.AbstractJoistCli;

public class JoistCli extends AbstractJoistCli {

    public JoistCli() {
        super("features");
        this.dbSaPassword = "postgresql_chimera";
    }

}
