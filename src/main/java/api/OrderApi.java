package api;

import config.TestConfig;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.util.Map;

import static io.restassured.RestAssured.given;

public class OrderApi {
    public Response createOrder(String token, String productId, int quantity, double unitPrice) {
        Map<String, Object> requestBody = Map.of(
                "productId", productId,
                "quantity", quantity,
                "unitPrice", unitPrice
        );

        return given()
                .baseUri(TestConfig.baseUrl())
                .auth().oauth2(token)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(requestBody)
                .when()
                .post(TestConfig.ORDER_ENDPOINT);
    }

    public Response getOrder(String token, String orderId) {
        return given()
                .baseUri(TestConfig.baseUrl())
                .auth().oauth2(token)
                .accept(ContentType.JSON)
                .when()
                .get(TestConfig.ORDER_ENDPOINT + "/{id}", orderId);
    }
}
