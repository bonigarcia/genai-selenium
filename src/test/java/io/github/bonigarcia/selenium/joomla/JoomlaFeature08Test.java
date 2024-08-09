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

class JoomlaFeature08Test extends JoomlaParent {

    // Feature: Content management
    // Scenario: Adds a new article
    @Test
    void testAddNewArticle() {
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

        // And clicks the "Create a Post" link
        clickCreateAPostLink();

        // And enters "Test Article 01" in the "Title" field
        String title = "Test Article 01";
        typeTextInField(By.id("jform_title"), title);

        // And enters "This is the body of the first article for testing the
        // platform" in the main text editor
        click(By.cssSelector("a[title='Toggle editor']"));
        String text = "This is the body of the first article for testing the platform";
        typeTextInField(By.id("jform_articletext"), text);

        // And clicks the "Save" button
        click(By.xpath("//*[contains(@class, 'btn btn-primary')]"));

        // Then "Test Article 01" is shown as title of the first article
        assertText(By.xpath("//*[contains(@class, 'page-header')]"), title);

        // And "This is the body of the first article for testing the platform"
        // is shown as text of the first article
        assertText(By.xpath("//*[contains(@class, 'article-info')]"), text);

        // Given the previous assertion passed
        // Then the user clicks the "Author Login" link
        clickAuthorLoginLink();

        // And clicks the "Log out" button
        clickLogoutButton();
    }

}
