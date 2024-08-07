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
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Duration;

class BluditFeature10Test {

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
    void testAddUserLinksToSocialNetworks() {
        driver.get("http://localhost:8080/admin");

        // Enter "admin" in the Username field
        driver.findElement(By.name("username")).sendKeys("admin");

        // Enter "password" in the Password field
        driver.findElement(By.name("password")).sendKeys("password");

        // Click the "Login" button
        driver.findElement(By.xpath("//*[contains(text(), 'Login')]")).click();

        // Click the "Users" link
        driver.findElement(By.cssSelector("body > a")).click(); // open navigation bar
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        WebElement usersLink = wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Manage users")));
        usersLink.click();

        // Click the first username (admin)
        By byAdmin = By.xpath("//table/tbody/tr/td/a[contains(text(), 'admin')]");
        WebElement firstUser = wait.until(ExpectedConditions.elementToBeClickable(byAdmin));
        firstUser.click();

        // Enter social network links
        WebElement facebook = driver.findElement(By.name("facebook"));
        facebook.clear();
        String fakeFacebookUser = "some_fake_user_name_52432562135863";
        facebook.sendKeys(fakeFacebookUser);

        WebElement instagram = driver.findElement(By.name("instagram"));
        instagram.clear();
        String fakeInstagram = "https://instagram.com/some_fake_user_name_52432562135863";
        instagram.sendKeys(fakeInstagram);
        driver.findElement(By.xpath("//*[contains(text(), 'Save')]")).click();

        // Verify the entered links
        driver.findElement(By.cssSelector("body > a")).click(); // open navigation bar
        usersLink = wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Manage users")));
        usersLink.click();
        firstUser = wait.until(ExpectedConditions.elementToBeClickable(byAdmin));
        firstUser.click();

        WebElement facebookField = driver.findElement(By.name("facebook"));
        WebElement instagramField = driver.findElement(By.name("instagram"));

        assertEquals(fakeFacebookUser, facebookField.getAttribute("value"));
        assertEquals(fakeInstagram, instagramField.getAttribute("value"));

        // Click the "Log out" link
        WebElement logoutLink = wait
                .until(ExpectedConditions.elementToBeClickable(By.xpath("//*[contains(text(), 'Log out')]")));
        logoutLink.click();
    }
}
