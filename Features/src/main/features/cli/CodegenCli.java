package features.cli;

import javax.sql.DataSource;

import org.exigencecorp.domainobjects.codegen.Codegen;
import org.exigencecorp.domainobjects.codegen.CodegenConfig;

import com.domainlanguage.time.CalendarDate;

import features.Registry;
import features.domain.orm.CalendarDateAliasColumn;

public class CodegenCli implements Runnable {

    public void run() {
        new Codegen(new Config()).generate();
    }

    public static class Config extends CodegenConfig {
        public Config() {
            super("features");
            this.setJavaType("date", CalendarDate.class, CalendarDateAliasColumn.class);
        }

        public DataSource getDataSource() {
            return Registry.getDataSource();
        }
    }

}
