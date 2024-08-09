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

class JoomlaFeature20Test extends JoomlaParent {

    // Feature: Site menus management
    // Scenario: Tries to add a menu item without selecting a menu item type
    // and fails
    @Test
    void testAddMenuItem_EmptyMenuType() {
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

        // And clicks the "Menu(s)" link
        click(By.xpath("//span[normalize-space()='Menu(s)']"));

        // And clicks the "Menu Items" link
        click(By.linkText("Menu Items"));

        // And clicks the "New" button
        click(By.cssSelector(".btn.btn-small.button-new.btn-success"));

        // And enters "Test menu item" in the "Menu Title" field
        String title = "Test menu item";
        typeTextInField(By.id("jform_title"), title);

        // And selects "Main Menu" from the "Menu" dropdown select
        String menu = "Main Menu";
        html5Select(By.id("jform_menutype_chzn"), menu);

        // And clicks the "Save & Close" button
        click(By.xpath("//*[contains(@class, 'button-save')]"));

        // Then "Invalid field: Menu Item Type" is shown on a red box
        checkTextOnPage("Invalid field:");
        checkTextOnPage("Menu Item Type");
        click(By.id("toolbar-cancel"));

        // Given the previous assertion passed
        // Then the user clicks the down pointing arrow icon in the top-right
        // corner of the page
        clickPointingArrow();

        // And clicks the "Log out" button
        click(By.linkText("Logout"));

        // And closes the current tab
        closeCurrentTab();

        // And clicks the "Log out" link
        click(By.linkText("Log out"));

        // And clicks the "Log out" button
        clickLogoutButton();
    }

}
