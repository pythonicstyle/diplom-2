import static org.hamcrest.CoreMatchers.equalTo;

import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Test;

public class LoginUserTest {

    UserController userController = new UserController();
    AuthController authController = new AuthController();

    @Test
    @DisplayName("Логина пользователя")
    public void testLoginUser() {
        userController.createUser(
            Constants.RANDOM_EMAIL,
            Constants.TEST_USER_PASSWORD,
            Constants.TEST_USER_NAME
        );
        userController.loginUser(
                Constants.RANDOM_EMAIL,
                Constants.TEST_USER_PASSWORD
            )
            .then()
            .statusCode(200)
            .and()
            .body("success", equalTo(true))
            .body("user.email", equalTo(Constants.RANDOM_EMAIL.toLowerCase()));
    }

    @Test
    @DisplayName("Логина пользователя с неверным email")
    public void testLoginUserWithIncorrectEmail() {
        userController.createUser(
            Constants.RANDOM_EMAIL,
            Constants.TEST_USER_PASSWORD,
            Constants.TEST_USER_NAME
        );
        userController.loginUser(
                Constants.RANDOM_INCORRECT_EMAIL,
                Constants.TEST_USER_PASSWORD
            )
            .then()
            .statusCode(401)
            .and()
            .body("success", equalTo(false))
            .body("message", equalTo(Constants.INCORRECT_PASSWORD_ERROR));
    }

    @After
    public void tearDown() {
        String token = authController.getAuthToken(Constants.RANDOM_EMAIL, Constants.TEST_USER_PASSWORD);
        userController.deleteUser(token).then().statusCode(202);
        System.out.printf("\nПользователь %s удален", Constants.RANDOM_EMAIL);
    }
}
