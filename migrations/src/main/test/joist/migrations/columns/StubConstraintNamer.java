package joist.migrations.columns;

import joist.migrations.columns.ForeignKeyColumn.Owner;
import joist.util.Interpolate;

class StubConstraintNamer implements ConstraintNamer {

  // Like the production one, but we want determinism so always start at 0
  private long nextId = 0;

  @Override
  public String name(Owner owner) {
    return Interpolate.string("c_{}_{}_fk", this.nextId++, owner.toString().toLowerCase());
  }

}
