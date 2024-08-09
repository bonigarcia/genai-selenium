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

class JoomlaFeature05Test extends JoomlaParent {

    // Feature: Site administration login
    // Scenario: Login as the newly added user
    @Test
    void testLoginAsNewUser() {
        // Given the user is on the home page
        navigateHomePage();

        // When the user clicks the "Author Login" link
        clickAuthorLoginLink();

        // And enters "tuser01" in the "Username" field
        typeTextInField(By.id("username"), "tuser01");

        // And enters "tpassword" in the "Password" field
        typeTextInField(By.id("password"), "tpassword");

        // And clicks the "Sign in" button
        click(By.xpath("//button[@type='submit']"));

        // Then "Test User" is shown as value of the "Name" field
        assertValue(By.id("jform_name"), "Test User");

        // Given the previous assertion passed
        // Then the user clicks the "Author Login" link
        clickAuthorLoginLink();

        // And clicks the "Log out" button
        clickLogoutButton();
    }

}
