package com.airline.paymentservice;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class PaymentEvent {

    private Long paymentId;
    private Long bookingId;
    private BigDecimal amount;
    private PaymentStatus status;
    private LocalDateTime timestamp;

    public PaymentEvent() {
    }

    public PaymentEvent(
            Long paymentId,
            Long bookingId,
            BigDecimal amount,
            PaymentStatus status,
            LocalDateTime timestamp) {

        this.paymentId = paymentId;
        this.bookingId = bookingId;
        this.amount = amount;
        this.status = status;
        this.timestamp = timestamp;
    }

    public Long getPaymentId() {
        return paymentId;
    }

    public Long getBookingId() {
        return bookingId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public PaymentStatus getStatus() {
        return status;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}