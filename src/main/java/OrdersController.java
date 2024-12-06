import com.google.gson.JsonObject;
import io.qameta.allure.Step;
import io.restassured.response.Response;


public class OrdersController {

    @Step("Создание заказа под авторизованным пользователем")
    public static Response createOrderWithToken(String token, JsonObject object) {
        return Specification.getRequestSpecification()
            .header("Authorization", token)
            .body(object)
            .post(Constants.API_ORDERS);
    }

    @Step("Создание заказа под неавторизованным пользователем")
    public static Response createOrderWithoutToken(JsonObject object) {
        return Specification.getRequestSpecification()
            .body(object)
            .post(Constants.API_ORDERS);
    }

    @Step("Получить список всех заказов")
    public static Response getOrdersList() {
        return Specification.getRequestSpecification()
            .get(Constants.API_ORDERS_ALL);
    }

    @Step("Получить список ингредиентов")
    public static Response getIngredientsList() {
        return Specification.getRequestSpecification()
            .get(Constants.API_INGREDIENTS);
    }

    @Step("Получить список заказов конкретного пользователя")
    public Response getUserOrders(String token) {
        return Specification.getRequestSpecification()
            .header("Authorization", token)
            .get(Constants.API_ORDERS);
    }
}