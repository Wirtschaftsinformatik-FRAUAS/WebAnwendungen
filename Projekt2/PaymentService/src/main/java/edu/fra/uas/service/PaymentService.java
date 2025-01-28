package edu.fra.uas.service;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.fra.uas.model.Payment;
import edu.fra.uas.repository.PaymentRepository;

@Service
public class PaymentService {
    private static final Logger log = org.slf4j.LoggerFactory.getLogger(PaymentService.class);

    @Autowired
    private PaymentRepository paymentRepository;

    private long nextPaymentId = 1;

    public Payment createPayment(Long orderId, Long userId, Double amount) {
        if (orderId == null || userId == null || amount == null) {
            throw new IllegalArgumentException("orderId, userid und amount dürfen nicht null sein");
        }
        
        Payment payment = new Payment(nextPaymentId++, orderId, userId, amount);
        log.debug("createPayment: " + payment);
        paymentRepository.put(payment.getId(), payment);
        return paymentRepository.get(payment.getId());
    }
    
    public Iterable<Payment> getAllPayments() {
        log.debug("getAllPayments");
        return paymentRepository.values();
    }

    public Payment getPaymentById(Long id) {
        log.debug("getPayment: " + id);
        return paymentRepository.get(id);
    }

    public Payment updatePayment(Long id, Payment payment) {
        log.debug("updatePayment: " + payment);
        paymentRepository.put(id, payment);
        return paymentRepository.get(id);
    }

    public Payment deletePayment(Long id) {
        log.debug("deletePayment: " + id);
        return paymentRepository.remove(id);
    }

     // Neue Methode hinzufügen, um Zahlungen nach Benutzer-ID zu erhalten
    public List<Payment> getPaymentsByUserId(Long userId) {
        log.debug("getPaymentsByUserId: " + userId);
        return paymentRepository.values().stream()
                .filter(payment -> payment.getUserId().equals(userId)) 
                .collect(Collectors.toList());
    }
}
