package @projectName@.domain;

import joist.domain.validation.ValidationException;
import @projectName@.Abstract@ProjectName@IntegrationTest;
import @projectName@.domain.builders.UserBuilder;

public class UserIntegrationTest extends Abstract@ProjectName@IntegrationTest {

    public void testCommit() {
        UserBuilder user1 = UserBuilder.create();
        this.commitAndReOpen();
    }

}
