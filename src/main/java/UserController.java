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
            .post(Constants.API_AUTH_REGISTER);
    }

    @Step("Создание пользователя без Email")
    public Response createUserWithoutEmail(String pass, String name) {
        HashMap<String, String> map = new HashMap<>();
        map.put("password", pass);
        map.put("name", name);
        return Specification.getRequestSpecification()
            .body(map)
            .post(Constants.API_AUTH_REGISTER);
    }

    @Step("Удаление пользователя по токену")
    public Response deleteUser(String token) {
        return Specification.getRequestSpecification()
            .header("Authorization", token)
            .delete(Constants.API_AUTH_USER);
    }

    @Step("Обновление информации о пользователе")
    public Response updateUser(String email, String name, String pass, String token) {
        HashMap<String, String> map = new HashMap<>();
        map.put("email", email);
        map.put("name", name);
        map.put("password", pass);
        return Specification.getRequestSpecification()
            .header("Authorization", token)
            .body(map)
            .patch(Constants.API_AUTH_USER);
    }

    @Step("Получение информации о пользователе")
    public Response getUserInfo(String token) {
        return Specification.getRequestSpecification()
            .header("Authorization", token)
            .get(Constants.API_AUTH_USER);
    }

    @Step("Логин пользователя")
    public Response loginUser(String email, String pass) {
        HashMap<String, String> map = new HashMap<>();
        map.put("email", email);
        map.put("password", pass);
        return Specification.getRequestSpecification()
            .body(map)
            .post(Constants.API_AUTH_LOGIN);
    }
}
