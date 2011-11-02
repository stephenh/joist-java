package features.domain;

import joist.domain.ValidationAssert;
import joist.domain.validation.rules.RegexRule;

import org.junit.Test;

public class PrimitivesRegexRuleTest extends AbstractFeaturesTest {

  @Test
  public void testDoesNotMatches() {
    Primitives p = new Primitives("p");
    p.addRule(new RegexRule<Primitives>("name", "pp", PrimitivesCodegen.Shims.name));
    ValidationAssert.assertErrors(p, "Name is invalid");
  }

  @Test
  public void testMatches() {
    Primitives p = new Primitives("pop");
    p.addRule(new RegexRule<Primitives>("name", "p.p", PrimitivesCodegen.Shims.name));
    ValidationAssert.assertValid(p);
  }

}
