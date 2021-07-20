package next.model;

import static org.junit.Assert.*;

public class UserTest {
    public static User newUser(String userId) {
        return new User(userId, "password", "name", "test@sample.com");
    }
}