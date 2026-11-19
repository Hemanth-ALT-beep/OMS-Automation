package base;

import config.TestConfig;
import io.restassured.RestAssured;
import org.testng.annotations.BeforeClass;

public class BaseApiTest extends MockApiBase {
    @BeforeClass(alwaysRun = true)
    public void configureApi() {
        RestAssured.baseURI = TestConfig.baseUrl();
    }
}
