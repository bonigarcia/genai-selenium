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

class ExpressCartFeature07Test {

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
    public void testAddProductToCart() {
        // Given the user is on the home page
        driver.get("http://localhost:3000");

        // When the user clicks the "NewProduct000" link
        WebElement productLink = wait.until(ExpectedConditions
                .elementToBeClickable(By.linkText("NewProduct000")));
        productLink.click();

        // And clicks the "Add to cart" button
        WebElement addToCartButton = wait
                .until(ExpectedConditions.elementToBeClickable(
                        By.xpath("//button[normalize-space()='Add to cart']")));
        addToCartButton.click();

        // And clicks the "Home" link
        driver.findElement(
                By.xpath("//button[@aria-label='Toggle navigation']")).click();
        WebElement home = driver
                .findElement(By.xpath("//a[normalize-space()='Home']"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();",
                home);

        // Then "1" is shown in the red square to the right of the "Cart" link
        WebElement cartLink = wait.until(ExpectedConditions
                .elementToBeClickable(By.xpath("//a[@href='/checkout/cart']")));
        String cartCountText = cartLink.getText();
        assertTrue(cartCountText.contains("1"));

        // When the user clicks on the "Cart" link
        cartLink.click();

        // Then "NewProduct000" is shown in the "Cart contents"
        WebElement cartContents = wait
                .until(ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//div[@class='card-body cart-body']")));
        String cartContentsText = cartContents.getText();
        assertTrue(cartContentsText.contains("NewProduct000"));
    }

}
