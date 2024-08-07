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

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Duration;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

class BluditFeature20Test {

    private WebDriver driver;

    @BeforeEach
    void setup() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-search-engine-choice-screen");
        driver = new ChromeDriver(options);
    }

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    void testAssignCategoryToContent() {
        driver.get("http://localhost:8080/admin");

        // Enter "admin" in the Username field
        driver.findElement(By.name("username")).sendKeys("admin");

        // Enter "password" in the Password field
        driver.findElement(By.name("password")).sendKeys("password");

        // Click the "Login" button
        driver.findElement(By.xpath("//*[contains(text(), 'Login')]")).click();

        // Navigate to the Categories page
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
        WebElement content = wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Manage content")));
        content.click();

        // Click the "Create your own content" link
        driver.findElement(By.linkText("Create your own content")).click();

        // Click the "GENERAL" button
        WebElement genrealButton = driver.findElement(By.xpath("//*[contains(text(), ' General')]"));
        genrealButton.click();

        // Select "Category001" in the "CATEGORY" dropdown select
        Select categoryDropdown = new Select(driver.findElement(By.name("category")));
        categoryDropdown.selectByVisibleText("Category001");

        // Click the "Save" button
        WebElement saveButton = wait
                .until(ExpectedConditions.elementToBeClickable(By.xpath("//*[contains(text(), 'Save')]")));
        saveButton.click();

        // Navigate back to the Content page
        driver.findElement(By.cssSelector("body > a")).click(); // open navigation bar
        content = wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Manage content")));
        content.click();

        // Click the "Create your own content" link again
        driver.findElement(By.linkText("Create your own content")).click();

        // Click the "GENERAL" button again
        genrealButton = driver.findElement(By.xpath("//*[contains(text(), ' General')]"));
        genrealButton.click();

        // Verify that "Category001" is shown as the selected value of the "CATEGORY"
        // dropdown select
        categoryDropdown = new Select(driver.findElement(By.name("category")));
        assertEquals("Category001", categoryDropdown.getFirstSelectedOption().getText());

        // Log out
        WebElement logoutLink = wait
                .until(ExpectedConditions.elementToBeClickable(By.xpath("//*[contains(text(), 'Log out')]")));
        logoutLink.click();
    }
}
