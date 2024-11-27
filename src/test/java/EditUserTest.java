import static org.hamcrest.CoreMatchers.equalTo;

import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Test;

public class EditUserTest {

    UserController userController = new UserController();
    AuthController authController = new AuthController();


    @Test
    @DisplayName("Получение информации о пользователе")
    public void testGetUserInfo() {
        String token = userController.createUser(
            Constants.RANDOM_EMAIL,
            Constants.TEST_USER_PASSWORD,
            Constants.TEST_USER_NAME
        ).then().extract().body().path("accessToken");
        userController.getUserInfo(token)
            .then()
            .statusCode(200)
            .and()
            .body("success", equalTo(true));
    }

    @Test
    @DisplayName("Редактирование информации о пользователе")
    public void testEditUserInfo() {
        String token = userController.createUser(
            Constants.RANDOM_EMAIL,
            Constants.TEST_USER_PASSWORD,
            Constants.TEST_USER_NAME
        ).then().extract().body().path("accessToken");
        userController.updateUser(
                Constants.RANDOM_NEW_EMAIL,
                Constants.TEST_USER_NEW_NAME,
                token
            ).then()
            .statusCode(200)
            .and()
            .body("success", equalTo(true))
            .body("user.name", equalTo(Constants.TEST_USER_NEW_NAME))
            .body("user.email", equalTo((Constants.RANDOM_NEW_EMAIL).toLowerCase()));
    }

    @Test
    @DisplayName("Редактирование информации о пользователе без токена")
    public void testEditUserInfoWithoutToken() {
        userController.createUser(
            Constants.RANDOM_EMAIL,
            Constants.TEST_USER_PASSWORD,
            Constants.TEST_USER_NAME
        );
        userController.updateUser(
                Constants.RANDOM_NEW_EMAIL,
                Constants.TEST_USER_NEW_NAME,
                ""
            ).then()
            .statusCode(401)
            .and()
            .body("success", equalTo(false))
            .body("message", equalTo("You should be authorised"));
    }

    @Test
    @DisplayName("Редактирование информации о пользователе без токена")
    public void testEditUserInfoWithPreviousEmail() {
        String token = userController.createUser(
            Constants.RANDOM_EMAIL,
            Constants.TEST_USER_PASSWORD,
            Constants.TEST_USER_NAME
        ).then().extract().body().path("accessToken");
        userController.createUser(
            Constants.TEST_USER_EMAIL,
            Constants.TEST_USER_PASSWORD,
            Constants.TEST_USER_NAME
        );
        userController.updateUser(
                Constants.TEST_USER_EMAIL,
                Constants.TEST_USER_NEW_NAME,
                token
            ).then()
            .statusCode(403)
            .and()
            .body("success", equalTo(false))
            .body("message", equalTo(Constants.USER_WITH_SUCH_EMAIL_ALREADY_EXISTS_ERROR));
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
