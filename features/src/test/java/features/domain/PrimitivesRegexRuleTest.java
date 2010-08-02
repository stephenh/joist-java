package features.domain;

import joist.domain.Requirements;
import joist.domain.validation.rules.RegexRule;

public class PrimitivesRegexRuleTest extends AbstractFeaturesTest {

  static {
    Requirements.rulesCanBeRegex.tests();
  }

  public void testDoesNotMatches() {
    Primitives p = new Primitives("p");
    p.addRule(new RegexRule<Primitives>("name", "pp", PrimitivesCodegen.Shims.name));
    ValidationAssert.assertErrors(p, "Name is invalid");
  }

  public void testMatches() {
    Primitives p = new Primitives("pop");
    p.addRule(new RegexRule<Primitives>("name", "p.p", PrimitivesCodegen.Shims.name));
    ValidationAssert.assertValid(p);
  }

}
