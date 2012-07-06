package joist.migrations.columns;

import joist.migrations.columns.ForeignKeyColumn.Owner;
import joist.util.Interpolate;

class HackyConstraintNamer implements ConstraintNamer {

  // We need something short (64 char max constraint names), different, and
  // that will change each time we apply a new column. nanos might also work.
  private long hackyNextId = System.currentTimeMillis();

  @Override
  public String name(Owner owner) {
    return Interpolate.string("c_{}_{}_fk", this.hackyNextId++, owner.toString().toLowerCase());
  }

}
