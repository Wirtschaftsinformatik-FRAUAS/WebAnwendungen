package edu.fra.uas.model;

public class Payment {
    private Long id;
    private Long userId;
    private Long orderId;
    private Double amount;

    public Payment(Long id, Long orderId, Long userId, Double amount) {
        this.id = id;
        this.orderId = orderId;
        this.amount = amount;
        this.userId =userId;
    }

public void setUserId(Long userId) {
    this.userId = userId;
}
public Long getUserId() {
    return userId;
}


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}
