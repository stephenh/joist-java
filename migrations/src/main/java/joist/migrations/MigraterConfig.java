package joist.migrations;

import java.util.ArrayList;
import java.util.List;

public class MigraterConfig {

  public List<String> packageNamesContainingMigrations = new ArrayList<String>();

  public String getInitialConnectionSetupCommand() {
    return null;
  }

  public void setProjectNameForDefaults(String projectName) {
    this.packageNamesContainingMigrations.add(projectName + ".migrations");
  }

}
