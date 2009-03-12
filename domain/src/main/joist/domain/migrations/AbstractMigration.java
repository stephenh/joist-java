package joist.domain.migrations;

import java.sql.SQLException;

/** A base class with nice helper methods for updates to extend. */
public abstract class AbstractMigration implements Migration {

    private String description;

    protected AbstractMigration(String description) {
        this.description = description;
    }

    public String toString() {
        return this.description;
    }

    public abstract void apply() throws SQLException;

}
