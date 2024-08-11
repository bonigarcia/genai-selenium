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
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

class MediaWikiFeature16Test {

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
    public void testRevertLastEdit() {
        // Given the user is on the home page
        driver.get("http://localhost:8080/");

        // When the user clicks the "Log in" link
        WebElement loginLink = driver.findElement(By.linkText("Log in"));
        loginLink.click();

        // And enters "User001" in the "Username" field
        WebElement usernameField = driver.findElement(By.name("wpName"));
        usernameField.sendKeys("User001");

        // And enters "Password001" in the "Password" field
        WebElement passwordField = driver.findElement(By.name("wpPassword"));
        passwordField.sendKeys("Password001");

        // And clicks the "Log in" button
        WebElement loginButton = driver.findElement(By.name("wploginattempt"));
        loginButton.click();

        // And enters "Selenium WebDriver" in the search bar
        WebElement searchBar = driver.findElement(By.name("search"));
        searchBar.sendKeys("Selenium WebDriver");
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//*[contains(@class, 'suggestions')]")));

        // And presses Enter
        searchBar.sendKeys(Keys.RETURN);

        // And clicks "View history"
        WebElement viewHistoryLink = driver
                .findElement(By.linkText("View history"));
        viewHistoryLink.click();

        // And clicks the "rollback 1 edit" link
        WebElement rollbackLink = driver
                .findElement(By.linkText("rollback 1 edit"));
        rollbackLink.click();

        // Then an "Action complete" page with the comparison of the two
        // different versions of the edited page is shown
        WebElement actionCompleteText = driver.findElement(By.tagName("body"));
        assertTrue(actionCompleteText.getText().contains("Action complete"));

        // Given the previous assertion passed
        // When the user clicks on the "Selenium WebDriver" link
        WebElement seleniumLink = driver
                .findElement(By.linkText("Selenium WebDriver"));
        seleniumLink.click();

        // Then the old version of the page is displayed
        WebElement pageContent = driver.findElement(By.id("bodyContent"));
        assertTrue(pageContent.getText().contains(
                "Selenium WebDriver is a browser automation tool that is widely used for web testing."));

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
