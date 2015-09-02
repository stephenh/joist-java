package joist.migrations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang.StringUtils;

import joist.util.Reflection;

public class MigrationLoader {

  private List<String> packagesContainingMigrations;

  public MigrationLoader(List<String> packagesContainingMigrations) {
    this.packagesContainingMigrations = packagesContainingMigrations;
  }

  public int getCurrentCodeVersion() {
    int version = -1;
    while (this.get("m", version + 1).isPresent()) {
      version++;
    }
    return version;
  }

  /** @throws ClassNotFoundException if version is not available */
  @SuppressWarnings("unchecked")
  public Optional<Migration> get(String prefix, int version) {
    String paddedVersion = StringUtils.leftPad(Integer.toString(version), 4, '0');

    // Use a list so we can fail if we find more than 1
    List<Class<? extends AbstractMigration>> possibleClasses = new ArrayList<Class<? extends AbstractMigration>>();
    for (String packageContainingMigrations : this.packagesContainingMigrations) {
      try {
        String possibleName = packageContainingMigrations + "." + prefix + paddedVersion;
        possibleClasses.add((Class<? extends AbstractMigration>) Class.forName(possibleName));
      } catch (ClassNotFoundException cnfe) {
      }
    }

    if (possibleClasses.size() == 0) {
      return Optional.empty();
    } else if (possibleClasses.size() > 1) {
      throw new RuntimeException("Found updates for version " + paddedVersion + " in multiple packages");
    } else {
      return Optional.of(Reflection.newInstance(possibleClasses.get(0)));
    }
  }

}
