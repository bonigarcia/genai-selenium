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

class BluditFeature05Test {

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
    void testAddNewDraft() {
        driver.get("http://localhost:8080/admin");

        // Enter "admin" in the Username field
        driver.findElement(By.name("username")).sendKeys("admin");

        // Enter "password" in the Password field
        driver.findElement(By.name("password")).sendKeys("password");

        // Click the "Login" button
        driver.findElement(By.xpath("//*[contains(text(), 'Login')]")).click();

        // Click the "New content" link
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
        WebElement newContentLink = wait.until(ExpectedConditions.elementToBeClickable(By.linkText("New content")));
        newContentLink.click();

        // Enter "Draft Content" in the "Title" field
        WebElement titleField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("title")));
        titleField.sendKeys("Draft Content");

        // Click the "Save as draft" button
        WebElement saveAsDraftButton = wait
                .until(ExpectedConditions.elementToBeClickable(By.xpath("//*[contains(text(), 'Save as draft')]")));
        saveAsDraftButton.click();

        // Verify "Draft Content" is shown as first content in the "DRAFT" section of
        // the "Manage content" page
        WebElement draftContent = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//table/tbody/tr[td[contains(text(), 'Draft')]]/following-sibling::tr[1]/td[1]")));
        assertTrue(draftContent.getText().equals("Draft Content"),
                "'Draft Content' is not displayed as the first content in the 'Published' section");

        // Wait for the success notification to disappear
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("notification-success")));

        // Click the "Log out" link
        WebElement logoutLink = wait
                .until(ExpectedConditions.elementToBeClickable(By.xpath("//*[contains(text(), 'Log out')]")));
        logoutLink.click();
    }
}
