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

class JoomlaFeature04Test extends JoomlaParent {

    // Feature: User management
    // Scenario: Adds a new user
    @Test
    void testAddUser() {
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

        // And clicks the "Users" link
        click(By.xpath("//span[normalize-space()='Users']"));

        // And clicks the "New" button
        click(By.cssSelector(".btn.btn-small.button-new.btn-success"));

        // And enters "Test User" in the "Name" field
        String testUser = "Test User";
        typeTextInField(By.id("jform_name"), testUser);

        // And enters "tuser01" in the "Login Name" field
        String loginName = "tuser01";
        typeTextInField(By.id("jform_username"), loginName);

        // And enters "tpassword" in the "Password" field
        String passwd = "tpassword";
        typeTextInField(By.id("jform_password"), passwd);

        // And enters "tpassword" in the "Confirm Password" field
        typeTextInField(By.id("jform_password2"), passwd);

        // And enters "testmail@example.com" in the "Email" field
        String email = "testmail@example.com";
        typeTextInField(By.id("jform_email"), email);

        // And clicks the "Save & Close" button
        click(By.xpath("//*[contains(@class, 'button-save')]"));

        // Then "Test User", "tuser01" and "testmail@example.com" are shown
        // respectively as name, username and email in the second row of the
        // table
        assertText(By.xpath(
                "//table[@class='table table-striped']/tbody/tr[2]/td[2]"),
                testUser);
        assertText(By.xpath(
                "//table[@class='table table-striped']/tbody/tr[2]/td[3]"),
                loginName);
        assertText(By.xpath(
                "//table[@class='table table-striped']/tbody/tr[2]/td[6]"),
                email);

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
