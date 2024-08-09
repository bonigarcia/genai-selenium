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

class ExpressCartFeature19Test {

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
    public void testAddMenu() {
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

        // And clicks the "Menu" link
        WebElement menuLink = wait.until(
                ExpectedConditions.elementToBeClickable(By.linkText("Menu")));
        menuLink.click();

        // And enters "Test Menu" in the first empty field (placeholder: Contact
        // Us)
        WebElement firstEmptyField = wait.until(ExpectedConditions
                .presenceOfElementLocated(By.id("newNavMenu")));
        firstEmptyField.sendKeys("Test Menu");

        // And enters "/category/tag000" in the second empty field (placeholder:
        // /contact)
        WebElement secondEmptyField = wait.until(ExpectedConditions
                .presenceOfElementLocated(By.id("newNavLink")));
        secondEmptyField.sendKeys("/category/tag000");

        // And clicks the green "+" button
        WebElement addButton = driver.findElement(By.id("settings-menu-new"));
        addButton.click();

        // Then "Menu created successfully" appears in a green box at the bottom
        // of the screen
        WebElement successBox = wait
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath(
                        "//*[contains(text(), 'Menu created successfully')]")));
        assertTrue(successBox.isDisplayed());

        // Wait for the box to disappear (approx. 2 seconds)
        wait.until(ExpectedConditions.invisibilityOf(successBox));

        // Given the previous assertion passed
        // Then the user clicks the "Logout" link
        WebElement logoutLink = driver.findElement(By.linkText("Logout"));
        logoutLink.click();
    }

}
