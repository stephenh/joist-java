package org.exigencecorp.updater;

import java.sql.SQLException;

/** A base class with nice helper methods for updates to extend. */
public abstract class Update {

    private String description;

    protected Update(String description) {
        this.description = description;
    }

    public String toString() {
        return this.getClass().getSimpleName() + " - " + this.description;
    }

    public abstract void apply() throws SQLException;

}
