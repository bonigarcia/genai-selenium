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

import static org.junit.jupiter.api.Assertions.assertTrue;

class BluditFeature18Test {

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
    void testLoginWithWrongCredentials() {
        driver.get("http://localhost:8080/admin");

        // Enter "admin" in the Username field
        driver.findElement(By.name("username")).sendKeys("admin");

        // Enter "password" in the Password field
        driver.findElement(By.name("password")).sendKeys("wrongpassword");

        // Click the "Login" button
        driver.findElement(By.xpath("//*[contains(text(), 'Login')]")).click();

        // Verify the error message is shown
        WebElement errorMessage = driver
                .findElement(By.xpath("//div[contains(text(), 'Username or password incorrect')]"));
        assertTrue(errorMessage.isDisplayed());

        // Log out (if applicable)
        // Note: This step might not be necessary if the login fails
        // driver.findElement(By.linkText("Log out")).click();
    }
}
