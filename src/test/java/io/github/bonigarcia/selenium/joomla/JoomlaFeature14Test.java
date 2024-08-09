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

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.support.locators.RelativeLocator;

class JoomlaFeature14Test extends JoomlaParent {

    // Feature: Content management
    // Scenario: Assigns a category to an article
    @Test
    void testAssignCategory() {
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

        // And clicks the "Your Template" link
        click(By.linkText("Your Template"));

        // And selects "Test Category 001" from the "Category" dropdown select
        String title = "Test Category 001";
        html5Select(By.id("jform_catid_chzn"), title);

        // And clicks the "Save & Close" button
        click(By.xpath("//*[contains(@class, 'button-save')]"));

        // Then "Test Category 001" is shown below the "Your Template" link
        driver.findElement(RelativeLocator
                .with(By.xpath("//*[contains(text(),'" + title + "')]"))
                .below(getElement(By.linkText("Your Template"))));

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
