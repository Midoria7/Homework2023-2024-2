package javaChatRoom.server.userManager;

import org.junit.jupiter.api.Test;

public class UserManagerTest {

    @Test
    void testUserManager() {
        UserManager userManager = new UserManager();
        userManager.clearUsers();
        for (int i = 1; i <= 100; i++) {
            userManager.addUser(new User("user" + i, "password" + i));
        }
        assert userManager.isValidUser("user1", "password1");
        assert userManager.isValidUser("user2", "password2");
        assert !userManager.isValidUser("user1", "password2");
    }
}
