package com.airline.paymentservice;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final PaymentEventProducer paymentEventProducer;

    public PaymentService(
            PaymentRepository paymentRepository,
            PaymentEventProducer paymentEventProducer) {

        this.paymentRepository = paymentRepository;
        this.paymentEventProducer = paymentEventProducer;
    }

    public Payment createPayment(Payment payment) {

        if (payment.getPaymentDate() == null) {
            payment.setPaymentDate(LocalDateTime.now());
        }

        if (payment.getStatus() == null) {
            payment.setStatus(PaymentStatus.INITIATED);
        }

        Payment savedPayment = paymentRepository.save(payment);

        publishEvent(savedPayment);

        return savedPayment;
    }

    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }

    public Payment getPaymentById(Long id) {
        return paymentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Payment not found"));
    }

    public Payment updatePaymentStatus(Long id, PaymentStatus status) {

        Payment payment = getPaymentById(id);
        payment.setStatus(status);

        Payment savedPayment = paymentRepository.save(payment);

        publishEvent(savedPayment);

        return savedPayment;
    }

    public Payment refundPayment(Long id) {

        Payment payment = getPaymentById(id);
        payment.setStatus(PaymentStatus.REFUNDED);

        Payment savedPayment = paymentRepository.save(payment);

        publishEvent(savedPayment);

        return savedPayment;
    }

    private void publishEvent(Payment payment) {

        PaymentEvent event = new PaymentEvent(
                payment.getPaymentId(),
                payment.getBookingId(),
                payment.getAmount(),
                payment.getStatus(),
                LocalDateTime.now()
        );

        paymentEventProducer.publishPaymentEvent(event);
    }
}