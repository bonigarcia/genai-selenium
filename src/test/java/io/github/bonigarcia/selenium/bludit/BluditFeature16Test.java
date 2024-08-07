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

import static org.junit.jupiter.api.Assertions.assertFalse;

import java.time.Duration;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

class BluditFeature16Test {

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
    void testDeleteUser() {
        driver.get("http://localhost:8080/admin");

        // Enter "admin" in the Username field
        driver.findElement(By.name("username")).sendKeys("admin");

        // Enter "password" in the Password field
        driver.findElement(By.name("password")).sendKeys("password");

        // Click the "Login" button
        driver.findElement(By.xpath("//*[contains(text(), 'Login')]")).click();

        // Navigate to the Users page
        driver.findElement(By.cssSelector("body > a")).click(); // open navigation bar
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
        WebElement usersLink = wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Manage users")));
        usersLink.click();

        // Click the second username (usertest) in the Users page
        WebElement secondUser = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//table/tbody/tr[2]/td/a[contains(text(), 'usertest')]")));
        secondUser.click();

        // Click the "Delete the user and all his pages" link
        WebElement deleteUser = wait
                .until(ExpectedConditions
                        .elementToBeClickable(By.xpath("//*[contains(text(), 'Delete the user and all his pages')]")));
        deleteUser.click();

        // Confirm the JavaScript alert
        Alert alert = driver.switchTo().alert();
        alert.accept();

        // Verify the Users page does not contain the user "usertest"
        List<WebElement> users = driver.findElements(By.cssSelector(".user-list .user-item"));
        boolean userExists = users.stream().anyMatch(user -> user.getText().contains("usertest"));
        assertFalse(userExists);

        // Click the "Log out" link
        WebElement logoutLink = wait
                .until(ExpectedConditions.elementToBeClickable(By.xpath("//*[contains(text(), 'Log out')]")));
        logoutLink.click();
    }
}
