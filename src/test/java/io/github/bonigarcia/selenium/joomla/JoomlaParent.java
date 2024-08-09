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
package io.github.bonigarcia.selenium.joomla;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

class JoomlaParent {

    WebDriver driver;
    WebDriverWait wait;

    @BeforeEach
    void setup() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-search-engine-choice-screen");
        driver = new ChromeDriver(options);
        wait = new WebDriverWait(driver, Duration.ofSeconds(5));
    }

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    // *********************************************************************
    // Joomla specific methods
    // *********************************************************************
    void navigateHomePage() {
        driver.get("http://localhost:8080/");
    }

    void toggleNavigationBar() {
        click(By.xpath("//div[@class='navbar pull-left']"));
    }

    void clickAuthorLoginLink() {
        toggleNavigationBar();
        click(By.linkText("Author Login"));
    }

    void clickAuthorLogoutLink() {
        toggleNavigationBar();
        click(By.linkText("Log out"));
    }

    void clickSiteAdministrator() {
        toggleNavigationBar();
        click(By.linkText("Site Administrator"));
    }

    void clickCreateAPostLink() {
        toggleNavigationBar();
        click(By.linkText("Create a Post"));
    }

    void clickLogoutButton() {
        click(By.xpath("//button[@type='submit']"));
    }

    void clickPointingArrow() {
        click(By.xpath(
                "//ul[@class='nav nav-user pull-right']//a[@class='dropdown-toggle']"));
    }

    // *********************************************************************
    // Generic Selenium methods
    // *********************************************************************
    WebElement getElement(By element) {
        return wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    void jsClick(By element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();",
                getElement(element));
    }

    void click(By element) {
        getElement(element).click();
    }

    void typeTextInField(By element, String text) {
        getElement(element).sendKeys(text);
    }

    void assertValue(By element, String value) {
        assertThat(getElement(element).getAttribute("value").equals(value));
    }

    void assertValidationMessage(By element, String validationMessage) {
        assertThat(getElement(element).getAttribute("validationMessage")
                .equals(validationMessage));
    }

    void assertText(By element, String value) {
        assertThat(getElement(element).getText().contains(value));
    }

    // Manual pause for browser debugging
    // This method should only call during test development
    void waitForDebug() {
        try {
            Thread.sleep(Duration.ofMinutes(5).toMillis());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    List<String> getWindowHandles() {
        return new ArrayList<String>(driver.getWindowHandles());
    }

    void switchToTab(int index) {
        driver.switchTo().window(getWindowHandles().get(index));
    }

    void switchToNewTab() {
        switchToTab(getWindowHandles().size() - 1);
    }

    void closeCurrentTab() {
        driver.close();
        switchToTab(getWindowHandles().size() - 1);
    }

    boolean isElementPresent(By element) {
        try {
            driver.findElement(element);
            return true;
        } catch (org.openqa.selenium.NoSuchElementException e) {
            return false;
        }
    }

    void checkTextOnPage(String text) {
        List<WebElement> list = driver
                .findElements(By.xpath("//*[contains(text(),'" + text + "')]"));
        assertThat(list.size())
                .withFailMessage("\"" + text + "\" is not found in the page")
                .isGreaterThan(0);
    }

    void select(By element, String value) {
        Select select = new Select(getElement(element));
        select.selectByVisibleText(value);
    }

    void html5Select(By element, String value) {
        click(element);
        click(By.xpath("//li[contains(text(),'" + value + "')]"));
    }

    WebElement getElementByText(String text) {
        return getElement(By.xpath("//*[contains(text(),'" + text + "')]"));
    }

    void waitForIframe(String iFrameName) {
        wait.until(
                ExpectedConditions.frameToBeAvailableAndSwitchToIt(iFrameName));
    }

    void closedIFrame() {
        driver.switchTo().parentFrame();
    }

}
