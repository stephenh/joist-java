package features.domain;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Test;

import features.domain.queries.PrimitivesBQueries.PrimitivesBDto;

public class PrimitivesBTest extends AbstractFeaturesTest {

  @Test
  public void testNullables() {
    PrimitivesB p = new PrimitivesB();
    assertThat(p.getBoolNullableWithDefaultFalse(), is(false));

    this.setFields(p);
    this.commitAndReOpen();

    p = this.reload(p);
    assertThat(p.getBool1(), is(nullValue()));
    assertThat(p.getBool2(), is(true));
    assertThat(p.getBoolWithDefaultTrue(), is(true));
    assertThat(p.getBoolNullableWithDefaultFalse(), nullValue());
    assertThat(p.getInt1(), is(nullValue()));
    assertThat(p.getInt2().intValue(), is(2));
    assertThat(p.getSmall1(), is(nullValue()));
    assertThat(p.getSmall2().intValue(), is(2));
    assertThat(p.getBig1(), is(nullValue()));
    assertThat(p.getBig2(), is(Long.MAX_VALUE));
  }

  @Test
  public void testNullablesInDataTransferObject() {
    PrimitivesB p = new PrimitivesB();
    this.setFields(p);
    this.commitAndReOpen();
    List<PrimitivesBDto> dtos = PrimitivesB.queries.asDtos();
    PrimitivesBDto dto = dtos.get(0);
    assertThat(dto.bool1, is(nullValue()));
    assertThat(dto.bool2, is(true));
    assertThat(dto.int1a, is(nullValue()));
    assertThat(dto.int2a.intValue(), is(2));
    assertThat(dto.small1, is(nullValue()));
    assertThat(dto.small2.intValue(), is(2));
    assertThat(dto.big1, is(nullValue()));
    assertThat(dto.big2, is(Long.MAX_VALUE));
  }

  private void setFields(PrimitivesB p) {
    p.setBool1(null);
    p.setBool2(true);
    p.setBoolNullableWithDefaultFalse(null);
    p.setInt1(null);
    p.setInt2(2);
    p.setSmall1(null);
    p.setSmall2((short) 2);
    p.setBig1(null);
    p.setBig2(Long.MAX_VALUE);
  }

}
