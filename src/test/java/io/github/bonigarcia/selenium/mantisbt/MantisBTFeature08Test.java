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

class MantisBTFeature08Test {

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
    public void testAddNewIssue() {
        // Given the user is on the home of the site and logged as administrator
        // Already logged in and on the home page from setUp()

        // When the user clicks the "Report Issue" link
        driver.findElement(By.linkText("Report Issue")).click();

        // And selects "Category001" in the "Category" dropdown select
        Select categorySelect = new Select(wait.until(ExpectedConditions
                .visibilityOfElementLocated(By.name("category_id"))));
        categorySelect.selectByVisibleText("Category001");

        // And selects "random" in the "Reproducibility" dropdown select
        Select reproducibilitySelect = new Select(
                driver.findElement(By.name("reproducibility")));
        reproducibilitySelect.selectByVisibleText("random");

        // And selects "crash" in the "Severity" dropdown select
        Select severitySelect = new Select(
                driver.findElement(By.name("severity")));
        severitySelect.selectByVisibleText("crash");

        // And selects "immediate" in the "Priority" dropdown select
        Select prioritySelect = new Select(
                driver.findElement(By.name("priority")));
        prioritySelect.selectByVisibleText("immediate");

        // And enters "Summary001" in the "Summary" field
        driver.findElement(By.name("summary")).sendKeys("Summary001");

        // And enters "description001" in the "Description" field
        driver.findElement(By.name("description")).sendKeys("description001");

        // And clicks the "Submit Report" button
        driver.findElement(
                By.xpath("//input[@type='submit' and @value='Submit Report']"))
                .click();

        // And clicks the "View Issues" link
        driver.findElement(By.linkText("View Issues")).click();

        // Then "Category001" is shown as category of the only issue
        WebElement issueTable = wait.until(ExpectedConditions
                .visibilityOfElementLocated(By.id("buglist")));
        WebElement categoryColumn = issueTable
                .findElement(By.xpath(".//tr[4]/td[6]"));
        assertEquals("Category001", categoryColumn.getText(),
                "The expected category is not displayed.");

        // And "crash" is shown as severity of the only issue
        WebElement severityColumn = issueTable
                .findElement(By.xpath(".//tr[4]/td[7]"));
        assertEquals("crash", severityColumn.getText(),
                "The expected severity is not displayed.");

        // And "Summary001" is shown as summary of the only issue
        WebElement summaryColumn = issueTable
                .findElement(By.xpath(".//tr[4]/td[10]"));
        assertEquals("Summary001", summaryColumn.getText(),
                "The expected summary is not displayed.");

        // Given the previous assertion passed (handled by assertions above)

        // Then the user clicks the "Logout" link
        driver.findElement(By.linkText("Logout")).click();
        assertTrue(driver.findElement(By.name("username")).isDisplayed(),
                "Logout was not successful.");
    }

}
