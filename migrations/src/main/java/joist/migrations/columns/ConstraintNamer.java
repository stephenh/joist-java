package joist.migrations.columns;

import joist.migrations.columns.ForeignKeyColumn.Owner;

/**
 * Abstraction for naming constraints.
 *
 * This is actually tricky as, due to length requirements, sometimes
 * you can't just concatenate together table + column names.
 */
interface ConstraintNamer {

  String name(Owner owner);

}
