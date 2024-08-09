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
package io.github.bonigarcia.selenium.expresscart;

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

class ExpressCartFeature01Test {

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
    public void testAddUserToSystem() {
        // Given the user is on the administrative home page (/admin)
        driver.get("http://localhost:3000/admin");

        // When the user enters "owner@test.com" in the "email address" field
        WebElement emailField = wait.until(
                ExpectedConditions.elementToBeClickable(By.name("email")));
        emailField.sendKeys("owner@test.com");

        // And enters "test" in the "Password" field
        WebElement passwordField = driver.findElement(By.name("password"));
        passwordField.sendKeys("test");

        // And clicks the "Sign in" button
        WebElement signInButton = driver.findElement(By.id("loginForm"));
        signInButton.click();

        // And clicks the "+" icon to the right of the link "Users"
        WebElement addUserIcon = wait
                .until(ExpectedConditions.elementToBeClickable(
                        By.xpath("//a[@href='/admin/user/new']")));
        addUserIcon.click();

        // And enters "TestUser000" in the "Users name" field
        WebElement userNameField = wait.until(
                ExpectedConditions.elementToBeClickable(By.id("usersName")));
        userNameField.sendKeys("TestUser000");

        // And enters "test000@test.com" in the "User email" field
        WebElement userEmailField = driver.findElement(By.id("userEmail"));
        userEmailField.sendKeys("test000@test.com");

        // And enters "password" in the "User password" field
        WebElement userPasswordField = driver
                .findElement(By.id("userPassword"));
        userPasswordField.sendKeys("password");

        // And enters "password" in the "Password confirm" field
        WebElement passwordConfirmField = driver.findElement(
                By.cssSelector("input[data-match='#userPassword']"));
        passwordConfirmField.sendKeys("password");

        // And clicks the "Create" button
        WebElement createButton = driver.findElement(By.id("btnUserAdd"));
        createButton.click();

        // And clicks the "Users" link
        WebElement usersLink = wait.until(
                ExpectedConditions.elementToBeClickable(By.linkText("Users")));
        usersLink.click();

        // Then "User: TestUser000 - (test000@test.com)\nRole: User" is shown in
        // the third row of the table
        WebElement userRow = wait
                .until(ExpectedConditions.presenceOfElementLocated(
                        By.xpath("//ul[@class='list-group']/li[3]")));
        String userRowText = userRow.getText();
        assertTrue(
                userRowText.contains("User: TestUser000 - (test000@test.com)"));
        assertTrue(userRowText.contains("Role: User"));

        // Given the previous assertion passed
        // Then the user clicks the "Logout" link
        WebElement logoutLink = driver.findElement(By.linkText("Logout"));
        logoutLink.click();
    }

}
