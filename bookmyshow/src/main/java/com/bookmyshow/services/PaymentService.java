package com.bookmyshow.services;

import java.math.BigDecimal;
import com.razorpay.RazorpayException;

public interface PaymentService {
    String createRazorpayOrder(BigDecimal amount, Long bookingId) throws RazorpayException;

    boolean verifySignature(String payload, String actualSignature);

    void processWebhook(String payload);
}
