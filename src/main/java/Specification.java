import static io.restassured.RestAssured.given;

import io.restassured.specification.RequestSpecification;

public class Specification {

    private static final String base_URI = "https://stellarburgers.nomoreparties.site";

    public static RequestSpecification getRequestSpecification() {
        return given()
            .header("Content-Type", "application/json")
            .baseUri(base_URI)
            .when();
    }
}