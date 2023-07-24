package ru.practice.server.model.wagon.passenger.exemplar.restaurantWagon.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * A DTO for the {@link ru.practice.server.model.wagon.passenger.exemplar.restaurantWagon.RestaurantWagon} entity
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RestaurantWagonDto {
    private boolean hasAlcohol;
    private String wagonType;
}