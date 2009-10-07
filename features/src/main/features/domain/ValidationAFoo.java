package features.domain;

import joist.domain.util.TextString;
import joist.domain.validation.ValidationErrors;
import joist.domain.validation.rules.Rule;

public class ValidationAFoo extends ValidationAFooCodegen {

    private static final Rule<ValidationAFoo> customRule = new Rule<ValidationAFoo>() {
        public void validate(ValidationErrors errors, ValidationAFoo foo) {
            if ("bar".equals(foo.getName())) {
                errors.addPropertyError(foo, "name", "must not be bar");
            }
            if ("baz".equals(foo.getName())) {
                errors.addObjectError(foo, "is all messed up");
            }
        }
    };

    public ValidationAFoo() {
        this.addRule(customRule);
    }

    @Override
    public String toTextString() {
        return TextString.join(this.getName());
    }

}
