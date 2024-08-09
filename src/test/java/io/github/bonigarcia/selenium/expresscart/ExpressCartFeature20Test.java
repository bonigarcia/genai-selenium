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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Duration;
import java.util.List;

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

class ExpressCartFeature20Test {

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
    public void testSearchProductViaMenu() {
        // Given the user is on the home page
        driver.get("http://localhost:3000");

        // When the user clicks the "Test Menu" link
        driver.findElement(
                By.xpath("//button[@aria-label='Toggle navigation']")).click();
        WebElement testMenuLink = wait.until(ExpectedConditions
                .elementToBeClickable(By.linkText("Test Menu")));
        testMenuLink.click();

        // And clicks the "Search" button
        WebElement searchButton = driver.findElement(By.id("btn_search"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();",
                searchButton);

        // Then "tag000" is shown to the right of "Category: "
        WebElement categoryText = wait
                .until(ExpectedConditions.elementToBeClickable(
                        By.xpath("//*[contains(@class, 'product-results')]")));
        assertTrue(categoryText.getText().contains("Category:"));
        assertTrue(categoryText.getText().contains("tag000"));

        // And "NewProduct000" is the only product shown on the page
        List<WebElement> products = wait
                .until(ExpectedConditions.visibilityOfAllElementsLocatedBy(
                        By.className("product-item")));
        assertEquals(1, products.size());

        WebElement productItem = products.get(0);
        assertTrue(productItem.getText().contains("NewProduct000"));
    }

}
