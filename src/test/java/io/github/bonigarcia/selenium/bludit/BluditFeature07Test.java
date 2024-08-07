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
package io.github.bonigarcia.selenium.bludit;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertFalse;

class BluditFeature07Test {

	WebDriver driver;

	@BeforeEach
	void setup() {
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--disable-search-engine-choice-screen");
		driver = new ChromeDriver(options);
	}

	@AfterEach
	void teardown() {
		if (driver != null) {
			driver.quit();
		}
	}

	@Test
	void testDeleteContent() {
		driver.get("http://localhost:8080/admin");

		// Enter "admin" in the Username field
		driver.findElement(By.name("username")).sendKeys("admin");

		// Enter "password" in the Password field
		driver.findElement(By.name("password")).sendKeys("password");

		// Click the "Login" button
		driver.findElement(By.xpath("//*[contains(text(), 'Login')]")).click();

		// Click the "Content" link
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
        WebElement contentLink = wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Manage content")));
        contentLink.click();

		// Click the "Follow Bludit" link
		WebElement followBluditLink = wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Follow Bludit")));
		followBluditLink.click();

		// Click the "Delete" button
		WebElement deleteButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[contains(text(), 'Delete')]")));
		deleteButton.click();

		// Confirm the JavaScript alert
		Alert alert = wait.until(ExpectedConditions.alertIsPresent());
		alert.accept();

		// Verify "Follow Bludit" is not present in the "Manage content" page
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//table/tbody/tr[td[contains(text(), 'Follow Bludit')]]")));
		boolean isContentPresent = driver.findElements(By.xpath("//table/tbody/tr[td[contains(text(), 'Follow Bludit')]]")).size() > 0;
		assertFalse(isContentPresent, "'Follow Bludit' is still present in the 'Manage content' page");

		// Wait for the success notification to disappear
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("notification-success")));

		// Click the "Log out" link
		WebElement logoutLink = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[contains(text(), 'Log out')]")));
		logoutLink.click();
	}
}
