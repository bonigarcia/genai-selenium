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

class PrestaShopFeature03Test extends PrestaShopParent {

    // Feature: Catalog management
    // Scenario: Changes the name of a product
    @Test
    void testEditProduct() {
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

        // And clicks on "Products"
        click(By.linkText("Products"));

        // And clicks the "Edit" button on the row of "Blue Jacket3"
        click(By.xpath(
                "//table[@id='table-product']//td[contains(text(),'Blue Jacket3')]/following-sibling::td[7]//a"));

        // And clears the "Name" field
        By name = By.id("name_1");
        clearField(name);

        // And enters "Deep Blue Jacket" in the "Name" field
        typeTextInField(name, "Deep Blue Jacket");

        // And clicks the "Save" button
        click(By.name("submitAddproduct"));

        // Then "Successful update" is shown on a green box
        assertText(By.className("alert-success"), "Successful creation");

        // Given the previous assertion passed
        // Then the user clicks on the user icon in the top right corner of the
        // page
        click(By.id("employee_infos"));

        // And clicks the "Sign out" link
        click(By.linkText("Sign out"));
    }

}
