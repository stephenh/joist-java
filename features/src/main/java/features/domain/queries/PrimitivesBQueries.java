package features.domain.queries;

import java.util.List;

import joist.domain.orm.queries.Select;
import joist.domain.orm.queries.columns.Aggregate;
import features.domain.PrimitivesBAlias;

public class PrimitivesBQueries extends PrimitivesBQueriesCodegen {

  public List<PrimitivesBDto> asDtos() {
    PrimitivesBAlias p = new PrimitivesBAlias();
    return Select
      .from(p)
      .select(
        p.id.as("id"),
        p.bool1.as("bool1"),
        p.bool2.as("bool2"),
        p.int1.as("int1a"),
        p.int2.as("int2a"),
        p.small1.as("small1"),
        p.small2.as("small2"),
        p.big1.as("big1"),
        p.big2.as("big2"))
      .list(PrimitivesBDto.class);
  }

  public Short sumShort() {
    PrimitivesBAlias p = new PrimitivesBAlias();
    return Select.from(p).select(Aggregate.sum(p.small1).as("sumSmall")).list(SumResult.class).get(0).sumSmall;
  }

  public Long countShort() {
    PrimitivesBAlias p = new PrimitivesBAlias();
    return Select.from(p).select(Aggregate.count(p.small1).as("countSmall")).list(CountResult.class).get(0).countSmall;
  }

  public static class PrimitivesBDto {
    public Long id;
    public Boolean bool1;
    public Boolean bool2;
    public Integer int1a; // int1 is a keyword so add an extra letter
    public Integer int2a; // int2 is a keyword
    public Short small1;
    public Short small2;
    public Long big1;
    public Long big2;
  }

  public static class SumResult {
    public Short sumSmall;
  }

  public static class CountResult {
    public Long countSmall;
  }
}
