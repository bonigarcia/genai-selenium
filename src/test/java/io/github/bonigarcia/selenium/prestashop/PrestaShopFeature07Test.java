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

class PrestaShopFeature07Test extends PrestaShopParent {

    // Feature: Catalog management
    // Scenario: Checks that taxes are computed correctly when adding a new
    // product using the italian reduced VAT rate (10%)
    @Test
    void testAddNewProductWithTax10() {
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

        // And clicks the "Add new product" button
        click(By.linkText("Add new product"));

        // And clicks the "Prices" link
        click(By.linkText("Prices"));

        // And enters "10" in the "Pre-tax retail price" field
        By priceTE = By.id("priceTE");
        clearField(priceTE);
        typeTextInField(priceTE, "10");

        // And selects "IT Reduced Rate (10%)" from the "Tax rule" dropdown
        // select
        select(By.id("id_tax_rules_group"), "IT Reduced Rate (10%)");

        // Then "11.00" is shown as value of the "Retail price with tax" field
        assertValue(By.id("priceTI"), "11.00");

        // Given the previous assertion passed
        // Then the user clicks on the user icon in the top right corner of the
        // page
        click(By.id("employee_infos"));

        // And clicks the "Sign out" link
        click(By.linkText("Sign out"));
    }

}
