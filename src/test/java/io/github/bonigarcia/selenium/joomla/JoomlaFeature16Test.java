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

class JoomlaFeature16Test extends JoomlaParent {

    // Feature: User management
    // Scenario: Tries to change the password of a user with two non
    // matching passwords and fails
    @Test
    void testChangePasswordDontMatch() {
        // Given the user is on the home page
        navigateHomePage();

        // When the user clicks the "Author Login" link
        clickAuthorLoginLink();

        // And enters "tuser01" in the "Username" field
        typeTextInField(By.id("username"), "tuser01");

        // And enters "newpassword01" in the "Password" field
        typeTextInField(By.id("password"), "newpassword01");

        // And clicks the "Sign in" button
        click(By.xpath("//button[@type='submit']"));

        // And enters "asdasdasd22" in the "Password (optional)" field
        typeTextInField(By.id("jform_password1"), "asdasdasd22");

        // And enters "zxczxczxc23" in the "Confirm Password (optional)" field
        typeTextInField(By.id("jform_password2"), "zxczxczxc23");

        // And clicks the "Submit" button
        click(By.xpath("//button[@type='submit']"));

        // Then "The passwords you entered do not match. Please enter your
        // desired password in the password field and confirm your entry by
        // entering it in the confirm password field." is shown on a yellow box
        assertText(By.className("alert-message"),
                "The passwords you entered do not match. "
                        + "Please enter your desired password in the password field "
                        + "and confirm your entry by entering it in the confirm "
                        + "password field.");

        // Given the previous assertion passed
        // Then the user clicks the "Author Login" link
        clickAuthorLoginLink();

        // And clicks the "Log out" button
        clickLogoutButton();

    }

}
