package com.bookmyshow.controllers.user;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bookmyshow.dtos.payment.PaymentRequestDTO;
import com.bookmyshow.services.PaymentService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("api/v1/payments")
@RequiredArgsConstructor
@Slf4j
public class UserPaymentController {
    
    private final PaymentService paymentService;

    @PostMapping("/initiate")
    public ResponseEntity<?> initiatePayment(@RequestBody PaymentRequestDTO requestDTO) {
        try{
            String orderResponse = paymentService.createRazorpayOrder(requestDTO.getAmount(), requestDTO.getBookingId());
            if(orderResponse != null) {
                return ResponseEntity.ok(orderResponse);
            } else {
                return ResponseEntity.badRequest().body("Failed to create Razorpay order");
            }
        } catch(Exception e) {
            log.error("An error occurred while creating the order.", e);
            return ResponseEntity.status(500).body("An error occured while creating the order.");
        }
    }

    @PostMapping("/webhook")
    public ResponseEntity<String> handleWebhook(@RequestBody String payload, 
                                                @RequestHeader("X-Razorpay-signature") String signature) {
        boolean isValid = paymentService.verifySignature(payload, signature);
        if(!isValid) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid signature");
        }

        paymentService.processWebhook(payload);
        return ResponseEntity.ok("Webhook received");
    }

}
