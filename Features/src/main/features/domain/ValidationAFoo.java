package features.domain;

import org.exigencecorp.domainobjects.util.TextString;
import org.exigencecorp.domainobjects.validation.ValidationErrors;
import org.exigencecorp.domainobjects.validation.rules.Rule;

public class ValidationAFoo extends ValidationAFooCodegen {

    public ValidationAFoo() {
        this.addExtraRules();
    }

    @Override
    public String toTextString() {
        return TextString.join(this.getName());
    }

    private void addExtraRules() {
        this.addRule(new Rule<ValidationAFoo>() {
            public void validate(ValidationErrors errors, ValidationAFoo foo) {
                if ("bar".equals(foo.getName())) {
                    errors.addPropertyError(foo, "name", "must not be bar");
                }
                if ("baz".equals(foo.getName())) {
                    errors.addObjectError(foo, "is all messed up");
                }
            }
        });
    }

}
