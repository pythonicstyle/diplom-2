import io.qameta.allure.Step;
import io.restassured.response.Response;
import java.util.HashMap;


public class UserController {

    @Step("Создание пользователя")
    public Response createUser(String email, String pass, String name) {
        HashMap<String, String> map = new HashMap<>();
        map.put("email", email);
        map.put("password", pass);
        map.put("name", name);
        return Specification.getRequestSpecification()
            .body(map)
            .post("/api/auth/register");
    }

    @Step("Создание пользователя без Email")
    public Response createUserWithoutEmail(String pass, String name) {
        HashMap<String, String> map = new HashMap<>();
        map.put("password", pass);
        map.put("name", name);
        return Specification.getRequestSpecification()
            .body(map)
            .post("/api/auth/register");
    }

    @Step("Удаление пользователя по токену")
    public Response deleteUser(String token) {
        return Specification.getRequestSpecification()
            .header("Authorization", token)
            .delete("/api/auth/user");
    }

    @Step("Обновление информации о пользователе")
    public Response updateUser(String email, String name, String token) {
        HashMap<String, String> data = new HashMap<>();
        data.put("email", email);
        data.put("name", name);
        return Specification.getRequestSpecification()
            .header("Authorization", token)
            .body(data)
            .patch("/api/auth/user");
    }

    @Step("Получение информации о пользователе")
    public Response getUserInfo(String token) {
        return Specification.getRequestSpecification()
            .header("Authorization", token)
            .get("/api/auth/user");
    }

    @Step("Логин пользователя")
    public Response loginUser(String email, String pass) {
        HashMap<String, String> map = new HashMap<>();
        map.put("email", email);
        map.put("password", pass);
        return Specification.getRequestSpecification()
            .body(map)
            .post("/api/auth/login");
    }
}
