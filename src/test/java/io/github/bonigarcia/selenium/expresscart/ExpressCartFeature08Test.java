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
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

class ExpressCartFeature08Test {

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
    public void testSearchProductInStore() {
        // Given the user is on the home page
        driver.get("http://localhost:3000");

        // When the user enters "NewProduct000" in the "Search shop" field
        driver.findElement(
                By.xpath("//button[@aria-label='Toggle navigation']")).click();
        WebElement searchField = wait.until(
                ExpectedConditions.elementToBeClickable(By.id("frm_search")));
        searchField.sendKeys("NewProduct000");

        // And clicks the "Search" button
        WebElement searchButton = driver.findElement(By.id("btn_search"));
        searchButton.click();

        // Then "NewProduct000" is shown to the right of "Search results:"
        WebElement searchResultsText = wait
                .until(ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//*[contains(text(), 'Search results:')]")));
        assertTrue(searchResultsText.getText().contains("NewProduct000"));

        // And "NewProduct000" is the only product shown in the results
        List<WebElement> allResults = driver.findElements(
                By.xpath("//*[contains(@class, 'product-item')]"));
        assertEquals(1, allResults.size());
        assertTrue(allResults.get(0).getText().contains("NewProduct000"));
    }

}
