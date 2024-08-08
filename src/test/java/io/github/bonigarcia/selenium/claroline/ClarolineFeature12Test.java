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
package io.github.bonigarcia.selenium.claroline;

import static org.junit.jupiter.api.Assertions.assertEquals;

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

class ClarolineFeature12Test {

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
    public void testViewUserStatistics() {
        // Given the user is on the home page (/claroline11110/index.php)
        driver.get("http://localhost:3000/claroline11110/index.php");

        // When the user enters "user001" in the "Username" field
        WebElement usernameField = wait.until(ExpectedConditions
                .visibilityOfElementLocated(By.name("login")));
        usernameField.sendKeys("user001");

        // And enters "password001" in the "Password" field
        WebElement passwordField = driver.findElement(By.name("password"));
        passwordField.sendKeys("password001");

        // And clicks the "Enter" button
        WebElement enterButton = driver
                .findElement(By.xpath("//button[@type='submit']"));
        enterButton.click();

        // And clicks the "Manage my account" link
        WebElement manageAccountLink = wait.until(ExpectedConditions
                .visibilityOfElementLocated(By.linkText("Manage my account")));
        manageAccountLink.click();

        // And clicks the "View my statistics" button
        WebElement viewStatisticsButton = wait.until(ExpectedConditions
                .visibilityOfElementLocated(By.linkText("View my statistics")));
        viewStatisticsButton.click();

        // And selects "Course001" from the "Choose a course" select
        Select chooseCourseSelect = new Select(wait.until(ExpectedConditions
                .visibilityOfElementLocated(By.name("cidReq"))));
        chooseCourseSelect.selectByVisibleText("Course001");

        // Then "Exercise001" is shown on the first row of the "Exercises"
        // column of the "Exercises" table
        WebElement exerciseName = driver.findElements(By.xpath(
                "//table[@class='claroTable emphaseLine']/tbody/tr[1]/td[1]"))
                .get(2);
        assertEquals("Exercise 001", exerciseName.getText());

        // And "9" is shown on the first row of the "Best score" column of the
        // "Exercises" table
        WebElement bestScore = wait
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath(
                        "//table[@class='claroTable emphaseLine']/tbody/tr[1]/td[3]")));
        assertEquals("9", bestScore.getText());

        // Given the previous assertion passed
        // Then the user clicks the "Logout" link
        WebElement logoutLink = driver.findElement(By.linkText("Logout"));
        logoutLink.click();
    }

}
