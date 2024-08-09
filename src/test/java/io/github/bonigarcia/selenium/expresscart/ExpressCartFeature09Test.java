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
package io.github.bonigarcia.selenium.expresscart;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Duration;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

class ExpressCartFeature09Test {

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
    public void testAddReviewToProduct() {
        // Given the user is on the home page
        driver.get("http://localhost:3000");

        // When the user clicks on the account icon
        WebElement accountIcon = wait.until(ExpectedConditions
                .elementToBeClickable(By.xpath("//a[@class=\"btn\"]")));
        accountIcon.click();

        // And enters "test@test.com" in the "email address" field
        WebElement emailField = wait.until(
                ExpectedConditions.elementToBeClickable(By.name("email")));
        emailField.sendKeys("test@test.com");

        // And enters "test" in the "Password" field
        WebElement passwordField = driver.findElement(By.name("password"));
        passwordField.sendKeys("test");

        // And clicks the "Sign in" button
        WebElement signInButton = driver
                .findElement(By.id("customerloginForm"));
        signInButton.click();

        // And goes to the home page of the site
        driver.findElement(
                By.xpath("//button[@aria-label='Toggle navigation']")).click();
        WebElement home = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//a[normalize-space()='Home']")));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();",
                home);

        // And clicks the "NewProduct000" link
        WebElement productLink = wait.until(ExpectedConditions
                .elementToBeClickable(By.linkText("NewProduct000")));
        productLink.click();

        // And clicks the "Add review" button
        WebElement addReviewButton = wait.until(
                ExpectedConditions.elementToBeClickable(By.id("add-review")));
        addReviewButton.click();

        // And enters "Review000" in the "Title:" field
        WebElement reviewTitleField = wait.until(
                ExpectedConditions.elementToBeClickable(By.id("review-title")));
        reviewTitleField.sendKeys("Review000");

        // And enters "Description000" in the "Description:" field
        WebElement reviewDescriptionField = driver
                .findElement(By.id("review-description"));
        reviewDescriptionField.sendKeys("Description000");

        // And enters "5" in the "Rating:" field
        WebElement reviewRatingField = driver
                .findElement(By.id("review-rating"));
        reviewRatingField.sendKeys("5");

        // And clicks the "Add review" button
        WebElement submitReviewButton = driver.findElement(By.id("addReview"));
        submitReviewButton.click();

        // And clicks on "Recent reviews"
        WebElement recentReviewsLink = wait.until(ExpectedConditions
                .elementToBeClickable(By.linkText("Recent reviews")));
        recentReviewsLink.click();

        // Then "Title: Review000" is shown on the page
        WebElement reviewTitle = wait
                .until(ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//*[contains(text(), 'Title:')]/..")));
        assertTrue(reviewTitle.getText().contains("Review000"));

        // And "Description: Description000" is shown on the page
        WebElement reviewDescription = wait
                .until(ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//*[contains(text(), 'Description:')]/..")));
        assertTrue(reviewDescription.getText().contains("Description000"));

        // And "Rating: 5" is shown on the page
        WebElement reviewRating = wait
                .until(ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//*[contains(text(), 'Rating:')]/..")));
        assertTrue(reviewRating.getText().contains("5"));

        // Given the previous assertion passed
        // Then the user clicks on the user icon between the language selector
        // and the cart
        WebElement userIcon = wait
                .until(ExpectedConditions.elementToBeClickable(
                        By.xpath("//button[@class='btn dropdown-toggle']")));
        userIcon.click();

        // And clicks "Logout"
        WebElement logoutLink = wait.until(
                ExpectedConditions.elementToBeClickable(By.linkText("Logout")));
        logoutLink.click();
    }

}
