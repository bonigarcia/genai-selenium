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
package io.github.bonigarcia.selenium.prestashop;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

class PrestaShopFeature04Test extends PrestaShopParent {

    // Feature: Localization management
    // Scenario: Adds a new state
    @Test
    void testAddNewState() {
        // Given the user is on the login page of the administration panel
        // (/administrator)
        navigateAdminPage();

        // When the user enters "admin@prestashop.com" in the "Email address"
        // field
        typeTextInField(By.id("email"), "admin@prestashop.com");

        // And enters "password" in the "Password" field
        typeTextInField(By.id("passwd"), "password");

        // And clicks the "Login" button
        clickSubmit();

        // And hovers on "Localization"
        hover(By.id("maintab-AdminParentLocalization"));

        // And clicks on "States"
        click(By.linkText("States"));

        // And clicks the "Add new state" button
        click(By.linkText("Add new state"));

        // And enters "Liguria" in the "Name" field
        typeTextInField(By.id("name"), "Liguria");

        // And enters "1121" in the "ISO code" field
        typeTextInField(By.id("iso_code"), "1121");

        // And selects "Italy" in the "Country" dropdown select
        select(By.name("id_country"), "Italy");

        // And selects "Europe" in the "Zone" dropdown select
        select(By.name("id_zone"), "Europe");

        // And clicks the "Save" button
        clickSubmit();

        // Then "Successful update" is shown on a green box
        assertText(By.className("alert-success"), "Successful update");

        // Given the previous assertion passed
        // Then the user clicks on the user icon in the top right corner of the
        // page
        click(By.id("employee_infos"));

        // And clicks the "Sign out" link
        click(By.linkText("Sign out"));
    }

}
