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
package io.github.bonigarcia.selenium.mediawiki;

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

class MediaWikiFeature01Test {

    WebDriver driver;
    WebDriverWait wait;

    @BeforeEach
    public void setUp() {
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
    public void testCreateNewUser() {
        // Given the user is on the home page
        driver.get("http://localhost:8080/");

        // When the user clicks the "Log in" link
        WebElement loginLink = driver.findElement(By.linkText("Log in"));
        loginLink.click();

        // And enters "admin" in the "Username" field
        WebElement usernameField = driver.findElement(By.name("wpName"));
        usernameField.sendKeys("admin");

        // And enters "Password001" in the "Password" field
        WebElement passwordField = driver.findElement(By.name("wpPassword"));
        passwordField.sendKeys("Password001");

        // And clicks the "Log in" button
        WebElement loginButton = driver.findElement(By.name("wploginattempt"));
        loginButton.click();

        // And clicks the "Special pages" link
        WebElement specialPagesLink = driver
                .findElement(By.linkText("Special pages"));
        specialPagesLink.click();

        // And clicks the "Create account" link
        WebElement createAccountLink = driver
                .findElement(By.linkText("Create account"));
        createAccountLink.click();

        // And enters "User001" in the "Username" field
        WebElement newUsernameField = driver.findElement(By.name("wpName"));
        newUsernameField.sendKeys("User001");

        // And enters "Password001" in the "Password" field
        WebElement newPasswordField = driver.findElement(By.name("wpPassword"));
        newPasswordField.sendKeys("Password001");

        // And enters "Password001" in the "Confirm Password" field
        WebElement confirmPasswordField = driver.findElement(By.name("retype"));
        confirmPasswordField.sendKeys("Password001");

        // And enters "Real Name 001" in the "Real Name" field
        WebElement realNameField = driver.findElement(By.name("realname"));
        realNameField.sendKeys("Real Name 001");

        // And clicks the "Create" button
        WebElement createButton = driver
                .findElement(By.name("wpCreateaccount"));
        createButton.click();

        // Then "The user account for User001 (talk) has been created." is
        // displayed
        WebElement confirmationMessage = driver
                .findElement(By.id("mw-content-text"));
        assertTrue(confirmationMessage.getText().contains(
                "The user account for User001 (talk) has been created."));

        // Given the previous assertion passed
        // Then the user clicks the "Log out" link
        WebElement logoutLink = wait.until(ExpectedConditions
                .visibilityOfElementLocated(By.linkText("Log out")));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();",
                logoutLink);

        // Verify the user is logged out by checking the presence of the "Log
        // in" link
        wait.until(ExpectedConditions
                .visibilityOfElementLocated(By.linkText("Log in"))).click();
    }

}
