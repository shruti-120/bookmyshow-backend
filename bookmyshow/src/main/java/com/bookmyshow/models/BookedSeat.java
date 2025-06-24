package com.bookmyshow.models;

import java.math.BigDecimal;

import jakarta.persistence.*;

import lombok.*;

@Entity
@Table(uniqueConstraints = {
    @UniqueConstraint(columnNames = {"booking_id", "seat_id"})
})
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class BookedSeat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "seat_id", nullable = false)
    private Seat seat;

    @ManyToOne
    @JoinColumn(name = "show_id", nullable = false)
    private Show show;

    @ManyToOne
    @JoinColumn(name = "booking_id", nullable = false)
    private Booking booking;

    private BigDecimal pricePaid;
}
