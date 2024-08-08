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

class ClarolineFeature18Test {

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
    public void testEnrolUsersToCourse() {
        // Enroll testuser1
        enrollUserToCourse("testuser1", "testuser1", "Course001");

        // Enroll testuser2
        enrollUserToCourse("testuser2", "testuser2", "Course001");

        // Enroll testuser3
        enrollUserToCourse("testuser3", "testuser3", "Course001");

        // Verify the users in the course as admin
        verifyUsersInCourse("admin", "admin", "Course001", "testuser1",
                "testuser2", "testuser3");

        // Logout admin
        logout();
    }

    private void enrollUserToCourse(String username, String password,
            String course) {
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

        // And clicks the "Enrol on a new course" link
        WebElement enrolNewCourseLink = wait
                .until(ExpectedConditions.visibilityOfElementLocated(
                        By.linkText("Enrol on a new course")));
        enrolNewCourseLink.click();

        // And enters course into the "Search from keyword" field
        WebElement searchKeywordField = wait
                .until(ExpectedConditions.visibilityOfElementLocated(
                        By.name("coursesearchbox_keyword")));
        searchKeywordField.sendKeys(course);

        // And clicks the "Search" button
        WebElement searchButton = driver
                .findElement(By.xpath("//button[@type='submit']"));
        searchButton.click();

        // And clicks the enrolment button (icon) to the right of the "001 -
        // Course001" link
        WebElement enrolmentButton = wait
                .until(ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//img[@class='enrolment']")));
        enrolmentButton.click();

        // And clicks the logout button
        logout();
    }

    private void verifyUsersInCourse(String adminUsername, String adminPassword,
            String course, String... users) {
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

        // And clicks the button to the left of the "Users" link
        WebElement usersLink = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.id("CLUSR")));
        usersLink.click();

        // Then the page contains the enrolled users
        for (String user : users) {
            assertTrue(driver.getPageSource().contains(user));
        }
    }

    private void logout() {
        // Given the previous assertion passed
        // Then the user clicks the "Logout" link
        WebElement logoutLink = wait.until(ExpectedConditions
                .visibilityOfElementLocated(By.linkText("Logout")));
        logoutLink.click();
    }

}
