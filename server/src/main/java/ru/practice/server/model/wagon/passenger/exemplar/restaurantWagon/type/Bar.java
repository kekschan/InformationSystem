package ru.practice.server.model.wagon.passenger.exemplar.restaurantWagon.type;

import lombok.Data;
import ru.practice.server.model.wagon.passenger.exemplar.restaurantWagon.RestaurantWagon;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("bar")
public class Bar extends RestaurantWagon {
    private static final int SEATING_CAPACITY = 40;
    private static final int TABLES = 20;
    private static final boolean HAS_VENTILATION = true;
    private static final boolean TOILETS = true;
    private static final boolean HAS_ALCOHOL = true;

    public Bar() {
        super(SEATING_CAPACITY, TABLES, TOILETS, HAS_VENTILATION, HAS_ALCOHOL);
    }
}
