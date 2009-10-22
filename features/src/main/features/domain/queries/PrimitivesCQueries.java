package features.domain.queries;

import joist.domain.orm.queries.Select;

import com.domainlanguage.money.Money;

import features.domain.PrimitivesCAlias;

public class PrimitivesCQueries extends PrimitivesCQueriesCodegen {

    public long numberOver230() {
        PrimitivesCAlias c = new PrimitivesCAlias("c");
        return Select.from(c).where(c.dollarAmount.greaterThan(Money.dollars(2.30))).count();
    }
}
