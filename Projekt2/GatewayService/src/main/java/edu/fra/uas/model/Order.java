package edu.fra.uas.model;

public class Order {
    private Long id;
    private Long userId;
    private String productTitle;
    private String status;

    // Konstruktoren, Getter und Setter
    public Order(Long id, Long userId, String productTitle, String status) {
        this.id = id;
        this.userId = userId;
        this.productTitle = productTitle;
        this.status = status;
    }

    // Getter und Setter
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setProductTitle(String productTitle) {
        this.productTitle = productTitle;
    }

    public String getProductTitle() {
        return productTitle;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Order{id=" + id + ", userId=" + userId + ", productTitle='" + productTitle + "', status='" + status + "'}";
    }
}
