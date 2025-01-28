package edu.fra.uas.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import edu.fra.uas.model.Payment;
import edu.fra.uas.service.PaymentService;

import java.util.ArrayList;
import java.util.List;

@Controller
@SchemaMapping(typeName = "Payment")
public class PaymentGraphqlController {

    private static final Logger log = LoggerFactory.getLogger(PaymentGraphqlController.class);

    @Autowired
    private PaymentService paymentService;

    @QueryMapping(name="allPayments")
    public List<Payment> getAllPayments() {
        log.debug("getAllPayments() is called");
        Iterable<Payment> paymentIter = paymentService.getAllPayments();
        List<Payment> payments = new ArrayList<>();
        for (Payment payment : paymentIter) {
            payments.add(payment);
        } 
        return payments;
    }

    @QueryMapping(name="paymentById")
    public Payment getPaymentById(@Argument Long id) {
        log.debug("getPaymentById() is called");
        return paymentService.getPaymentById(id);
    }

    @MutationMapping
    public Payment addPayment(@Argument Long orderId,@Argument Long userId,  @Argument Double amount) {
        log.debug("addPayment() is called");
        return paymentService.createPayment(orderId,userId, amount);
    }

    @MutationMapping
    public Payment updatePayment(@Argument Long id, @Argument Long orderId, @Argument Double amount) {
        log.debug("updatePayment() is called");
        Payment payment = paymentService.getPaymentById(id);
        if (payment != null) {
            payment.setOrderId(orderId);
            payment.setAmount(amount);
            return paymentService.updatePayment(id, payment);
        }
        return null; // Optional: kann auch eine Ausnahme werfen
    }

    @MutationMapping
    public Integer deletePayment(@Argument Long id) {
        log.debug("deletePayment() is called");
        Payment payment = paymentService.deletePayment(id);
        return (payment != null) ? 1 : 0;
    }

    // Neue Methode hinzufügen, um Zahlungen nach Benutzer-ID zu erhalten
    @QueryMapping(name = "paymentsByUserId")
    public List<Payment> getPaymentsByUserId(@Argument Long userId) {
        log.debug("getPaymentsByUserId() is called for userId: {}", userId);
        List<Payment> payments = paymentService.getPaymentsByUserId(userId);
        if (payments.isEmpty()) {
            log.info("No payments found for userId: {}", userId);
        }
        return payments;
    }
    
}
