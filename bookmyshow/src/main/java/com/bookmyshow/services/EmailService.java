package com.bookmyshow.services;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailService {

    private final JavaMailSender mailSender;

    public void sendBookingConfirmation(String toEmail, String bookingId, String seatDetails, String showTime, String movieName, String theatre) {
        String subject = "🎟️ Booking Confirmed - BookMyShow";
        String body = String.format("""
                <body>
                    <h2 style="color: #2e6c80;">✅ Your Booking is Confirmed!</h2>
                    <p><strong>Booking ID:</strong> %s</p>
                    <p><strong>Movie:</strong> %s</p>
                    <p><strong>Theatre:</strong> %s</p>
                    <p><strong>Show Time:</strong> %s</p>
                    <p><strong>Seats:</strong> %s</p>
                    <br>
                    <p>Thank you for booking with <strong>BookMyShow</strong>!</p>
                    <p style="font-size: 12px; color: #888;">This is an automated email. Please do not reply.</p>
                </body>
            </html>
            """, bookingId, movieName, theatre, showTime, seatDetails);

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(toEmail);
            helper.setSubject(subject);
            helper.setText(body, true);
            mailSender.send(message);
            log.info("Confirmation email sent to {}", toEmail);
        } catch (Exception e) {
            log.error("Failed to send confirmation email to {}: {}", toEmail, e);
        }
    }

    public void sendBookingCancelled(String toEmail, String bookingId) {
        String subject = "Booking Cancelled - BookMyShow";
        String body = String.format("""
            <html>
                <body>
                    <h2 style="color: red;">❌ Your Booking Has Been Cancelled</h2>
                    <p><strong>Booking ID:</strong> %s</p>
                    <p>We hope to see you again soon.</p>
                    <p style="font-size: 12px; color: #888;">BookMyShow • This is an automated message</p>
                </body>
            </html>
        """, bookingId);

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(toEmail);
            helper.setSubject(subject);
            helper.setText(body, true); 
            mailSender.send(message);
            log.info("Cancellation email sent to {}", toEmail);
        } catch (Exception e) {
            log.error("Failed to send cancellation email to {}: {}", toEmail, e);
        }
    }
}
