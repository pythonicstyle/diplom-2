import static org.hamcrest.CoreMatchers.equalTo;

import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Test;

public class CreateUserTest {

    UserController userController = new UserController();
    AuthController authController = new AuthController();

    @Test
    @DisplayName("Создание пользователя")
    public void testCreateUser() {
        userController.createUser(
            Constants.RANDOM_EMAIL,
            Constants.TEST_USER_PASSWORD,
            Constants.TEST_USER_NAME
        ).then().statusCode(200).and().body("success", equalTo(true));
    }


    @Test
    @DisplayName("Попытка создания двух одинаковых пользователей")
    public void testCreateDoubleUser() {
        userController.createUser(
                Constants.RANDOM_EMAIL,
                Constants.TEST_USER_PASSWORD,
                Constants.TEST_USER_NAME
            );
        userController.createUser(
                Constants.RANDOM_EMAIL,
                Constants.TEST_USER_PASSWORD,
                Constants.TEST_USER_NAME
            )

            .then()
            .statusCode(403)
            .and()
            .body("success", equalTo(false))
            .body("message", equalTo(Constants.USER_ALREADY_EXIST_ERROR));
    }

    @Test
    @DisplayName("Попытка создания пользователя без Email")
    public void testCreateUserWithoutEmail() {
        userController.createUserWithoutEmail(
                Constants.TEST_USER_PASSWORD,
                Constants.TEST_USER_NAME
            )
            .then()
            .statusCode(403)
            .and()
            .body("success", equalTo(false))
            .body("message", equalTo(Constants.REQUIRED_FIELDS_ERROR));
    }

    @After
    public void tearDown() {
        String token = authController.getAuthToken(Constants.RANDOM_EMAIL, Constants.TEST_USER_PASSWORD);
        if (token != null) {
            userController.deleteUser(token).then().statusCode(202);
            System.out.printf("\nПользователь %s удален", Constants.RANDOM_EMAIL);
        }
    }
}
