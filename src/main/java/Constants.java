public class Constants {
    public static final String TEST_USER_EMAIL = "TitovYuri";
    public static final String TEST_USER_PASSWORD = "TitovYuri_112";
    public static final String TEST_USER_NAME = "Titov Yuri";
    public static final String RANDOM_EMAIL = Constants.TEST_USER_EMAIL + (int)(Math.random() * 1000) + "@gmail.com";

    public static final String TEST_USER_NAME_NEW = "Titov Yurets";
    public static final String TEST_USER_PASSWORD_NEW = "TitovYuri1234567890-";
    public static final String RANDOM_EMAIL_NEW = Constants.TEST_USER_EMAIL + (int)(Math.random() * 1000) + "@x.net";
    public static final String RANDOM_INCORRECT_EMAIL = Constants.TEST_USER_EMAIL + (int)(Math.random() * 1000) + "@gmai.com";

    public static final String INCORRECT_PASSWORD_ERROR = "email or password are incorrect";
    public static final String USER_WITH_SUCH_EMAIL_ALREADY_EXISTS_ERROR = "User with such email already exists";
    public static final String USER_ALREADY_EXIST_ERROR = "User already exists";
    public static final String REQUIRED_FIELDS_ERROR = "Email, password and name are required fields";
    public static final String SHOULD_BE_AUTHORISED_ERROR = "You should be authorised";
    public static final String INCORRECT_INGREDIENT_HASH = "____61c0c5a71d1f82001bdaaa75";

    public static final String BASE_URL = "https://stellarburgers.nomoreparties.site";

    public static final String API_AUTH_LOGIN = "/api/auth/login";
    public static final String API_ORDERS = "/api/orders";
    public static final String API_ORDERS_ALL = "/api/orders/all";
    public static final String API_INGREDIENTS = "/api/ingredients";
    public static final String API_AUTH_REGISTER = "/api/auth/register";
    public static final String API_AUTH_USER = "/api/auth/user";
}