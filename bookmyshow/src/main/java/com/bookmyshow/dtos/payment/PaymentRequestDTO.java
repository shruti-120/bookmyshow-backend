package com.bookmyshow.dtos.payment;

import java.math.BigDecimal;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PaymentRequestDTO {
    private Long bookingId;
    private BigDecimal amount;
}
