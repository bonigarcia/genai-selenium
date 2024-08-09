/*
 * (C) Copyright 2024 Boni Garcia (https://bonigarcia.github.io/)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package io.github.bonigarcia.selenium.expresscart;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Duration;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

class ExpressCartFeature16Test {

    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeEach
    void setup() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-search-engine-choice-screen");
        driver = new ChromeDriver(options);
        wait = new WebDriverWait(driver, Duration.ofSeconds(5));
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void testBuyProductWithPercentDiscountCode() {
        // Given the user is on the home page
        driver.get("http://localhost:3000");

        // When the user clicks on the account icon
        WebElement accountIcon = wait.until(ExpectedConditions
                .elementToBeClickable(By.xpath("//a[@class=\"btn\"]")));
        accountIcon.click();

        // And enters "test@test.com" in the "email address" field
        WebElement emailField = wait.until(
                ExpectedConditions.elementToBeClickable(By.name("email")));
        emailField.sendKeys("test@test.com");

        // And enters "test" in the "Password" field
        WebElement passwordField = driver.findElement(By.name("password"));
        passwordField.sendKeys("test");

        // And clicks the "Sign in" button
        WebElement signInButton = driver
                .findElement(By.id("customerloginForm"));
        signInButton.click();

        // And goes to the home page of the site
        driver.findElement(
                By.xpath("//button[@aria-label='Toggle navigation']")).click();
        WebElement home = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//a[normalize-space()='Home']")));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();",
                home);

        // And clicks the "NewProduct000" link
        WebElement productLink = wait.until(ExpectedConditions
                .elementToBeClickable(By.linkText("NewProduct000")));
        productLink.click();

        // And clicks the "Add to cart" button
        WebElement addToCartButton = wait
                .until(ExpectedConditions.elementToBeClickable(By.xpath(
                        "//*[contains(@class, 'product-add-to-cart')]")));
        addToCartButton.click();

        // And clicks the "Cart" link
        WebElement cartLink = wait.until(ExpectedConditions
                .elementToBeClickable(By.xpath("//a[@href='/checkout/cart']")));
        cartLink.click();

        // And clicks the "Checkout" button
        WebElement checkoutButton = wait
                .until(ExpectedConditions.elementToBeClickable(
                        By.xpath("//a[@href='/checkout/information']")));
        checkoutButton.click();

        // And clicks the "Continue to shipping" button
        WebElement continueToShippingButton = wait.until(ExpectedConditions
                .elementToBeClickable(By.id("checkoutInformation")));
        continueToShippingButton.click();

        // And clicks the "Proceed to payment" button
        WebElement proceedToPaymentButton = wait
                .until(ExpectedConditions.elementToBeClickable(
                        By.xpath("//a[@href='/checkout/payment']")));
        proceedToPaymentButton.click();

        // And enters "discperc000" in the "Discount code" field
        WebElement discountCodeField = wait.until(
                ExpectedConditions.elementToBeClickable(By.id("discountCode")));
        discountCodeField.sendKeys("discperc000");

        // And clicks the "Apply" button
        WebElement applyButton = driver.findElement(By.id("addDiscountCode"));
        applyButton.click();

        // And waits for at least 6 seconds
        wait.withTimeout(java.time.Duration.ofSeconds(6));

        // Then "Discount: £3.00" is displayed on the page
        WebElement discountDisplay = wait
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath(
                        "//*[contains(text(), 'Discount code applied')]"))); // FIXME
        assertTrue(discountDisplay.isDisplayed());

        // Given the previous assertion passed
        // Then the user clicks on the user icon between the language selector
        // and the cart
        WebElement userIcon = wait
                .until(ExpectedConditions.elementToBeClickable(
                        By.xpath("//button[@class='btn dropdown-toggle']")));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();",
                userIcon);

        // And clicks "Logout"
        WebElement logoutLink = wait.until(
                ExpectedConditions.elementToBeClickable(By.linkText("Logout")));
        logoutLink.click();

    }

}
