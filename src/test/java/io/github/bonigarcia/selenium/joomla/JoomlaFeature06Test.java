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

class JoomlaFeature06Test extends JoomlaParent {

    // Feature: Site administration login
    // Scenario: Tries to login to the site administration area with wrong
    // credentials and fails
    @Test
    void testBadSiteAdminLogin() {
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

        // And enters "wrongpassword" in the "Password" field
        typeTextInField(By.id("mod-login-password"), "wrongpassword");

        // And clicks the "Log in" button
        click(By.xpath("//*[contains(@class, 'login-button')]"));

        // Then "Username and password do not match or you do not have an
        // account yet." is shown in a yellow box
        assertText(By.className("alert-message"),
                "Username and password do not match or you do not have an account yet.");

        // Given the previous assertion passed
        // Then the user closes the current tab
        closeCurrentTab();

        // And clicks the "Log out" link
        clickAuthorLogoutLink();

        // And clicks the "Log out" button
        clickLogoutButton();
    }

}
