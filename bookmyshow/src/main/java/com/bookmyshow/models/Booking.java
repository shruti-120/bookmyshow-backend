package com.bookmyshow.models;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.bookmyshow.enums.BookingStatus;

import jakarta.persistence.*;

import lombok.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    @ManyToOne
    @JoinColumn(name = "show_id", nullable = false)
    private Show show;

    @OneToMany(mappedBy = "booking", cascade = CascadeType.ALL)
    private List<BookedSeat> bookedSeats;

    private LocalDateTime bookingTime;

    @Enumerated(EnumType.STRING)
    private BookingStatus status;

    private BigDecimal totalAmount;
}
