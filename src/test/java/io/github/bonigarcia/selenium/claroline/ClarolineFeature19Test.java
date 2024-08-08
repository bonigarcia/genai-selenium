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
import org.openqa.selenium.support.ui.WebDriverWait;

class ClarolineFeature19Test {

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
    public void testMultipleUsersDoExercise() {
        // User testuser1 does the exercise
        doExercise("testuser1", "testuser1", 2, "FALSE", 3);

        // User testuser2 does the exercise
        doExercise("testuser2", "testuser2", 1, "FALSE", 3);

        // User testuser3 does the exercise
        doExercise("testuser3", "testuser3", 1, "TRUE", 2);

        // Admin checks the results
        checkExerciseResults("admin", "admin", "testuser1 testuser1", -3,
                "testuser2 testuser2", 0, "testuser3 testuser3", 6);
    }

    private void doExercise(String username, String password,
            int question1Option, String question2Option, int question3Option) {
        // Given the user is on the home page (/claroline11110/index.php)
        driver.get("http://localhost:3000/claroline11110/index.php");

        // When the user enters username in the "Username" field
        WebElement usernameField = wait.until(ExpectedConditions
                .visibilityOfElementLocated(By.name("login")));
        usernameField.sendKeys(username);

        // And enters password in the "Password" field
        WebElement passwordField = driver.findElement(By.name("password"));
        passwordField.sendKeys(password);

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

        // And clicks the radio button for "Question 1"
        WebElement question1RadioButton = wait
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath(
                        "(//input[@type='radio'])[" + question1Option + "]")));
        question1RadioButton.click();

        // And clicks the radio button for "Question 2"
        WebElement question2RadioButton = wait.until(ExpectedConditions
                .visibilityOfElementLocated(By.xpath("//input[@value='"
                        + question2Option + "' and @name='a_103']")));
        question2RadioButton.click();

        // And clicks the checkbox for "Question 3"
        WebElement question3Checkbox = wait
                .until(ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("(//input[@type='checkbox'])["
                                + question3Option + "]")));
        question3Checkbox.click();

        // And clicks the "Finish the test" button
        WebElement finishTestButton = driver
                .findElement(By.xpath("//input[@type='submit']"));
        finishTestButton.click();

        // And clicks the "Logout" button
        logout();
    }

    private void checkExerciseResults(String adminUsername,
            String adminPassword, String user1Name, int user1Score,
            String user2Name, int user2Score, String user3Name,
            int user3Score) {
        // Given the user is on the home page (/claroline11110/index.php)
        driver.get("http://localhost:3000/claroline11110/index.php");

        // When the user enters adminUsername in the "Username" field
        WebElement usernameField = wait.until(ExpectedConditions
                .visibilityOfElementLocated(By.name("login")));
        usernameField.sendKeys(adminUsername);

        // And enters adminPassword in the "Password" field
        WebElement passwordField = driver.findElement(By.name("password"));
        passwordField.sendKeys(adminPassword);

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

        // And clicks the icon in the "Statistics" column of the second row
        WebElement statisticsIcon = wait
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath(
                        "//table[@class='claroTable emphaseLine']/tbody/tr[2]//img[@alt='Statistics']")));
        statisticsIcon.click();

        // Then the column "Student" of the third row of the first table
        // contains user1Name
        WebElement studentColumn1 = wait
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath(
                        "//table[@class='claroTable emphaseLine']/tbody/tr[3]//td[1]")));
        assertEquals(user1Name, studentColumn1.getText());

        // And the column "Worst score" of the third row of the first table
        // contains user1Score
        WebElement worstScoreColumn1 = wait
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath(
                        "//table[@class='claroTable emphaseLine']/tbody/tr[3]//td[2]")));
        assertEquals(String.valueOf(user1Score), worstScoreColumn1.getText());

        // And the column "Student" of the fourth row of the first table
        // contains user2Name
        WebElement studentColumn2 = wait
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath(
                        "//table[@class='claroTable emphaseLine']/tbody/tr[4]//td[1]")));
        assertEquals(user2Name, studentColumn2.getText());

        // And the column "Worst score" of the fourth row of the first table
        // contains user2Score
        WebElement worstScoreColumn2 = wait
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath(
                        "//table[@class='claroTable emphaseLine']/tbody/tr[4]//td[2]")));
        assertEquals(String.valueOf(user2Score), worstScoreColumn2.getText());

        // And the column "Student" of the fifth row of the first table contains
        // user3Name
        WebElement studentColumn3 = wait
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath(
                        "//table[@class='claroTable emphaseLine']/tbody/tr[5]//td[1]")));
        assertEquals(user3Name, studentColumn3.getText());

        // And the column "Worst score" of the fifth row of the first table
        // contains user3Score
        WebElement worstScoreColumn3 = wait
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath(
                        "//table[@class='claroTable emphaseLine']/tbody/tr[5]//td[2]")));
        assertEquals(String.valueOf(user3Score), worstScoreColumn3.getText());

        // Given the previous assertion passed
        // Then the user clicks the "Logout" link
        logout();
    }

    private void logout() {
        // Given the previous assertion passed
        // Then the user clicks the "Logout" link
        WebElement logoutLink = wait.until(ExpectedConditions
                .visibilityOfElementLocated(By.linkText("Logout")));
        logoutLink.click();
    }

}
