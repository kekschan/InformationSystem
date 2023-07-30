package ru.practice.server.model.wagon.passenger.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practice.server.model.wagon.passenger.PeopleWagon;

import java.io.Serializable;

/**
 * A DTO for the {@link PeopleWagon} entity
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PeopleWagonDto implements Serializable {
    private Integer seatingCapacity;
    private Integer tables;
    private boolean toilets;
    private boolean hasVentilation;
    private String wagonType;

    private Integer accommodation;
    private Integer beds;
    private boolean hasAlcohol;
}