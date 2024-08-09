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

class JoomlaFeature10Test extends JoomlaParent {

    // Feature: Content management
    // Scenario: Edits an article
    @Test
    void testEditArticle() {
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

        // And clicks the gear icon to the bottom right of "Test Article 01"
        click(By.linkText("Test Article 01"));
        click(By.xpath("//*[contains(@class, 'icon-cog')]"));

        // And clicks the "Edit" option
        // #the new text must be appended to the existing one
        click(By.xpath("//*[contains(@class, 'edit-icon')]"));

        // And enters "EDITED" in the main text editor
        click(By.cssSelector("a[title='Toggle editor']"));
        typeTextInField(By.id("jform_articletext"), "EDITED");

        // And clicks the "Save" button
        click(By.xpath("//*[contains(@class, 'btn btn-primary')]"));

        // Then "This is the body of the first article for testing the
        // platformEDITED" is shown as text of the first article
        assertText(By.xpath("//*[contains(@class, 'article-info')]"),
                "This is the body of the first article for testing the platformEDITED");

        // Given the previous assertion passed
        // Then the user clicks the "Author Login" link
        clickAuthorLoginLink();

        // And clicks the "Log out" button
        clickLogoutButton();

    }

}
