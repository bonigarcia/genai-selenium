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
import org.openqa.selenium.support.ui.WebDriverWait;

class ClarolineFeature13Test {

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
    public void testAddUsersToSystem() {
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

        // Add testuser1
        createUser("testuser1", "Follow courses (student)");
        driver.findElement(By.linkText("Administration")).click();

        // Add testuser2
        createUser("testuser2", "Create courses (teacher)");
        driver.findElement(By.linkText("Administration")).click();

        // Add testuser3
        createUser("testuser3", "Manage platform (administrator)");

        // And clicks the "Back to admin page" link
        WebElement backToAdminPageLink = wait.until(ExpectedConditions
                .visibilityOfElementLocated(By.linkText("Back to admin page")));
        backToAdminPageLink.click();

        // And clicks the "User list" link
        WebElement userListLink = wait.until(ExpectedConditions
                .visibilityOfElementLocated(By.linkText("User list")));
        userListLink.click();

        // Then the page contains "testuser1"
        assertTrue(driver.getPageSource().contains("testuser1"));

        // And the page contains "testuser2"
        assertTrue(driver.getPageSource().contains("testuser2"));

        // And the page contains "testuser3"
        assertTrue(driver.getPageSource().contains("testuser3"));

        // Given the previous assertion passed
        // Then the user clicks the "Logout" link
        WebElement logoutLink = driver.findElement(By.linkText("Logout"));
        logoutLink.click();
    }

    private void createUser(String username, String role) {
        // And clicks the "Create user" link
        WebElement createUserLink = wait.until(ExpectedConditions
                .visibilityOfElementLocated(By.linkText("Create user")));
        createUserLink.click();

        // And enters username in the "Name" field
        WebElement nameField = wait.until(ExpectedConditions
                .visibilityOfElementLocated(By.name("lastname")));
        nameField.sendKeys(username);

        // And enters username in the "First name" field
        WebElement firstNameField = driver.findElement(By.name("firstname"));
        firstNameField.sendKeys(username);

        // And enters username in the "Username" field
        WebElement userNameField = driver.findElement(By.name("username"));
        userNameField.sendKeys(username);

        // And enters username in the "Password" field
        WebElement passwordField = driver.findElement(By.name("password"));
        passwordField.sendKeys(username);

        // And enters username in the "Password (Confirmation)" field
        WebElement passwordConfirmationField = driver
                .findElement(By.name("password_conf"));
        passwordConfirmationField.sendKeys(username);

        // And clicks the appropriate radio button for role
        WebElement roleRadioButton;
        switch (role) {
        case "Follow courses (student)":
            roleRadioButton = driver.findElement(By.cssSelector(
                    "input[name='platformRole'][value='student']"));
            break;
        case "Create courses (teacher)":
            roleRadioButton = driver.findElement(By.cssSelector(
                    "input[name='platformRole'][value='courseManager']"));
            break;
        case "Manage platform (administrator)":
            roleRadioButton = driver.findElement(By.cssSelector(
                    "input[name='platformRole'][value='platformAdmin']"));
            break;
        default:
            throw new IllegalArgumentException("Invalid role: " + role);
        }
        roleRadioButton.click();

        // And clicks the "Ok" button
        WebElement okButton = driver
                .findElement(By.xpath("//input[@type='submit']"));
        okButton.click();
    }

}
