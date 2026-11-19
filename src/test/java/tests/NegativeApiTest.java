package tests;

import api.AuthApi;
import api.OrderApi;
import api.PaymentApi;
import base.BaseApiTest;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

public class NegativeApiTest extends BaseApiTest {
    @Test
    public void invalidLoginIsRejected() {
        Response response = new AuthApi().login(
                "qa.user@example.test", "wrong-password");

        Assert.assertEquals(response.statusCode(), 401);
        Assert.assertNull(response.jsonPath().getString("accessToken"));
        Assert.assertEquals(response.jsonPath().getString("error"), "INVALID_CREDENTIALS");
    }

    @Test
    public void declinedPaymentIsNotSuccessful() {
        String token = new AuthApi().login(
                "qa.user@example.test", "MockPass123!")
                .jsonPath().getString("accessToken");

        Response order = new OrderApi().createOrder(token, "SKU-1001", 2, 25.00);
        Assert.assertEquals(order.statusCode(), 201);

        String orderId = order.jsonPath().getString("orderId");
        Response payment = new PaymentApi().pay(token, orderId, 50.00, "DECLINED");

        Assert.assertEquals(payment.statusCode(), 402);
        Assert.assertEquals(payment.jsonPath().getString("paymentStatus"), "DECLINED");
        Assert.assertEquals(
                payment.jsonPath().getString("reason"),
                "MOCK_INSUFFICIENT_FUNDS"
        );
    }
}
