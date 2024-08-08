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

class ClarolineFeature04Test {

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
    public void testAddCourse() {
        // Given the user is on the home page (/claroline11110/index.php)
        driver.get("http://localhost:3000/claroline11110/index.php");

        // When the user enters "admin" in the "Username" field
        WebElement usernameField = wait.until(ExpectedConditions
                .visibilityOfElementLocated(By.name("login")));
        usernameField.sendKeys("admin");

        // And enters "admin" in the "Password" field
        WebElement passwordField = driver.findElement(By.name("password"));
        passwordField.sendKeys("admin");

        // And clicks the "Enter" button
        WebElement enterButton = driver
                .findElement(By.xpath("//button[@type='submit']"));
        enterButton.click();

        // And clicks the "Platform administration" link
        WebElement platformAdminLink = wait
                .until(ExpectedConditions.visibilityOfElementLocated(
                        By.linkText("Platform administration")));
        platformAdminLink.click();

        // And clicks the "Create course" link
        WebElement createCourseLink = wait.until(ExpectedConditions
                .visibilityOfElementLocated(By.linkText("Create course")));
        createCourseLink.click();

        // And enters "Course001" in the "Course title" field
        WebElement courseTitleField = wait.until(ExpectedConditions
                .visibilityOfElementLocated(By.name("course_title")));
        courseTitleField.sendKeys("Course001");

        // And enters "001" in the "Code" field
        WebElement codeField = driver
                .findElement(By.name("course_officialCode"));
        codeField.sendKeys("001");

        // And selects "Sciences" from the "Unlinked categories" list
        Select unlinkedCategories = new Select(
                driver.findElement(By.id("mslist2")));
        unlinkedCategories.selectByVisibleText("Sciences");

        // And clicks the left pointing arrow
        WebElement leftArrowButton = driver
                .findElement(By.xpath("//a[@class='msremove']"));
        leftArrowButton.click();

        // And selects "Economics" from the "Unlinked categories" list
        unlinkedCategories.selectByVisibleText("Economics");

        // And clicks the left pointing arrow
        leftArrowButton.click();

        // And clicks the "Access allowed to anybody (even without login)" radio
        // button
        WebElement accessAllowedRadioButton = driver
                .findElement(By.xpath("//input[@id='access_reserved']"));
        accessAllowedRadioButton.click();

        // And clicks the "Allowed" radio button
        WebElement allowedRadioButton = driver
                .findElement(By.xpath("//input[@id='registration_true']"));
        allowedRadioButton.click();

        // And clicks the "Ok" button
        WebElement okButton = driver
                .findElement(By.xpath("//input[@type='submit']"));
        okButton.click();

        // Then "You have just created the course website : 001" is shown in a
        // green box
        WebElement successMessage = wait
                .until(ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//div[@class='claroDialogMsg msgSuccess']")));
        assertTrue(successMessage.getText()
                .contains("You have just created the course website : 001"));

        // Given the previous assertion passed
        // Then the user clicks the "Continue" link
        WebElement continueLink = driver.findElement(By.linkText("Continue"));
        continueLink.click();

        // And clicks the "Logout" link
        WebElement logoutLink = driver.findElement(By.linkText("Logout"));
        logoutLink.click();
    }

}
