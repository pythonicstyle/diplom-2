import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;

import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public class CreateOrderParametrizedTest {

    String accessToken;
    private final JsonObject object;
    private final Integer expectedStatusCode;

    UserController userController = new UserController();

    private static JsonObject getList() {
        List<String> lst = OrdersController.getIngredientsList().then().extract().jsonPath().getList(
            "data._id", String.class
        );
        JsonArray array = new JsonArray();
        for (String hash : lst) {
            array.add(hash);
        }
        JsonObject object = new JsonObject();
        object.add("ingredients", array);
        return object;
    }

    private static JsonObject getIncorrectList() {
        List<String> lst = OrdersController.getIngredientsList().then().extract().jsonPath().getList(
            "data._id", String.class
        );
        JsonArray array = new JsonArray();
        for (String string : lst) {
            array.add(string);
        }
        array.add(Constants.INCORRECT_INGREDIENT_HASH);
        JsonObject object = new JsonObject();
        object.add("ingredients", array);
        return object;
    }

    private static JsonObject getEmptyList() {
        JsonObject object = new JsonObject();
        object.add("ingredients", new JsonArray());
        return object;
    }

    public CreateOrderParametrizedTest(JsonObject object, Integer expectedStatusCode) {
        this.object = object;
        this.expectedStatusCode = expectedStatusCode;
    }

    @Before
    public void setUp() {
        accessToken = userController.createUser(
            Constants.RANDOM_EMAIL,
            Constants.TEST_USER_PASSWORD,
            Constants.TEST_USER_NAME
        ).then().extract().body().path("accessToken");
    }

    @Parameterized.Parameters
    public static Object[][] data() {
        return new Object[][] {
            { getList(), 200 },
            { getEmptyList(), 400 },
            { getIncorrectList(), 500 },
            };
    }

    @Test
    @DisplayName("Создание заказа")
    @Description("Параметризированный тест для создания заказов авторизованным пользователем")
    public void createOrderWithTokenTest() {
        OrdersController.createOrderWithToken(accessToken, object)
            .then()
            .statusCode(expectedStatusCode);
    }

    @Test
    @DisplayName("Создание заказа")
    @Description("Параметризированный тест для создания заказов неавторизованным пользователем")
    public void createOrderWithoutTokenTest() {
        OrdersController.createOrderWithoutToken(object)
            .then()
            .statusCode(expectedStatusCode);
    }

    @After
    public void tearDown() {
        if (accessToken != null) {
            userController.deleteUser(accessToken).then().statusCode(202);
            System.out.printf("\nПользователь %s удален", Constants.RANDOM_EMAIL);
        }
    }
}