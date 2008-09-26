package org.exigencecorp.updater;

import org.exigencecorp.jdbc.Jdbc;
import org.exigencecorp.updater.columns.BooleanColumn;
import org.exigencecorp.updater.columns.Column;
import org.exigencecorp.updater.columns.DateColumn;
import org.exigencecorp.updater.columns.ForeignKeyColumn;
import org.exigencecorp.updater.columns.IntColumn;
import org.exigencecorp.updater.columns.IsUnique;
import org.exigencecorp.updater.columns.Nullable;
import org.exigencecorp.updater.columns.PrimaryKeyColumn;
import org.exigencecorp.updater.columns.VarcharColumn;
import org.exigencecorp.updater.columns.ForeignKeyColumn.Owner;
import org.exigencecorp.updater.columns.PrimaryKeyColumn.UseSequence;
import org.exigencecorp.updater.commands.CreateTable;
import org.exigencecorp.util.StringBuilderr;

public class Keywords {

    public static void execute(String sql, Object... args) {
        Jdbc.executeUpdate(Updater.getConnection(), sql, args);
    }

    public static Owner theyOwnMe = Owner.IsThem;

    public static Owner iOwnThem = Owner.IsMe;

    public static Owner neitherOwns = Owner.IsNeither;

    public static IsUnique isUnique = IsUnique.Yes;

    public static void createTable(String name, Column... columns) {
        Keywords.execute(new CreateTable(name, columns).toSql());
    }

    public static void createTableSubclass(String parentName, String name, Column... columns) {
        CreateTable t = new CreateTable(name, columns);
        ((PrimaryKeyColumn) t.getColumns()[0]).setUseSequence(UseSequence.No);
        Keywords.execute(t.toSql());
        Keywords.addForeignKey(name, "id", parentName, "id", Owner.IsThem, Nullable.No);
    }

    public static PrimaryKeyColumn primaryKey(String name) {
        return new PrimaryKeyColumn(name);
    }

    public static PrimaryKeyColumn primaryKey(String name, UseSequence useSequence) {
        return new PrimaryKeyColumn(name, useSequence);
    }

    public static ForeignKeyColumn foreignKey(String otherTable, Owner owner) {
        return new ForeignKeyColumn(otherTable, owner);
    }

    public static DateColumn date(String name) {
        return new DateColumn(name);
    }

    public static IntColumn integer(String name) {
        return new IntColumn(name);
    }

    public static VarcharColumn varchar(String name) {
        return new VarcharColumn(name);
    }

    public static VarcharColumn varchar(String name, IsUnique isUnique) {
        return new VarcharColumn(name, isUnique);
    }

    public static BooleanColumn bool(String name) {
        return new BooleanColumn(name);
    }

    public static void addForeignKey(String table, String column, String otherTable, String otherColumn, Owner owner, Nullable nullable) {
        StringBuilderr sql = new StringBuilderr();
        ForeignKeyColumn fk = new ForeignKeyColumn(column, otherTable, otherColumn, nullable, owner);
        fk.setTableName(table);
        fk.postInjectCommands(sql);
        Keywords.execute(sql.toString());
    }

}
