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
package io.github.bonigarcia.selenium.mantisbt;

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

class MantisBTFeature01Test {

    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeEach
    void setup() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-search-engine-choice-screen");
        driver = new ChromeDriver(options);
        wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        // Log in as administrator
        driver.get("http://localhost:3000/mantisbt/login_page.php");
        driver.findElement(By.name("username")).sendKeys("administrator");
        driver.findElement(By.name("password")).sendKeys("root");
        driver.findElement(By.xpath("//input[@type='submit']")).click();
    }

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void testAddNewUser() {
        // Given the user is on the home of the site and logged as administrator
        // Already logged in and on the home page from setUp()

        // When the user clicks the "Manage" link
        driver.findElement(By.linkText("Manage")).click();

        // And clicks the "Manage users" link
        wait.until(ExpectedConditions
                .visibilityOfElementLocated(By.linkText("Manage Users")))
                .click();

        // And clicks the "Create new account" button
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//input[@value='Create New Account']"))).click();

        // And enters "username001" in the "Username" field
        wait.until(ExpectedConditions
                .visibilityOfElementLocated(By.name("username")))
                .sendKeys("username001");

        // And enters "username001" in the "Real Name" field
        driver.findElement(By.name("realname")).sendKeys("username001");

        // And enters "username@username.it" in the "E-mail" field
        driver.findElement(By.name("email")).sendKeys("username@username.it");

        // And selects "updater" in the "Access Level" dropdown select
        Select accessLevelSelect = new Select(
                driver.findElement(By.name("access_level")));
        accessLevelSelect.selectByVisibleText("updater");

        // And clicks the "Create user" button
        driver.findElement(
                By.xpath("//input[@type='submit' and @value='Create User']"))
                .click();

        // And clicks the "Manage Users" link
        wait.until(ExpectedConditions
                .visibilityOfElementLocated(By.linkText("Manage Users")))
                .click();

        // Then "username001" is shown as username of the second user
        WebElement secondUserRow = driver
                .findElement(By.xpath("//table/tbody/tr[4]"));
        assertEquals("username001",
                secondUserRow.findElement(By.xpath(".//td[1]")).getText());

        // And "username001" is shown as real name of the second user
        assertEquals("username001",
                secondUserRow.findElement(By.xpath(".//td[2]")).getText());

        // And "username@username.it" is shown as e-mail of the second user
        assertEquals("username@username.it",
                secondUserRow.findElement(By.xpath(".//td[3]")).getText());

        // And "updater" is shown as access level of the second user
        assertEquals("updater",
                secondUserRow.findElement(By.xpath(".//td[4]")).getText());

        // Given the previous assertion passed (handled by assertions above)

        // Then the user clicks the "Logout" link
        driver.findElement(By.linkText("Logout")).click();
        assertTrue(driver.findElement(By.name("username")).isDisplayed(),
                "Logout was not successful.");
    }

}
