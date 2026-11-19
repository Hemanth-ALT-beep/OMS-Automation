package config;

public final class TestConfig {
    public static final String BASE_URL = baseUrl();
    public static final String LOGIN_ENDPOINT = "/api/login";
    public static final String ORDER_ENDPOINT = "/api/orders";
    public static final String PAYMENT_ENDPOINT = "/api/payments";
    public static final int MOCK_SERVER_PORT = 8089;

    private TestConfig() {
        // Utility class
    }

    public static String baseUrl() {
        return System.getProperty(
                "baseUrl",
                System.getenv().getOrDefault("OMS_BASE_URL", "http://localhost:8089")
        );
    }

    public static String username() {
        return System.getenv().getOrDefault("OMS_TEST_USERNAME", "qa.user@example.test");
    }

    public static String password() {
        return System.getenv().getOrDefault("OMS_TEST_PASSWORD", "MockPass123!");
    }

    public static String uiBaseUrl() {
        return System.getProperty(
                "uiBaseUrl",
                System.getenv().getOrDefault("OMS_UI_BASE_URL", "http://localhost:3000")
        );
    }
}
