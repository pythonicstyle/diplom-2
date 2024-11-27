import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import java.io.File;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public class CreateOrderParametrizedTest {

    private final File file;
    private final Integer expectedStatusCode;

    AuthController authController = new AuthController();
    UserController userController = new UserController();
    OrdersController ordersController = new OrdersController();

    public CreateOrderParametrizedTest(File file, Integer expectedStatusCode) {
        this.file = file;
        this.expectedStatusCode = expectedStatusCode;
    }

    @Parameterized.Parameters
    public static Object[][] data() {
        return new Object[][] {
            { new File("src/test/resources/OrderWithIngredients.json"), 200 },
            { new File("src/test/resources/OrderWithIngredients.json"), 200 },
            { new File("src/test/resources/OrderWithoutIngredients.json"), 400 },
            { new File("src/test/resources/OrderWithIncorrectIngredient.json"), 500 },
            };
    }

    @Test
    @DisplayName("Создание заказа")
    @Description("Параметризированный тест для создания заказов авторизованным пользователем")
    public void createOrderWithTokenTest() {
        String accessToken = userController.createUser(
            Constants.RANDOM_EMAIL,
            Constants.TEST_USER_PASSWORD,
            Constants.TEST_USER_NAME
        ).then().extract().body().path("accessToken");

        ordersController.createOrderWithToken(accessToken, file)
            .then()
            .statusCode(expectedStatusCode);
    }

    @Test
    @DisplayName("Создание заказа")
    @Description("Параметризированный тест для создания заказов неавторизованным пользователем")
    public void createOrderWithoutTokenTest() {
        ordersController.createOrderWithoutToken(file)
            .then()
            .statusCode(expectedStatusCode);
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