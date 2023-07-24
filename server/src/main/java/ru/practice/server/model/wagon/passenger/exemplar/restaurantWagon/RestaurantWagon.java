package ru.practice.server.model.wagon.passenger.exemplar.restaurantWagon;

import lombok.Data;
import ru.practice.server.model.wagon.passenger.WagonForPeople;
@Data
public abstract class RestaurantWagon extends WagonForPeople {
    private boolean hasAlcohol;

    public RestaurantWagon(int seatingCapacity, int tables, boolean toilets, boolean hasVentilation, boolean hasAlcohol) {
        super(seatingCapacity, tables, toilets, hasVentilation);
        this.hasAlcohol = hasAlcohol;
    }
}
