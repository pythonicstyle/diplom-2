import static org.hamcrest.CoreMatchers.equalTo;

import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class EditUserTest {
    String accessToken;

    UserController userController = new UserController();

    @Before
    public void setUp() {
        accessToken = userController.createUser(
            Constants.RANDOM_EMAIL,
            Constants.TEST_USER_PASSWORD,
            Constants.TEST_USER_NAME
        ).then().extract().body().path("accessToken");
    }

    @Test
    @DisplayName("Получение информации о пользователе")
    public void testGetUserInfo() {
        userController.getUserInfo(accessToken)
            .then()
            .statusCode(200)
            .and()
            .body("success", equalTo(true));
    }

    @Test
    @DisplayName("Редактирование информации о пользователе с авторизацией")
    public void testEditUserInfo() {
        userController.updateUser(
                Constants.RANDOM_EMAIL_NEW,
                Constants.TEST_USER_NAME_NEW,
                Constants.TEST_USER_PASSWORD_NEW,
                accessToken
            ).then()
            .statusCode(200)
            .and()
            .body("success", equalTo(true))
            .body("user.name", equalTo(Constants.TEST_USER_NAME_NEW))
            .body("user.email", equalTo((Constants.RANDOM_EMAIL_NEW).toLowerCase()));
    }

    @Test
    @DisplayName("Редактирование информации о пользователе без авторизации")
    public void testEditUserInfoWithoutToken() {
        userController.createUser(
            Constants.RANDOM_EMAIL_NEW,
            Constants.TEST_USER_NAME_NEW,
            Constants.TEST_USER_PASSWORD_NEW
        );
        userController.updateUser(
                Constants.RANDOM_EMAIL_NEW,
                Constants.TEST_USER_NAME_NEW,
                Constants.TEST_USER_PASSWORD_NEW,
                ""
            ).then()
            .statusCode(401)
            .and()
            .body("success", equalTo(false))
            .body("message", equalTo(Constants.SHOULD_BE_AUTHORISED_ERROR));
    }

    @Test
    @DisplayName("Редактирование информации о пользователе с email, котороый уже используется")
    public void testEditUserInfoWithPreviousEmail() {
        userController.updateUser(
                Constants.TEST_USER_EMAIL,
                Constants.TEST_USER_NAME_NEW,
                Constants.TEST_USER_PASSWORD_NEW,
                accessToken
            ).then()
            .statusCode(403)
            .and()
            .body("success", equalTo(false))
            .body("message", equalTo(Constants.USER_WITH_SUCH_EMAIL_ALREADY_EXISTS_ERROR));
    }

    @After
    public void tearDown() {
        if (accessToken != null) {
            userController.deleteUser(accessToken).then().statusCode(202);
            System.out.printf("\nПользователь %s удален", Constants.RANDOM_EMAIL);
        }
    }
}