import io.qameta.allure.Step;
import io.restassured.response.Response;
import java.io.File;


public class OrdersController {

    @Step("Создание заказа под авторизованным пользователем")
    public Response createOrderWithToken(String token, File file) {
        return Specification.getRequestSpecification()
            .header("Authorization", token)
            .body(file)
            .post("/api/orders");
    }

    @Step("Создание заказа под неавторизованным пользователем")
    public Response createOrderWithoutToken(File file) {
        return Specification.getRequestSpecification()
            .body(file)
            .post("/api/orders");
    }

    @Step("Получить список всех заказов")
    public Response getOrdersList() {
        return Specification.getRequestSpecification()
            .get("/api/orders/all");
    }

    @Step("Получить список ингредиентов")
    public Response getIngredientsList() {
        return Specification.getRequestSpecification()
            .get("/api/ingredients");
    }

    @Step("Получить список заказов конкретного пользователя")
    public Response getUserOrders(String token) {
        return Specification.getRequestSpecification()
            .header("Authorization", token)
            .get("/api/orders");
    }
}