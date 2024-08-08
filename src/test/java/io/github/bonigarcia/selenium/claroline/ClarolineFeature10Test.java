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

class ClarolineFeature10Test {

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
    public void testAddQuestionsToExercise() {
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

        // And clicks the "001 - Course001" link
        WebElement courseLink = wait.until(ExpectedConditions
                .visibilityOfElementLocated(By.linkText("001 - Course001")));
        courseLink.click();

        // And clicks the icon to the left of the "Exercises" link
        WebElement exercisesIcon = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.id("CLQWZ")));
        exercisesIcon.click();

        // And clicks the icon on the "Modify" column in the second row of the
        // table
        WebElement modifyIcon = wait
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath(
                        "//table[@class='claroTable emphaseLine']/tbody/tr[2]//img[@alt='Modify']")));
        modifyIcon.click();

        // And clicks the "New question" link
        WebElement newQuestionLink = wait.until(ExpectedConditions
                .visibilityOfElementLocated(By.linkText("New question")));
        newQuestionLink.click();

        // And enters "Question 1" in the "Title" field
        WebElement titleField = wait.until(ExpectedConditions
                .visibilityOfElementLocated(By.name("title")));
        titleField.sendKeys("Question 1");

        // And clicks the "Multiple choice (Unique answer)" radio button
        WebElement multipleChoiceUniqueRadioButton = driver
                .findElement(By.id("MCUA"));
        multipleChoiceUniqueRadioButton.click();

        // And clicks the "Ok" button
        WebElement okButton = driver
                .findElement(By.xpath("//input[@type='submit']"));
        okButton.click();

        // And clicks the first radio button
        WebElement firstRadioButton = wait.until(ExpectedConditions
                .visibilityOfElementLocated(By.id("correct_1")));
        firstRadioButton.click();

        // And clears the first "Weighting" field
        WebElement firstWeightingField = driver.findElement(By.name("grade_1"));
        firstWeightingField.clear();

        // And enters "3" in the first "Weighting" field
        firstWeightingField.sendKeys("3");

        // And clears the second "Weighting" field
        WebElement secondWeightingField = driver
                .findElement(By.name("grade_2"));
        secondWeightingField.clear();

        // And enters "-3" in the second "Weighting" field
        secondWeightingField.sendKeys("-3");

        // And clicks the "Ok" button
        okButton = driver.findElement(By.xpath("//input[@type='submit']"));
        okButton.click();

        // Add Question 2
        newQuestionLink = wait.until(ExpectedConditions
                .visibilityOfElementLocated(By.linkText("New question")));
        newQuestionLink.click();
        titleField = wait.until(ExpectedConditions
                .visibilityOfElementLocated(By.name("title")));
        titleField.sendKeys("Question 2");
        WebElement trueFalseRadioButton = driver.findElement(By.id("TF"));
        trueFalseRadioButton.click();
        okButton = driver.findElement(By.xpath("//input[@type='submit']"));
        okButton.click();

        firstRadioButton = wait.until(ExpectedConditions
                .visibilityOfElementLocated(By.id("trueCorrect")));
        firstRadioButton.click();
        firstWeightingField = driver.findElement(By.name("trueGrade"));
        firstWeightingField.clear();
        firstWeightingField.sendKeys("3");
        secondWeightingField = driver.findElement(By.name("falseGrade"));
        secondWeightingField.clear();
        secondWeightingField.sendKeys("-3");
        okButton = driver.findElement(By.xpath("//input[@type='submit']"));
        okButton.click();

        // Add Question 3
        newQuestionLink = wait.until(ExpectedConditions
                .visibilityOfElementLocated(By.linkText("New question")));
        newQuestionLink.click();
        titleField = wait.until(ExpectedConditions
                .visibilityOfElementLocated(By.name("title")));
        titleField.sendKeys("Question 3");
        WebElement multipleChoiceMultipleRadioButton = driver
                .findElement(By.id("MCMA"));
        multipleChoiceMultipleRadioButton.click();
        okButton = driver.findElement(By.xpath("//input[@type='submit']"));
        okButton.click();

        WebElement addAnswerButton = driver.findElement(By.name("cmdAddAnsw"));
        addAnswerButton.click();
        WebElement firstCheckbox = wait.until(ExpectedConditions
                .visibilityOfElementLocated(By.name("correct_1")));
        firstCheckbox.click();
        firstWeightingField = driver.findElement(By.name("grade_1"));
        firstWeightingField.clear();
        firstWeightingField.sendKeys("3");
        WebElement thirdWeightingField = driver.findElement(By.name("grade_3"));
        thirdWeightingField.clear();
        thirdWeightingField.sendKeys("-3");
        okButton = driver.findElement(By.xpath("//input[@type='submit']"));
        okButton.click();

        // FIXME: This was not specified
        wait.until(ExpectedConditions
                .visibilityOfElementLocated(By.linkText("Exercises"))).click();

        // And clicks on the "Exercise 001" link
        WebElement exerciseLink = wait.until(ExpectedConditions
                .visibilityOfElementLocated(By.linkText("Exercise 001")));
        exerciseLink.click();

        // Then the first row of the table contains "Question 1" in the
        // "Question" column and "Multiple choice (Unique answer)" in the
        // "Answer type" column
        WebElement firstRowQuestionColumn = wait
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath(
                        "//table[@class='claroTable']/tbody/tr[1]/td[1]")));
        assertTrue(firstRowQuestionColumn.getText().contains("Question 1"));
        assertTrue(firstRowQuestionColumn.getText()
                .contains("Multiple choice (Unique answer)"));

        // And the second row of the table contains "Question 2" in the
        // "Question" column and "True/False" in the "Answer type" column
        WebElement secondRowQuestionColumn = driver
                .findElements(By.xpath(
                        "//table[@class='claroTable']/tbody/tr[1]/td[1]"))
                .get(1);
        assertTrue(secondRowQuestionColumn.getText().contains("Question 2"));
        assertTrue(secondRowQuestionColumn.getText().contains("True/False"));

        // And the third row of the table contains "Question 3" in the
        // "Question" column and "Multiple choice (Multiple answers)" in the
        // "Answer type" column
        WebElement thirdRowQuestionColumn = driver
                .findElements(By.xpath(
                        "//table[@class='claroTable']/tbody/tr[1]/td[1]"))
                .get(2);
        assertTrue(thirdRowQuestionColumn.getText().contains("Question 3"));
        assertTrue(thirdRowQuestionColumn.getText()
                .contains("Multiple choice (Multiple answers)"));

        // Given the previous assertion passed
        // Then the user clicks the "Logout" link
        WebElement logoutLink = driver.findElement(By.linkText("Logout"));
        logoutLink.click();
    }

}
