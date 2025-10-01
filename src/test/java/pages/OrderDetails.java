package pages;

public class OrderDetails {
    private final String orderName;
    private final String orderQuantity;
    private final String orderTotal;

    public OrderDetails(String orderName, String orderQuantity, String orderTotal) {
        this.orderName = orderName;
        this.orderQuantity = orderQuantity;
        this.orderTotal = orderTotal;
    }

    // Getters
    public String getOrderName() {
        return orderName;
    }

    public String getOrderQuantity() {
        return orderQuantity;
    }

    public String getOrderTotal() {
        return orderTotal;
    }

}