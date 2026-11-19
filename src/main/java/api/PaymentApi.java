package api;

import config.TestConfig;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.util.Map;

import static io.restassured.RestAssured.given;

public class PaymentApi {
    public Response pay(String token, String orderId, double amount, String outcome) {
        Map<String, Object> requestBody = Map.of(
                "orderId", orderId,
                "amount", amount,
                "simulateOutcome", outcome
        );

        return given()
                .baseUri(TestConfig.baseUrl())
                .auth().oauth2(token)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(requestBody)
                .when()
                .post(TestConfig.PAYMENT_ENDPOINT);
    }
}
