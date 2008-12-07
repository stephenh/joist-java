package features.domain;

import org.exigencecorp.domainobjects.util.FriendlyString;
import org.exigencecorp.domainobjects.validation.ValidationErrors;
import org.exigencecorp.domainobjects.validation.rules.Rule;

public class ValidationAFoo extends ValidationAFooCodegen {

    public ValidationAFoo() {
        this.addExtraRules();
    }

    @Override
    public String toFriendlyString() {
        return FriendlyString.join(this.getName());
    }

    private void addExtraRules() {
        this.addRule(new Rule<ValidationAFoo>() {
            public void validate(ValidationErrors errors, ValidationAFoo foo) {
                if ("bar".equals(foo.getName())) {
                    errors.addPropertyError(foo, "name", "must not be bar");
                }
            }
        });
    }

}
