package base;

import com.github.tomakehurst.wiremock.WireMockServer;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

public class MockApiBase {
    protected static WireMockServer mock;

    @BeforeSuite(alwaysRun = true)
    public void startMock() {
        if (mock != null && mock.isRunning()) {
            return;
        }

        mock = new WireMockServer(com.github.tomakehurst.wiremock.core.WireMockConfiguration.options().port(8089));
        mock.start();

        mock.stubFor(post(urlEqualTo("/api/login"))
                .atPriority(1)
                .withRequestBody(containing("wrong-password"))
                .willReturn(aResponse()
                        .withStatus(401)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\"error\":\"INVALID_CREDENTIALS\"}")));

        mock.stubFor(post(urlEqualTo("/api/login"))
                .atPriority(2)
                .withRequestBody(containing("qa.user@example.test"))
                .willReturn(okJson(
                        "{\"accessToken\":\"mock-token-123\",\"tokenType\":\"Bearer\"}"
                )));

        mock.stubFor(post(urlEqualTo("/api/orders"))
                .withHeader("Authorization", equalTo("Bearer mock-token-123"))
                .willReturn(created()
                        .withHeader("Content-Type", "application/json")
                        .withBody("{"
                                + "\"orderId\":\"ORD-MOCK-1001\","
                                + "\"productId\":\"SKU-1001\","
                                + "\"quantity\":2,"
                                + "\"unitPrice\":25.00,"
                                + "\"totalAmount\":50.00,"
                                + "\"currency\":\"USD\","
                                + "\"orderStatus\":\"PENDING_PAYMENT\""
                                + "}")));

        mock.stubFor(post(urlEqualTo("/api/payments"))
                .withHeader("Authorization", equalTo("Bearer mock-token-123"))
                .withRequestBody(containing("\"simulateOutcome\":\"SUCCESS\""))
                .willReturn(okJson("{"
                        + "\"paymentId\":\"PAY-MOCK-2001\","
                        + "\"orderId\":\"ORD-MOCK-1001\","
                        + "\"paymentStatus\":\"SUCCESS\","
                        + "\"amount\":50.00"
                        + "}")));

        mock.stubFor(post(urlEqualTo("/api/payments"))
                .withHeader("Authorization", equalTo("Bearer mock-token-123"))
                .withRequestBody(containing("\"simulateOutcome\":\"DECLINED\""))
                .willReturn(aResponse()
                        .withStatus(402)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{"
                                + "\"paymentStatus\":\"DECLINED\","
                                + "\"reason\":\"MOCK_INSUFFICIENT_FUNDS\""
                                + "}")));

        mock.stubFor(get(urlEqualTo("/api/orders/ORD-MOCK-1001"))
                .withHeader("Authorization", equalTo("Bearer mock-token-123"))
                .willReturn(okJson("{"
                        + "\"orderId\":\"ORD-MOCK-1001\","
                        + "\"productId\":\"SKU-1001\","
                        + "\"quantity\":2,"
                        + "\"unitPrice\":25.00,"
                        + "\"totalAmount\":50.00,"
                        + "\"currency\":\"USD\","
                        + "\"orderStatus\":\"CONFIRMED\""
                        + "}")));
    }

    @AfterSuite(alwaysRun = true)
    public void stopMock() {
        if (mock != null && mock.isRunning()) {
            mock.stop();
        }
    }
}
