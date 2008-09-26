package features.cli;

import javax.sql.DataSource;

import org.exigencecorp.domainobjects.codegen.Codegen;
import org.exigencecorp.domainobjects.codegen.CodegenConfig;

import features.Registry;

public class CodegenCli implements Runnable {

    public void run() {
        new Codegen(new Config()).generate();
    }

    public static class Config extends CodegenConfig {
        public Config() {
            super("features");
        }

        public DataSource getDataSource() {
            return Registry.getDataSource();
        }
    }

}
