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
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

class ExpressCartFeature13Test {

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
    public void testAddDiscountCodeByPercent() {
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

        // And clicks the "Discount codes" link
        WebElement discountCodesLink = wait.until(ExpectedConditions
                .elementToBeClickable(By.linkText("Discount codes")));
        discountCodesLink.click();

        // And clicks the "New discount" button
        WebElement newDiscountButton = wait
                .until(ExpectedConditions.elementToBeClickable(
                        By.xpath("//a[@class='btn btn-outline-success']")));
        newDiscountButton.click();

        // And enters "discperc000" in the "Discount" field
        WebElement discountField = wait.until(
                ExpectedConditions.elementToBeClickable(By.id("discountCode")));
        discountField.sendKeys("discperc000");

        // And selects "Percent" in the "Discount type" dropdown select
        WebElement discountTypeDropdown = driver
                .findElement(By.id("discountType"));
        Select discountTypeSelect = new Select(discountTypeDropdown);
        discountTypeSelect.selectByVisibleText("Percent");

        // And enters "50" in the "Discount value" field
        WebElement discountValueField = driver
                .findElement(By.id("discountValue"));
        discountValueField.sendKeys("50");

        // And enters "12/02/2024 00:00" in the "Discount start" field
        WebElement discountStartField = driver
                .findElement(By.id("discountStart"));
//        String now = new SimpleDateFormat("dd/MM/yyyy HH:mm")
//                .format(Calendar.getInstance().getTime());
//        discountStartField.sendKeys(now);
        discountStartField.sendKeys("12/02/2024 00:00");

        // And enters "12/02/2025 00:00" in the "Discount end" field
        WebElement discountEndField = driver.findElement(By.id("discountEnd"));
        discountEndField.sendKeys("12/02/2025 00:00");

        // And clicks the "Add discount" button
        WebElement addDiscountButton = driver.findElement(
                By.xpath("//button[normalize-space()='Add discount']"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();",
                addDiscountButton);

        // And clicks the "Discount codes" button
        WebElement discountCodesButton = wait.until(ExpectedConditions
                .elementToBeClickable(By.linkText("Discount codes")));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();",
                discountCodesButton);

        // Then "Code: discperc000" is shown in the last row of the table
        WebElement lastRow = wait
                .until(ExpectedConditions.presenceOfElementLocated(
                        By.xpath("//ul[@class='list-group']/li[last()]")));
        String lastRowText = lastRow.getText();
        assertTrue(lastRowText.contains("Code:  discperc000"));

        // Given the previous assertion passed
        // Then the user clicks the "Logout" link
        WebElement logoutLink = driver.findElement(By.linkText("Logout"));
        logoutLink.click();

    }

}
