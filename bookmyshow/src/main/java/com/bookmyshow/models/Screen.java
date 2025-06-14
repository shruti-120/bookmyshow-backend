package com.bookmyshow.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
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
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Screen {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    private int screenNumber;

    private int seatingCapacity;

    @ManyToOne
    @JoinColumn(name = "theatre_id", nullable = false)
    private Theatre theatre;
}
