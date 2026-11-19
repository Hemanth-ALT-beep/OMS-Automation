package tests;

import api.AuthApi;
import api.OrderApi;
import api.PaymentApi;
import base.BaseApiTest;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

public class OrderApiTest extends BaseApiTest {
    @Test
    public void customerCanCreatePayAndRetrieveOrder() {
        Response login = new AuthApi().login(
                "qa.user@example.test", "MockPass123!");

        Assert.assertEquals(login.statusCode(), 200, "Login should succeed");
        String token = login.jsonPath().getString("accessToken");
        Assert.assertNotNull(token);
        Assert.assertFalse(token.isBlank());

        Response created = new OrderApi().createOrder(token, "SKU-1001", 2, 25.00);
        Assert.assertEquals(created.statusCode(), 201, "Order should be created");
        String orderId = created.jsonPath().getString("orderId");
        Assert.assertEquals(orderId, "ORD-MOCK-1001");
        Assert.assertEquals(created.jsonPath().getString("orderStatus"), "PENDING_PAYMENT");
        Assert.assertEquals(created.jsonPath().getDouble("totalAmount"), 50.00);

        Response payment = new PaymentApi().pay(token, orderId, 50.00, "SUCCESS");
        Assert.assertEquals(payment.statusCode(), 200);
        Assert.assertEquals(payment.jsonPath().getString("paymentStatus"), "SUCCESS");
        Assert.assertEquals(payment.jsonPath().getString("orderId"), orderId);

        Response retrieved = new OrderApi().getOrder(token, orderId);
        Assert.assertEquals(retrieved.statusCode(), 200);
        Assert.assertEquals(retrieved.jsonPath().getString("orderId"), orderId);
        Assert.assertEquals(retrieved.jsonPath().getString("orderStatus"), "CONFIRMED");
    }
}
