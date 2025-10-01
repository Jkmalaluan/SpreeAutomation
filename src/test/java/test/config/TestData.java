package test.config;

public class TestData {
    // Application URL
    public static final String BASE_URL = "https://demo.spreecommerce.org/";
    
    // Test Account Data
    public static class UserData {
        public static final String PASSWORD = "test123";
    }
    
    // Payment Data
    public static class PaymentData {
        public static final String CARD_NUMBER = "4242424242424242";
        public static final String CARD_EXPIRY = "12/34";
        public static final String CARD_CVC = "123";
    }
}