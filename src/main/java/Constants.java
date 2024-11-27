public class Constants {
    public static final String TEST_USER_EMAIL = "TitovYuri";
    public static final String TEST_USER_PASSWORD = "TitovYuri112";
    public static final String TEST_USER_NAME = "Titov Yuri";
    public static final String TEST_USER_NEW_NAME = "Titov Yurets";

    public static final String RANDOM_EMAIL = Constants.TEST_USER_EMAIL + (int)(Math.random() * 1000) + "@gmail.com";
    public static final String RANDOM_NEW_EMAIL = Constants.TEST_USER_EMAIL + (int)(Math.random() * 1000) + "@x.net";
    public static final String RANDOM_INCORRECT_EMAIL = Constants.TEST_USER_EMAIL + (int)(Math.random() * 1000) + "@gmai.com";

    public static final String INCORRECT_PASSWORD_ERROR = "email or password are incorrect";
    public static final String USER_WITH_SUCH_EMAIL_ALREADY_EXISTS_ERROR = "User with such email already exists";
    public static final String USER_ALREADY_EXIST_ERROR = "User already exists";
    public static final String REQUIRED_FIELDS_ERROR = "Email, password and name are required fields";
}