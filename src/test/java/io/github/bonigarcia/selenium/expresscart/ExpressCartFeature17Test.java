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

class ExpressCartFeature17Test {

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
    public void testAddTagToProduct() {
        // Given the user is on the administrative home page (/admin)
        driver.get("http://localhost:3000/admin");

        // When the user enters "owner@test.com" in the "email address" field
        WebElement emailField = wait.until(
                ExpectedConditions.elementToBeClickable(By.name("email")));
        emailField.sendKeys("owner@test.com");

        // And enters "test" in the "Password" field
        WebElement passwordField = driver.findElement(By.name("password"));
        passwordField.sendKeys("test");

        // And clicks the "Sign in" button
        WebElement signInButton = driver.findElement(By.id("loginForm"));
        signInButton.click();

        // And clicks the "Products" link
        WebElement productsLink = wait.until(ExpectedConditions
                .elementToBeClickable(By.linkText("Products")));
        productsLink.click();

        // And clicks the "NewProduct000" link
        WebElement productLink = wait.until(ExpectedConditions
                .elementToBeClickable(By.linkText("NewProduct000")));
        productLink.click();

        // And enters "tag000," in the "Product tag words" field
        WebElement productTagWordsField = wait.until(ExpectedConditions
                .elementToBeClickable(By.id("productTags-tokenfield")));
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView(true);", productTagWordsField);
        productTagWordsField.sendKeys("tag000,");

        // And clicks the "Save product" button
        WebElement saveProductButton = driver
                .findElement(By.id("productUpdate"));
        saveProductButton.click();

        // Then "tag000" is shown in the "Product tag words" field
        WebElement updatedProductTagWordsField = wait
                .until(ExpectedConditions.elementToBeClickable(
                        By.xpath("//*[contains(@class, 'tokenfield')]")));
        assertTrue(updatedProductTagWordsField.getText().contains("tag000"));

        // Given the previous assertion passed
        // Then the user clicks the "Logout" link
        WebElement logoutLink = driver.findElement(By.linkText("Logout"));
        logoutLink.click();
    }
}
