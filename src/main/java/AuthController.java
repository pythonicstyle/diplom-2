import io.qameta.allure.Step;
import java.util.HashMap;

public class AuthController {

    @Step("Получкение токена пользователя")
    public String getAuthToken(String email, String pass) {
        HashMap<String, String> map = new HashMap<>();
        map.put("email", email);
        map.put("password", pass);
        return Specification.getRequestSpecification()
            .body(map)
            .post(Constants.API_AUTH_LOGIN)
            .then()
            .extract()
            .body()
            .path("accessToken");
    }
}
