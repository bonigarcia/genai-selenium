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

class MantisBTFeature09Test {

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
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void testAssignIssueToAdministrator() {
        // Given the user is on the home of the site and logged as administrator
        // Already logged in and on the home page from setUp()

        // When the user clicks the "View Issues" link
        driver.findElement(By.linkText("View Issues")).click();

        // And clicks the checkbox of the first issue

        WebElement firstIssueCheckbox = driver
                .findElements(By.xpath("//input[@type='checkbox']")).get(0);
        firstIssueCheckbox.click();

        // And selects "Assign" in the dropdown select
        Select actionSelect = new Select(driver.findElement(By.name("action")));
        actionSelect.selectByVisibleText("Assign");

        // And clicks the "OK" button
        driver.findElement(By.xpath("//input[@type='submit' and @value='OK']"))
                .click();

        // And clicks the "Assign Issues" button
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//input[@type='submit' and @value='Assign Issues']")))
                .click();

        // Then "assigned (administrator)" is shown in the "Status" column of
        // the table
        WebElement issueTable = wait.until(ExpectedConditions
                .visibilityOfElementLocated(By.id("buglist")));
        WebElement column = issueTable.findElement(By.xpath(".//tr[4]/td[8]"));
        assertTrue(column.getText().contains("assigned (administrator)"));

        // Given the previous assertion passed (handled by assertion above)

        // Then the user clicks the "Logout" link
        driver.findElement(By.linkText("Logout")).click();
        assertTrue(driver.findElement(By.name("username")).isDisplayed(),
                "Logout was not successful.");
    }

}
