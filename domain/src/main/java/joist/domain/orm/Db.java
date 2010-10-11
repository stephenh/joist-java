package joist.domain.orm;

public enum Db {

  MYSQL, PG;

  public boolean isPg() {
    return this == PG;
  }

  public boolean isMySQL() {
    return this == MYSQL;
  }

}
