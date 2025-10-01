package test.config;

import org.testng.annotations.DataProvider;

public class OrderTestData {
    @DataProvider(name = "orderData")
    public static Object[][] getOrderData() {
        return new Object[][] {
            // productName, size, country, firstName, lastName, address, city, postalCode
            {"Checkered Shirt", "M", "Philippines", "Kervin", "Malaluan", "7C Ilaya", "Muntinlupa", "1234"},
            
        };
    }
}