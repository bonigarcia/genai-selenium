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

class ExpressCartFeature05Test {

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
    public void testAddDuplicateUserToSystemAndFail() {
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

        // And clicks the "+" icon to the right of the link "Products"
        WebElement addProductIcon = wait
                .until(ExpectedConditions.elementToBeClickable(
                        By.xpath("//a[@href='/admin/product/new']")));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();",
                addProductIcon);

        // And enters "NewProduct000" in the "Product title" field
        WebElement productTitleField = wait.until(
                ExpectedConditions.elementToBeClickable(By.id("productTitle")));
        productTitleField.sendKeys("NewProduct000");

        // And enters "15.95" in the "Product price" field
        WebElement productPriceField = driver
                .findElement(By.id("productPrice"));
        productPriceField.sendKeys("15.95");

        // And enters "Description for product 000" in the "Product description"
        // field
        WebElement productDescriptionField = driver.findElement(
                By.xpath("//div[@class='note-editable panel-body']"));
        productDescriptionField.sendKeys("Description for product 000");

        // And clicks the "Add product" button
        WebElement addProductButton = driver
                .findElement(By.id("frm_edit_product_save"));
        addProductButton.click();

        // And clicks the "Products" link
        WebElement productsLink = wait.until(ExpectedConditions
                .elementToBeClickable(By.linkText("Products")));
        productsLink.click();

        // Then "NewProduct" is shown in the first row of the table
        WebElement firstRow = wait
                .until(ExpectedConditions.presenceOfElementLocated(
                        By.xpath("//ul[@class='list-group']/li[2]")));
        String firstRowText = firstRow.getText();
        assertTrue(firstRowText.contains("NewProduct000"));

        // Given the previous assertion passed
        // Then the user clicks the "Logout" link
        WebElement logoutLink = driver.findElement(By.linkText("Logout"));
        logoutLink.click();
    }

}
