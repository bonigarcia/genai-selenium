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

class ClarolineFeature15Test {

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
    public void testSearchStudent() {
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

        // And clicks the "Platform administration" link
        WebElement platformAdminLink = wait
                .until(ExpectedConditions.visibilityOfElementLocated(
                        By.linkText("Platform administration")));
        platformAdminLink.click();

        // And clicks the "Advanced" link in the "Users" section
        WebElement advancedLink = wait.until(ExpectedConditions
                .visibilityOfElementLocated(By.linkText("Advanced")));
        advancedLink.click();

        // And enters "testuser1" into the "Name" field
        WebElement nameField = wait.until(ExpectedConditions
                .visibilityOfElementLocated(By.name("lastName")));
        nameField.sendKeys("testuser1");

        // And selects "Student" in the "Action" dropdown select
        Select actionSelect = new Select(driver.findElement(By.name("action")));
        actionSelect.selectByVisibleText("Student");

        // And clicks the "Search user" button
        WebElement searchUserButton = driver
                .findElement(By.xpath("//input[@type='submit']"));
        searchUserButton.click();

        // Then "testuser1" is shown in the "Name" column of the first row of
        // the table
        WebElement firstRowNameColumn = wait
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath(
                        "//table[@class='claroTable emphaseLine']/tbody/tr[1]//td[2]")));
        assertEquals("testuser1", firstRowNameColumn.getText());

        // Given the previous assertion passed
        // Then the user clicks the "Logout" link
        WebElement logoutLink = driver.findElement(By.linkText("Logout"));
        logoutLink.click();

    }

}
