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

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.locators.RelativeLocator;

class JoomlaFeature11Test extends JoomlaParent {

    // Feature: Content management
    // Scenario: Deletes an article
    @Test
    void testDeleteArticle() {
        // Given the user is on the home page
        navigateHomePage();

        // When the user clicks the "Author Login" link
        clickAuthorLoginLink();

        // And enters "administrator" in the "Username" field
        typeTextInField(By.id("username"), "administrator");

        // And enters "root" in the "Password" field
        typeTextInField(By.id("password"), "root");

        // And clicks the "Sign in" button
        click(By.xpath("//button[@type='submit']"));

        // And clicks the "Site Administrator" link
        // #a new tab opens
        clickSiteAdministrator();
        switchToNewTab();

        // And enters "administrator" in the "Username" field
        typeTextInField(By.id("mod-login-username"), "administrator");

        // And enters "root" in the "Password" field
        typeTextInField(By.id("mod-login-password"), "root");

        // And clicks the "Log in" button
        click(By.xpath("//*[contains(@class, 'login-button')]"));

        // And clicks the "Articles" link
        click(By.xpath("//span[normalize-space()='Articles']"));

        // And clicks the checkbox to the left of "Test Article 01"
        WebElement checkbox = driver.findElement(
                RelativeLocator.with(By.xpath("//input[@type='checkbox']"))
                        .toLeftOf(getElement(By.linkText("Test Article 01"))));
        checkbox.click();

        // And clicks the "Trash" button
        click(By.xpath("//*[contains(@class, 'icon-trash')]"));

        // Then "1 article trashed" is shown in a green box
        assertText(By.className("alert-message"), "1 article trashed");

        // And "Test Article 01" is not shown on the page
        assertThat(isElementPresent(By.linkText("Test Article 01"))).isFalse();

        // Given the previous assertion passed
        // Then the user clicks the down pointing arrow icon in the top-right
        // corner of the page
        clickPointingArrow();

        // And clicks the "Log out" button
        click(By.linkText("Logout"));

        // And closes the current tab
        closeCurrentTab();

        // And clicks the "Log out" link
        clickAuthorLogoutLink();

        // And clicks the "Log out" button
        clickLogoutButton();
    }

}
