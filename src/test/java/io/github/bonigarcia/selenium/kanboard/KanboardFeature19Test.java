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
package io.github.bonigarcia.selenium.kanboard;

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

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Duration;

class KanboardFeature19Test {

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
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    void testAddLocalUser() {
        // Navigate to the login page
        driver.get("http://localhost:8080/login");

        // Perform login
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.xpath("//button[text()='Sign in']")).click();

        // Navigate to user management
        driver.findElement(By.xpath("//div[@title='admin']")).click();
        driver.findElement(By.linkText("Users management")).click();
        driver.findElement(By.linkText("New user")).click();

        // Fill in user details
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("username"))).sendKeys("TestUser");
        driver.findElement(By.name("name")).sendKeys("User1");
        driver.findElement(By.name("email")).sendKeys("user@gmail.com");
        driver.findElement(By.name("password")).sendKeys("test123");
        driver.findElement(By.name("confirmation")).sendKeys("test123");
        driver.findElement(By.xpath("//button[text()='Save']")).click();

        // Verify the user details in the summary
        WebElement summary = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("user-section")));
        String summaryText = summary.getText();
        assertTrue(summaryText.contains("Login: TestUser"), "Summary should contain 'Login: TestUser'");
        assertTrue(summaryText.contains("Full Name: User1"), "Summary should contain 'Full Name: User1'");
        assertTrue(summaryText.contains("Email: user@gmail.com"), "Summary should contain 'Email: user@gmail.com'");

        // Logout
        driver.findElement(By.xpath("//div[@title='admin']")).click();
        driver.findElement(By.linkText("Logout")).click();
    }
}
