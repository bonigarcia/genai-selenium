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
import org.openqa.selenium.support.ui.WebDriverWait;

class ClarolineFeature11Test {

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
    public void testUserDoesExercise() {
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

        // And clicks the "001 - Course001" link
        WebElement courseLink = wait.until(ExpectedConditions
                .visibilityOfElementLocated(By.linkText("001 - Course001")));
        courseLink.click();

        // And clicks the icon to the left of the "Exercises" link
        WebElement exercisesIcon = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.id("CLQWZ")));
        exercisesIcon.click();

        // And clicks the link "Exercise 001"
        WebElement exerciseLink = wait.until(ExpectedConditions
                .visibilityOfElementLocated(By.linkText("Exercise 001")));
        exerciseLink.click();

        // And clicks the first radio button for "Question 1"
        WebElement question1RadioButton = wait
                .until(ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("(//input[@type='radio'])[1]")));
        question1RadioButton.click();

        // And clicks the "True" radio button for "Question 2"
        WebElement question2RadioButton = wait
                .until(ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//input[@value='TRUE' and @name='a_103']")));
        question2RadioButton.click();

        // And clicks the first checkbox for "Question 3"
        WebElement question3Checkbox = wait
                .until(ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("(//input[@type='checkbox'])[1]")));
        question3Checkbox.click();

        // And clicks the "Finish the test" button
        WebElement finishTestButton = driver.findElement(By.name("cmdOk"));
        finishTestButton.click();

        // Then "Your total score is 9/9" is shown at the bottom of the page
        WebElement scoreMessage = wait
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath(
                        "//*[contains(text(),'Your total score is 9/9')]")));
        assertTrue(scoreMessage.isDisplayed());

        // Given the previous assertion passed
        // Then the user clicks the "Logout" link
        WebElement logoutLink = driver.findElement(By.linkText("Logout"));
        logoutLink.click();

    }

}
