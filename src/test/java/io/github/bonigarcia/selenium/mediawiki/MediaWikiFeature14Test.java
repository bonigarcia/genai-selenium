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

class MediaWikiFeature14Test {

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
    public void testPromoteUserToAdmin() {
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

        // And clicks the "User rights" link
        WebElement userRightsLink = driver
                .findElement(By.linkText("User rights"));
        userRightsLink.click();

        // And enters "User001" in the "Enter a username" field
        WebElement usernameInput = driver.findElement(By.name("user"));
        usernameInput.sendKeys("User001");

        // And clicks the "Load user groups" button
        WebElement loadUserGroupsButton = driver
                .findElement(By.xpath("//input[@type='submit']"));
        loadUserGroupsButton.click();

        // And clicks the "administrator" checkbox
        WebElement adminCheckbox = driver.findElement(By.name("wpGroup-sysop"));
        if (!adminCheckbox.isSelected()) {
            adminCheckbox.click();
        }

        // And enters "promotion" in the "Reason" field
        WebElement reasonField = driver.findElement(By.name("user-reason"));
        reasonField.sendKeys("promotion");

        // And clicks the "Save user groups" button
        WebElement saveUserGroupsButton = driver
                .findElement(By.name("saveusergroups"));
        saveUserGroupsButton.click();

        // Then the "administrator" checkbox is checked
        adminCheckbox = driver.findElement(By.name("wpGroup-sysop"));
        assertTrue(adminCheckbox.isSelected());

        // And an entry about the promotion is shown in the "User rights log"
        // section
        WebElement userRightsLog = driver
                .findElement(By.xpath("//ul[@class='mw-logevent-loglines']"));
        assertTrue(userRightsLog.getText().contains("promotion"));

        // Given the previous assertion passed
        // Then the user clicks the "Log out" link
        WebElement logoutLink = wait.until(ExpectedConditions
                .visibilityOfElementLocated(By.linkText("Log out")));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();",
                logoutLink);

        // Verify the user is logged out by checking the presence of the "Log
        // in" link
        wait.until(ExpectedConditions
                .visibilityOfElementLocated(By.linkText("Log in")));
    }

}
