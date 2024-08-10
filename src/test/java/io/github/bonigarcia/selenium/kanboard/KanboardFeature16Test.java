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

import static org.junit.jupiter.api.Assertions.assertTrue;

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
import org.openqa.selenium.support.ui.WebDriverWait;

class KanboardFeature16Test {

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
    void testAddNewPrivateProject() {
        // Navigate to the login page
        driver.get("http://localhost:8080/login");

        // Perform login
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.xpath("//button[text()='Sign in']")).click();

        // Add new private project
        driver.findElement(By.linkText("New personal project")).click();
        driver.findElement(By.name("name")).sendKeys("Test private 2");
        driver.findElement(By.xpath("//button[text()='Save']")).click();

        // Verify the project title and summary
        WebElement projectTitle = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//span[contains(text(), 'Test private 2')]")));
        assertTrue(projectTitle.isDisplayed(), "Project title 'Test private 2' should be displayed");

        WebElement projectSummary = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//ul[@class='panel']")));
        String summaryText = projectSummary.getText();
        assertTrue(summaryText.contains("This project is open"), "Summary should contain 'This project is open'");
        assertTrue(summaryText.contains("This project is personal"),
                "Summary should contain 'This project is personal'");
        assertTrue(summaryText.contains("Public access disabled"), "Summary should contain 'Public access disabled'");

        // Logout
        driver.findElement(By.xpath("//div[@title='admin']")).click();
        driver.findElement(By.linkText("Logout")).click();
    }

}
