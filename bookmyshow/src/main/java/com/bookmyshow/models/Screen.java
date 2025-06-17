package com.bookmyshow.models;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(uniqueConstraints = {
    @UniqueConstraint(columnNames = {"screenNumber", "theatre_id"})
})
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Screen {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int screenNumber;

    private int rows;
    private int columns;

    @ManyToOne
    @JoinColumn(name = "theatre_id", nullable = false)
    private Theatre theatre;
}
