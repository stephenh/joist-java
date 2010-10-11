package @projectName@.domain.builders;

import joist.domain.util.AbstractBuilder;
import joist.util.TestCounter;
import @projectName@.domain.User;

public class UserBuilder extends AbstractBuilder<User> {

    private static TestCounter counter = new TestCounter();

    public static UserBuilder create() {
        int i = counter.next();
        User user = new User();
        user.setUsername("user" + i);
        return new UserBuilder(user);
    }

    protected UserBuilder(User user) {
        super(user);
    }

}
