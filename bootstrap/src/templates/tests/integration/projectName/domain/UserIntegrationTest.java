package @projectName@.domain;

import @projectName@.Abstract@ProjectName@IntegrationTest;
import @projectName@.domain.builders.UserBuilder;

public class UserIntegrationTest extends Abstract@ProjectName@IntegrationTest {

    public void testCommit() {
        UserBuilder.create();
        this.commitAndReOpen();
    }

}
