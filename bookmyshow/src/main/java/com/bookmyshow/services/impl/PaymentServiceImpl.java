package com.bookmyshow.services.impl;

import java.math.BigDecimal;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.bookmyshow.services.PaymentService;
import com.bookmyshow.utils.Helper;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PaymentServiceImpl implements PaymentService{
    
    @Value("${razorpay.publishable.key}")
    private String publishableKey;

    @Value("${razorpay.secret.key}")
    private String secretKey;

    @Value("${razorpay.webhook.secret}")
    private String webhookSecret;

    @Override
    public String createRazorpayOrder(BigDecimal amount, Long bookingId) throws RazorpayException {
        RazorpayClient razorpay = new RazorpayClient(publishableKey, secretKey);
        JSONObject orderRequest = new JSONObject();
        orderRequest.put("amount", amount.multiply(BigDecimal.valueOf(100)).intValue());
        orderRequest.put("currency", "INR");
        orderRequest.put("receipt", "booking_" + bookingId);
        orderRequest.put("payment_capture", true);

        Order order = razorpay.orders.create(orderRequest);
        return order.toString();
    }

    @Override
    public boolean verifySignature(String payload, String actualSignature) {
        try {
            String expected = Helper.calculateHMACSHA256(payload, webhookSecret);
            return expected.equals(actualSignature);
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public void processWebhook(String payload) {
        log.info("processing webhook");
    }
}
