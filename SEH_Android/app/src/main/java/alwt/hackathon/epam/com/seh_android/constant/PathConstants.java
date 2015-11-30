package alwt.hackathon.epam.com.seh_android.constant;

public class PathConstants {

    private static final String ARTUR = "http://10.17.164.91";
    private static final String SASHA = "http://10.17.164.76";

    private static final String SERVER_HOST = SASHA;
    private static final String SERVER_PORT = ":8080";
    private static final String SERVER = SERVER_HOST + SERVER_PORT;

    private static final String USER = SERVER + "/user/";

    public static final String AUTH = USER + "auth";

    public static final String USER_EMAIL = "email=";

    public static final String USER_NAME = "name=";

    public static final String AMPERSAND = "&";

    public static final String USER_PASS = "password=";

    public static final String USER_ID = "id=";

    public static final String GETUSER = USER + "get?" + USER_EMAIL;

    public static final String ADD_USER = USER + "add?";

    public static final String VALID_USER = USER + "login?" + USER_EMAIL;

    private static final String AUTHORIZATION = SERVER + "/authorization";

    public static final String CHECK_QR = SERVER + "/checkqr?value=";

    public static final String SEND = SERVER + "/send?";

    public static final String PICTURE = "picture=";
}
