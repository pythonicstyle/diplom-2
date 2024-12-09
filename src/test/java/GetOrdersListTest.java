import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import io.qameta.allure.junit4.DisplayName;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class GetOrdersListTest {
    String token;

    OrdersController ordersController = new OrdersController();
    UserController userController = new UserController();

    @Before
    public void setUp() {
        token = userController.createUser(
                Constants.RANDOM_EMAIL,
                Constants.TEST_USER_PASSWORD,
                Constants.TEST_USER_NAME
            )
            .then()
            .extract()
            .body()
            .path("accessToken");
    }

    @Test
    @DisplayName("Получение списка заказов пользователя по токену")
    public void testGetUserOrders() {
        ordersController.getUserOrders(token);
        List<String> lst = OrdersController.getIngredientsList().then().extract().jsonPath().getList(
            "data._id", String.class
        );
        JsonArray array = new JsonArray();
        for (String hash : lst) {
            array.add(hash);
        }
        JsonObject object = new JsonObject();
        object.add("ingredients", array);
        OrdersController.createOrderWithToken(token, object);
        ordersController.getUserOrders(token)
            .then()
            .statusCode(200)
            .and()
            .body("success", equalTo(true))
            .body("orders", notNullValue()
        );
    }

    @Test
    @DisplayName("Получение списка заказов всех пользователей")
    public void testGetOrdersList() {
        OrdersController.getOrdersList().then().statusCode(200).body("success", equalTo(true));
        Integer totalOrders = OrdersController.getOrdersList().then().extract().body().path("total");
        System.out.printf("\nВсего заказов: %d%n", totalOrders);
    }

    @Test
    @DisplayName("Получение списка ингредиентов")
    public void testGetIngredientsList() {
        OrdersController.getIngredientsList().then().statusCode(200).body("success", equalTo(true));
    }

    @After
    public void tearDown() {
        if (token != null) {
            userController.deleteUser(token).then().statusCode(202);
            System.out.printf("\nПользователь %s удален", Constants.RANDOM_EMAIL);
        }
    }
}