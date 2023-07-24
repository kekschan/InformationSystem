package ru.practice.server.model.wagon.passenger.exemplar.passengerWagon.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * A DTO for the {@link ru.practice.server.model.wagon.passenger.exemplar.passengerWagon.PassengerWagon} entity
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PassengerWagonDto {
    private int beds;
    private String wagonType;
}