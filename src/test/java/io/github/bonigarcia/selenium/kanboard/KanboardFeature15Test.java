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

import static org.junit.jupiter.api.Assertions.assertEquals;
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
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

class KanboardFeature15Test {

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
    void testChangeApplicationLanguage() {
        // Navigate to the login page
        driver.get("http://localhost:8080/login");

        // Perform login
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.xpath("//button[text()='Sign in']")).click();

        // Navigate to application settings
        driver.findElement(By.xpath("//div[@title='admin']")).click();
        driver.findElement(By.linkText("Settings")).click();
        driver.findElement(By.linkText("Application settings")).click();

        // Change language to Italian
        WebElement languageDropdown = wait
                .until(ExpectedConditions.visibilityOfElementLocated(By.id("form-application_language")));
        Select select = new Select(languageDropdown);
        select.selectByVisibleText("Italiano");
        driver.findElement(By.xpath("//button[text()='Save']")).click();

        // Verify the language change to Italian
        WebElement languageLabel = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//label[contains(text(), 'Lingua')]")));
        assertTrue(languageLabel.isDisplayed(), "Language label should be 'Lingua'");

        languageDropdown = wait
                .until(ExpectedConditions.visibilityOfElementLocated(By.id("form-application_language")));
        select = new Select(languageDropdown);
        assertEquals("Italiano", select.getFirstSelectedOption().getText(), "Selected language should be 'Italiano'");

        // Change language back to English
        select.selectByVisibleText("English (US)");
        driver.findElement(By.xpath("//button[text()='Salva']")).click();

        // Verify the language change back to English
        languageLabel = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//label[contains(text(), 'Language')]")));
        assertTrue(languageLabel.isDisplayed(), "Language label should be 'Language'");

        languageDropdown = wait
                .until(ExpectedConditions.visibilityOfElementLocated(By.id("form-application_language")));
        select = new Select(languageDropdown);
        assertEquals("English (US)", select.getFirstSelectedOption().getText(),
                "Selected language should be 'English (US)'");

        // Logout
        driver.findElement(By.xpath("//div[@title='admin']")).click();
        driver.findElement(By.linkText("Logout")).click();
    }
}
