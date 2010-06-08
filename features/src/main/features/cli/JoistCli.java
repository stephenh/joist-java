package features.cli;

import joist.AbstractJoistCli;

public class JoistCli extends AbstractJoistCli {

    public JoistCli() {
        super("features");
        this.codegenConfig.setCollectionSkipped("ParentD", "parentDChildAs");
        this.codegenConfig.setCollectionSkipped("ParentD", "parentDToChildCs");
    }

}
