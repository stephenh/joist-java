package joist.domain.migrations;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.exigencecorp.util.Reflection;

public class MigrationLoader {

    private List<String> packagesContainingMigrations;

    public MigrationLoader(List<String> packagesContainingMigrations) {
        this.packagesContainingMigrations = packagesContainingMigrations;
    }

    public int getCurrentCodeVersion() {
        int version = -1;
        while (this.hasMigration(version + 1)) {
            version++;
        }
        return version;
    }

    public boolean hasMigration(int version) {
        try {
            this.get(version);
            return true;
        } catch (ClassNotFoundException cnfe) {
            return false;
        }
    }

    /** @throws ClassNotFoundException if version is not available */
    public AbstractMigration get(int version) throws ClassNotFoundException {
        String paddedVersion = StringUtils.leftPad(Integer.toString(version), 4, '0');

        // Use a list so we can fail if we find more than 1
        List<Class<? extends AbstractMigration>> possibleClasses = new ArrayList<Class<? extends AbstractMigration>>();
        for (String packageContainingMigrations : this.packagesContainingMigrations) {
            try {
                String possibleName = packageContainingMigrations + ".m" + paddedVersion;
                possibleClasses.add((Class<? extends AbstractMigration>) Class.forName(possibleName));
            } catch (ClassNotFoundException cnfe) {
            }
        }

        if (possibleClasses.size() == 0) {
            throw new ClassNotFoundException("Didn't find an update for version " + paddedVersion);
        } else if (possibleClasses.size() > 1) {
            throw new RuntimeException("Found updates for version " + paddedVersion + " in multiple packages");
        } else {
            return Reflection.newInstance(possibleClasses.get(0));
        }
    }

}
