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

class PrestaShopFeature12Test extends PrestaShopParent {

    // Feature: Customer management
    // Scenario: Adds a new customer address
    @Test
    void testAddNewAddress() {
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

        // And hovers on "Customers"
        hover(By.id("maintab-AdminParentCustomer"));

        // And clicks on "Addresses"
        click(By.linkText("Addresses"));

        // And clicks the "Add new address" button
        click(By.xpath("//a[@title='Add new address']"));

        // And enters "pub@prestashop.com" in the "Customer email" field
        typeTextInField(By.id("email"), "pub@prestashop.com");

        // And enters "Test" in the "Address alias" field
        typeTextInField(By.id("alias"), "Test");

        // And enters "John" in the "First Name" field
        typeTextInField(By.id("firstname"), "John");

        // And enters "DOE" in the "Last Name" field
        typeTextInField(By.id("lastname"), "DOE");

        // And enters "Via Torino" in the "Address" field
        typeTextInField(By.id("address1"), "Via Torino");

        // And enters "12345" in the "Postcode" field
        typeTextInField(By.id("postcode"), "12345");

        // And enters "123456789" in the "Home phone" field
        typeTextInField(By.id("phone"), "123456789");

        // And enters "987654321" in the "Mobile phone" field
        typeTextInField(By.id("phone_mobile"), "987654321");

        // And enters "Bologna" in the "State" field
        select(By.id("id_state"), "Bologna");

        // And enters "Bologna" in the "City" field
        typeTextInField(By.id("city"), "Bologna");

        // And clicks the "Save" button
        click(By.id("address_form_submit_btn"));

        // Then "Successful creation" is shown on a green box
        assertText(By.className("alert-success"), "Successful creation");

        // Given the previous assertion passed
        // Then the user clicks on the user icon in the top right corner of the
        // page
        click(By.id("employee_infos"));

        // And clicks the "Sign out" link
        click(By.linkText("Sign out"));
    }

}
