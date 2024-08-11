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
import org.openqa.selenium.support.locators.RelativeLocator;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

class MantisBTFeature06Test {

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
    public void testAddCategoryToProject() {
        // Given the user is on the home of the site and logged as administrator
        // Already logged in and on the home page from setUp()

        // When the user clicks the "Manage" link
        driver.findElement(By.linkText("Manage")).click();

        // And clicks the "Manage Projects" link
        wait.until(ExpectedConditions
                .visibilityOfElementLocated(By.linkText("Manage Projects")))
                .click();

        // And clicks the "Project001" link
        wait.until(ExpectedConditions
                .visibilityOfElementLocated(By.linkText("Project001"))).click();

        // And enters "Category001" in the text field to the left of the "Add
        // Category" button
        By byCategory = By
                .xpath("//input[@type='submit' and @value='Add Category']");
        WebElement addCategory = driver.findElement(byCategory);
        WebElement categoryField = driver.findElement(
                RelativeLocator.with(By.tagName("input")).toLeftOf(byCategory));
        categoryField.sendKeys("Category001");

        // And clicks the "Add Category" button
        addCategory.click();

        // Then "Category001" is shown in the "Category" column of the table in
        // the "Categories" section
        WebElement categoryTable = driver
                .findElement(By.xpath("//td[contains(text(),'Category')]"));
        WebElement addedCategory = categoryTable.findElement(By.xpath(
                "./following::tr/td[1][contains(text(),'Category001')]"));

        // Verify that the category is added to the table
        assertTrue(addedCategory.isDisplayed(),
                "The category 'Category001' was not displayed in the Categories section.");

        // Given the previous assertion passed (handled by assertion above)

        // Then the user clicks the "Logout" link
        driver.findElement(By.linkText("Logout")).click();
        assertTrue(driver.findElement(By.name("username")).isDisplayed(),
                "Logout was not successful.");
    }

}
