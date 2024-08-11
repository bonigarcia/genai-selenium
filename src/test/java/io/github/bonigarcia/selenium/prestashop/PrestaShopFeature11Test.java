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

class PrestaShopFeature11Test extends PrestaShopParent {

    // Feature: Catalog management
    // Scenario: Tries to add an empty product attribute and fails
    @Test
    void testAddEmptyAttribute() {
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

        // And hovers on "Catalog"
        hover(By.id("maintab-AdminCatalog"));

        // And clicks on "Product Attributes"
        click(By.linkText("Product Attributes"));

        // And clicks the "Add new attribute" button
        click(By.xpath("//a[@title='Add new attribute']"));

        // And clicks the "Save" button
        click(By.id("attribute_group_form_submit_btn"));
        // attribute_group_form_submit_btn

        // Then an error message is shown on a red box
        // Then "2 errors This link_rewrite field is required at least in
        // English (English) This name field is required at least in English
        // (English)" is shown on a red box
        assertText(By.className("alert-danger"),
                "2 errors This link_rewrite field is required at least in English (English) "
                        + "This name field is required at least in English (English)");

        // Given the previous assertion passed
        // Then the user clicks on the user icon in the top right corner of the
        // page
        click(By.id("employee_infos"));

        // And clicks the "Sign out" link
        click(By.linkText("Sign out"));
    }

}
