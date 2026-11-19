package api;

import config.TestConfig;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.util.Map;

import static io.restassured.RestAssured.given;

public class AuthApi {
    public Response login(String username, String password) {
        Map<String, String> requestBody = Map.of(
                "username", username,
                "password", password
        );

        return given()
                .baseUri(TestConfig.baseUrl())
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(requestBody)
                .when()
                .post(TestConfig.LOGIN_ENDPOINT);
    }
}
