package joist.domain.migrations;

import java.util.ArrayList;
import java.util.List;

public class MigraterConfig {

    public List<String> packageNamesContainingMigrations = new ArrayList<String>();

    public String getInitialConnectionSetupCommand() {
        return "SET CONSTRAINTS ALL DEFERRED; SET CLIENT_ENCODING TO 'UNICODE';";
    }

    public void setProjectNameForDefaults(String projectName) {
        this.packageNamesContainingMigrations.add(projectName + ".migrations");
    }

}
