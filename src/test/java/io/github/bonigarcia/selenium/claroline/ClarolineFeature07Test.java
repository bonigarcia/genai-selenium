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
package io.github.bonigarcia.selenium.claroline;

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

class ClarolineFeature07Test {

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
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void testAddEventToCourse() {
        // Given the user is on the home page (/claroline11110/index.php)
        driver.get("http://localhost:3000/claroline11110/index.php");

        // When the user enters "admin" in the "Username" field
        WebElement usernameField = wait.until(ExpectedConditions
                .visibilityOfElementLocated(By.name("login")));
        usernameField.sendKeys("admin");

        // And enters "admin" in the "Password" field
        WebElement passwordField = driver.findElement(By.name("password"));
        passwordField.sendKeys("admin");

        // And clicks the "Enter" button
        WebElement enterButton = driver
                .findElement(By.xpath("//button[@type='submit']"));
        enterButton.click();

        // And clicks the "001 - Course001" link
        WebElement courseLink = wait.until(ExpectedConditions
                .visibilityOfElementLocated(By.linkText("001 - Course001")));
        courseLink.click();

        // And clicks the icon to the left of the "Agenda" link
        WebElement agendaIcon = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.id("CLCAL")));
        agendaIcon.click();

        // And clicks the "Add an event" link
        WebElement addEventLink = wait.until(ExpectedConditions
                .visibilityOfElementLocated(By.linkText("Add an event")));
        addEventLink.click();

        // And enters "Exam 001" in the "Title" field
        WebElement titleField = wait.until(ExpectedConditions
                .visibilityOfElementLocated(By.name("title")));
        titleField.sendKeys("Exam 001");

        // And selects "31" from the select for days
        Select daySelect = new Select(driver.findElement(By.name("fday")));
        daySelect.selectByVisibleText("31");

        // And selects "May" from the select for months
        Select monthSelect = new Select(driver.findElement(By.name("fmonth")));
        monthSelect.selectByVisibleText("May");

        // And selects "2023" from the select for years
        Select yearSelect = new Select(driver.findElement(By.name("fyear")));
        yearSelect.selectByVisibleText("2023");

        // And enters "Genoa" in the "Location" field
        WebElement locationField = driver.findElement(By.name("location"));
        locationField.sendKeys("Genoa");

        // And clicks the "Ok" button
        WebElement okButton = driver.findElement(By.name("submitEvent"));
        okButton.click();

        // Then "Event added to the agenda" is shown in a green box
        WebElement successMessage = wait
                .until(ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//div[@class='claroDialogMsg msgSuccess']")));
        assertTrue(
                successMessage.getText().contains("Event added to the agenda"));

        // Given the previous assertion passed
        // Then the user clicks the "Logout" link
        WebElement logoutLink = driver.findElement(By.linkText("Logout"));
        logoutLink.click();
    }

}
