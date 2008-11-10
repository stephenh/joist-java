package org.exigencecorp.domainobjects.updater;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.exigencecorp.util.Reflection;

public class UpdateClasses {

    private String[] packagesContainingUpdates;

    public UpdateClasses(String[] packagesContainingUpdates) {
        this.packagesContainingUpdates = packagesContainingUpdates;
    }

    public int getCurrentCodeVersion() {
        int version = -1;
        while (this.hasUpdate(version + 1)) {
            version++;
        }
        return version;
    }

    public boolean hasUpdate(int version) {
        try {
            this.getUpdate(version);
            return true;
        } catch (ClassNotFoundException cnfe) {
            return false;
        }
    }

    /** @throws ClassNotFoundException if version is not available */
    public Update getUpdate(int version) throws ClassNotFoundException {
        String paddedVersion = StringUtils.leftPad(Integer.toString(version), 4, '0');

        // Use a list so we can fail if we find more than 1
        List<Class<? extends Update>> possibleClasses = new ArrayList<Class<? extends Update>>();
        for (String packageContainingUpdates : this.packagesContainingUpdates) {
            try {
                String possibleName = packageContainingUpdates + ".Update" + paddedVersion;
                possibleClasses.add((Class<? extends Update>) Class.forName(possibleName));
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
