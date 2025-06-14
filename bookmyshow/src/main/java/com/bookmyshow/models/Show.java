package com.bookmyshow.models;

import com.bookmyshow.enums.SeatType;

import java.util.Map;

import jakarta.persistence.*;

import java.time.LocalDateTime;

import lombok.*;

@Entity
@Table(name = "shows")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Show implements java.io.Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime startTime;
    private LocalDateTime endTime;


    @ManyToOne
    @JoinColumn(name = "movie_id", nullable = false)
    private Movie movie;

    @ManyToOne
    @JoinColumn(name = "screen_id", nullable = false)
    private Screen screen;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "show_price_map", joinColumns = @JoinColumn(name = "show_id"))
    @MapKeyEnumerated(EnumType.STRING) 
    @MapKeyColumn(name = "seat_type")
    @Column(name = "price")
    private Map<SeatType, Double> priceMap;

}