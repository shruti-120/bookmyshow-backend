package com.bookmyshow.services.impl;

import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.bookmyshow.enums.BookingStatus;
import com.bookmyshow.models.Booking;
import com.bookmyshow.models.User;
import com.bookmyshow.repositories.BookingRepository;
import com.bookmyshow.repositories.UserRepository;
import com.bookmyshow.services.EmailService;
import com.bookmyshow.services.PaymentService;
import com.bookmyshow.utils.exceptionHandlers.ResourceNotFoundException;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.razorpay.Utils;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor 
@Transactional
@Slf4j
public class PaymentServiceImpl implements PaymentService{

    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    
    private final EmailService emailService;
    
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

        Booking booking = bookingRepository.findById(bookingId)
                            .orElseThrow(() -> new ResourceNotFoundException("Booking not found for ID: {}"+ bookingId));
        
        booking.setOrderId(order.get("id"));
        bookingRepository.save(booking);
        return order.toString();
    }

    @Override
    public boolean verifySignature(String payload, String razorpaySignature) {
        try {
            boolean isValid = Utils.verifyWebhookSignature(payload, razorpaySignature, webhookSecret);
            return isValid;  
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    @Transactional
    public void processWebhook(String payload) {
        try{
            JSONObject response  = new JSONObject(payload);
            String event = response.getString("event");

            if(!"payment.captured".equals(event)){
                log.info("Ignored Event: {}", event);
                return;
            }

            JSONObject payment = response.getJSONObject("payload")
                                         .getJSONObject("payment")
                                         .getJSONObject("entity");

            String razorpayOrderId = payment.getString("order_id");
            String paymentId = payment.getString("id");

            log.info("Captured payment for Razorpay Order ID: {}", razorpayOrderId);

            Booking booking = bookingRepository.findByOrderId(razorpayOrderId)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found for Order ID: " + razorpayOrderId));

            if (booking.getStatus() == BookingStatus.CONFIRMED) {
                log.info("Booking already confirmed: {}", booking.getId());
                return;
            }

            booking.setStatus(BookingStatus.CONFIRMED);
            bookingRepository.save(booking);

            log.info("Booking {} confirmed after successful payment. Razorpay payment ID: {}", booking.getId(), paymentId);

            Long userId = booking.getUserId();
            User user = userRepository.findById(userId)
                                      .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + userId));

            String toEmail = user.getEmail();

            String seatDetails = booking.getBookedSeats().stream()
                .map(bs -> bs.getSeat().getSeatNumber())
                .collect(Collectors.joining(", "));

            String movieTitle = booking.getShow().getMovie().getTitle();
            String theatreName = booking.getShow().getScreen().getTheatre().getName();
            String showTime = booking.getShow().getStartTime().format(DateTimeFormatter.ofPattern("dd MMM yyyy, hh:mm a"));

            emailService.sendBookingConfirmation(toEmail, booking.getId().toString(), seatDetails, showTime, movieTitle, theatreName);


        } catch (Exception e) {
            log.error("Failed to process webhook: {}", e.getMessage(), e);
        }
    }
}
