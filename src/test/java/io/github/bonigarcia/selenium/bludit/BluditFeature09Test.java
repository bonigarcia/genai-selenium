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
package io.github.bonigarcia.selenium.bludit;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertTrue;

class BluditFeature09Test {

    WebDriver driver;

    @BeforeEach
    void setup() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-search-engine-choice-screen");
        driver = new ChromeDriver(options);
    }

    @AfterEach
    void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    void testChangeUserPassword() {
        driver.get("http://localhost:8080/admin");

        // Enter "admin" in the Username field
        driver.findElement(By.name("username")).sendKeys("admin");

        // Enter "password" in the Password field
        driver.findElement(By.name("password")).sendKeys("password");

        // Click the "Login" button
        driver.findElement(By.xpath("//*[contains(text(), 'Login')]")).click();

        // Click the "Users" link
        driver.findElement(By.cssSelector("body > a")).click(); // open navigation bar
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        WebElement usersLink = wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Manage users")));
        usersLink.click();

        // Click the second username in the "Users" page (usertest)
        WebElement secondUser = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//table/tbody/tr[2]/td/a[contains(text(), 'usertest')]")));
        secondUser.click();

        // Click the "Change password" link
        WebElement changePasswordLink = wait
                .until(ExpectedConditions.elementToBeClickable(By.linkText("Change password")));
        changePasswordLink.click();

        // Enter "newpassword" in the "New password" field
        WebElement newPasswordField = wait
                .until(ExpectedConditions.visibilityOfElementLocated(By.name("new_password")));
        newPasswordField.sendKeys("newpassword");

        // Enter "newpassword" in the "Confirm password" field
        WebElement confirmPasswordField = wait
                .until(ExpectedConditions.visibilityOfElementLocated(By.name("confirm_password")));
        confirmPasswordField.sendKeys("newpassword");

        // Click the "Save" button
        WebElement saveButton = wait
                .until(ExpectedConditions.elementToBeClickable(By.xpath("//*[contains(text(), 'Save')]")));
        saveButton.click();

        // Verify an alert saying "The changes has been saved" is shown for about 2
        // seconds in the top right corner of the page
        WebElement alert = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("alert-ok")));
        assertTrue(alert.isDisplayed(), "The alert 'The changes has been saved' is not displayed");

        // Wait for the alert to disappear
        wait.until(ExpectedConditions.invisibilityOf(alert));

        // Click the "Log out" link
        WebElement logoutLink = wait
                .until(ExpectedConditions.elementToBeClickable(By.xpath("//*[contains(text(), 'Log out')]")));
        logoutLink.click();
    }
}
