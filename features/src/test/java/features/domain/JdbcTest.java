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

  @Test
  public void testNoSqlInjection() {
    // previously these would fail with syntax exceptions on the ticks
    Jdbc.queryForInt(UoW.getConnection(), "SELECT COUNT(*) FROM parent WHERE name = ?", "'foo'");
    Jdbc.queryForRow(UoW.getConnection(), "SELECT COUNT(*) FROM parent WHERE name = ?", "'foo'");
    Jdbc.update(UoW.getConnection(), "UPDATE parent SET name = null WHERE name = ?", "'foo'");
  }

  public static class PrimitiveDto {
    public Integer id;
    public Boolean flag;
    public Long version;
  }

}
