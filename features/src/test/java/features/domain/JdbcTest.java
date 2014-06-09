package features.domain;

import static features.domain.builders.Builders.aPrimitives;
import static org.junit.Assert.assertEquals;

import java.util.List;

import joist.domain.uow.UoW;
import joist.jdbc.Jdbc;

import org.junit.Test;

public class JdbcTest extends AbstractFeaturesTest {

  @Test
  public void testQueryDtos() {
    aPrimitives().name("p1").defaults();
    aPrimitives().name("p2").defaults();
    this.commitAndReOpen();
    List<PrimitiveDto> dtos = Jdbc.query(UoW.getConnection(), "SELECT id, flag, version FROM primitives", PrimitiveDto.class);
    assertEquals(2, dtos.size());
    assertEquals(1, dtos.get(0).id.intValue());
    assertEquals(2, dtos.get(1).id.intValue());
  }

  public static class PrimitiveDto {
    public Integer id;
    public Boolean flag;
    public Long version;
  }

}
