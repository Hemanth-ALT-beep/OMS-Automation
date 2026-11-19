package tests;

import base.BaseUiTest;
import config.TestConfig;
import org.testng.Assert;
import org.testng.annotations.Test;
import ui.pages.LoginPage;
import ui.pages.OrderPage;

public class OrderUiTest extends BaseUiTest {
    @Test
    public void userCanCreateOrderInBrowser() {
        // This template requires a real OMS UI and matching element IDs.
        new LoginPage(driver).login(TestConfig.username(), TestConfig.password());

        OrderPage order = new OrderPage(driver);
        order.createOrder("SKU-1001", "2");
        Assert.assertTrue(order.orderId().startsWith("ORD-"));
    }
}
