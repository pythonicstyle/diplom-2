import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;

import io.qameta.allure.junit4.DisplayName;
import java.io.File;
import org.junit.After;
import org.junit.Test;

public class GetOrdersListTest {

    AuthController authController = new AuthController();
    OrdersController ordersController = new OrdersController();
    UserController userController = new UserController();

    @Test
    @DisplayName("Получение списка заказов пользователя по токену")
    public void testGetUserOrders() {
        String token = userController.createUser(
                Constants.RANDOM_EMAIL,
                Constants.TEST_USER_PASSWORD,
                Constants.TEST_USER_NAME
            )
            .then()
            .extract()
            .body()
            .path("accessToken");

        ordersController.getUserOrders(token);
        ordersController.createOrderWithToken(token, new File("src/test/resources/OrderWithIngredients.json"));
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
        ordersController.getOrdersList().then().statusCode(200).body("success", equalTo(true));
        Integer totalOrders = ordersController.getOrdersList().then().extract().body().path("total");
        System.out.printf("\nВсего заказов: %d%n", totalOrders);
    }

    @Test
    @DisplayName("Получение списка ингредиентов")
    public void testGetIngredientsList() {
        ordersController.getIngredientsList().then().statusCode(200).body("success", equalTo(true));
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