package ui.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class OrderPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    // Replace these example IDs with selectors from the actual OMS website.
    public OrderPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void createOrder(String sku, String quantity) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("productSku"))).sendKeys(sku);
        driver.findElement(By.id("quantity")).sendKeys(quantity);
        driver.findElement(By.id("createOrder")).click();
    }

    public String orderId() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("orderId"))).getText();
    }
}
