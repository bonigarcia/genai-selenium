/*
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

class PrestaShopFeature23Test extends PrestaShopParent {

    // Feature: Register employee
    // Scenario: Adds a new employee
    @Test
    void testAddNewSupplier() {
        // Given the user is on the login page of the administration panel
        // (/administrator)
        navigateAdminPage();

        // When the user enters "admin@prestashop.com" in the "Email address"
        // field
        typeTextInField(By.id("email"), "admin@prestashop.com");

        // And enters "password" in the "Password" field
        typeTextInField(By.id("passwd"), "e2eW3Bt3s71nGB3nchM4rK");

        // And clicks the "Login" button
        clickSubmit();

        driver.manage().window().maximize();

        // And hovers on "Administration"
        click(By.id("subtab-AdminAdvancedParameters"));

        // And clicks on "Employees"
        click(By.id("subtab-AdminParentEmployees"));

        // And clicks the "Add new employee" button
        // And enters "John" into the "First Name" field
        // And enters "Smith" into the "Last Name" field
        // And enters "smith@gmail.com" into the "Email address" field
        // And enters "abcd!@#$23" into the "Password" field
        // And selects "Salesman" from the "Permission profile" dropdown select
        // And clicks on the "Save" button
        // Then "Successful creation" is shown on a green box

//        // And hovers on "Catalog"
//        hover(By.id("maintab-AdminCatalog"));
//
//        // And clicks on "Suppliers"
//        click(By.linkText("Suppliers"));
//
//        // And clicks the "Add new supplier" button
//        click(By.linkText("Add new supplier"));
//
//        // And enters "John" in the "Name" field
//        typeTextInField(By.id("name"), "John");
//
//        // And enters "Via Fianle Ligure" in the "Address" field
//        typeTextInField(By.id("address"), "Via Fianle Ligure");
//
//        // And enters "Finale Ligure" in the "City" field
//        typeTextInField(By.id("city"), "Finale Ligure");
//
//        // And enters "Italy" in the "Country" field
//        select(By.id("id_country"), "Italy");
//
//        // And clicks the "Save" button
//        click(By.id("supplier_form_submit_btn"));
//
//        // Then "Successful creation" is shown on a green box
//        assertText(By.className("alert-success"), "Successful creation");
//
//        // Given the previous assertion passed
//        // Then the user clicks on the user icon in the top right corner of the
//        // page
//        click(By.id("employee_infos"));
//
//        // And clicks the "Sign out" link
//        click(By.linkText("Sign out"));
    }

}
