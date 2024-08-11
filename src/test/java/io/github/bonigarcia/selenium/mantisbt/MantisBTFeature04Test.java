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

class MantisBTFeature04Test {

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
    public void testAddNewProject() {
        // Given the user is on the home of the site and logged as administrator
        // Already logged in and on the home page from setUp()

        // When the user clicks the "Manage" link
        driver.findElement(By.linkText("Manage")).click();

        // And clicks the "Manage Projects" link
        wait.until(ExpectedConditions
                .visibilityOfElementLocated(By.linkText("Manage Projects")))
                .click();

        // And clicks the "Create New Project" button
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//input[@value='Create New Project']"))).click();

        // And enters "Project001" in the "Project Name" field
        wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.name("name")))
                .sendKeys("Project001");

        // And selects "release" in the "Status" dropdown select
        Select statusSelect = new Select(driver.findElement(By.name("status")));
        statusSelect.selectByVisibleText("release");

        // And selects "public" in the "View Status" dropdown select
        Select viewStatusSelect = new Select(
                driver.findElement(By.name("view_state")));
        viewStatusSelect.selectByVisibleText("public");

        // And enters "Description" in the "Description" field
        driver.findElement(By.name("description")).sendKeys("Description");

        // And clicks the "Add Project" button
        driver.findElement(
                By.xpath("//input[@type='submit' and @value='Add Project']"))
                .click();

        // And clicks the "Manage" link
        driver.findElement(By.linkText("Manage")).click();

        // And clicks the "Manage Projects" link
        wait.until(ExpectedConditions
                .visibilityOfElementLocated(By.linkText("Manage Projects")))
                .click();

        // Then "Project001" is shown as project name
        WebElement projectRow = driver
                .findElement(By.xpath("//table/tbody/tr[3]"));
        assertEquals("Project001",
                projectRow.findElement(By.xpath(".//td[1]")).getText());

        // And "release" is shown as status
        assertEquals("release",
                projectRow.findElement(By.xpath(".//td[2]")).getText());

        // And "public" is shown as "View Status"
        assertEquals("public",
                projectRow.findElement(By.xpath(".//td[4]")).getText());

        // And "Description" is shown as "Description"
        WebElement descriptionElement = driver
                .findElement(By.xpath("//td[contains(text(),'Description')]"));
        assertTrue(descriptionElement.isDisplayed(),
                "The description was not displayed as expected.");

        // Given the previous assertion passed (handled by assertions above)

        // Then the user clicks the "Logout" link
        driver.findElement(By.linkText("Logout")).click();
        assertTrue(driver.findElement(By.name("username")).isDisplayed(),
                "Logout was not successful.");
    }

}
