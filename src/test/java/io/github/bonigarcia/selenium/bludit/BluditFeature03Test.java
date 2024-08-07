
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

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Duration;

class BluditFeature03Test {

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
    void testChangeContentPosition() {
        driver.get("http://localhost:8080/admin");

        // Enter "admin" in the Username field
        driver.findElement(By.name("username")).sendKeys("admin");

        // Enter "password" in the Password field
        driver.findElement(By.name("password")).sendKeys("password");

        // Click the "Login" button
        driver.findElement(By.xpath("//*[contains(text(), 'Login')]")).click();

        // Click the "Content" link
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
        WebElement contentLink = wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Manage content")));
        contentLink.click();

        // Click the "Test Content" link
        WebElement testContentLink = wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Test Content")));
        testContentLink.click();

        // Click the "ADVANCED" button
        By byAdvanced = By.xpath("//*[contains(text(), ' Advanced')]");
        WebElement advancedButton = wait.until(ExpectedConditions.elementToBeClickable(byAdvanced));
        advancedButton.click();

        // Clear the "POSITION" field
        By byPosition = By.id("jsposition");
        WebElement positionField = wait.until(ExpectedConditions.visibilityOfElementLocated(byPosition));
        positionField.clear();

        // Enter "30" in the "POSITION" field
        positionField.sendKeys("30");

        // Click the "Save" button
        WebElement saveButton = wait
                .until(ExpectedConditions.elementToBeClickable(By.xpath("//*[contains(text(), 'Save')]")));
        saveButton.click();

        // Click the "Content" link
        driver.findElement(By.cssSelector("body > a")).click(); // open navigation bar
        contentLink = wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Manage content")));
        contentLink.click();

        // Click the "Test Content" link
        testContentLink = wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Test Content")));
        testContentLink.click();

        // Click the "ADVANCED" button
        advancedButton = wait.until(ExpectedConditions.elementToBeClickable(byAdvanced));
        advancedButton.click();

        // Verify "30" is shown as value of the "POSITION" field
        positionField = wait.until(ExpectedConditions.visibilityOfElementLocated(byPosition));
        assertEquals("30", positionField.getAttribute("value"),
                "'30' is not displayed as the value of the 'POSITION' field");

        // Click the "Log out" link
        WebElement logoutLink = wait
                .until(ExpectedConditions.elementToBeClickable(By.xpath("//*[contains(text(), 'Log out')]")));
        logoutLink.click();
    }
}
