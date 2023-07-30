package ru.practice.server.model.wagon.passenger.exemplar.restaurantWagon;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import ru.practice.server.model.wagon.passenger.PeopleWagon;
import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
public abstract class RestaurantWagonWagon extends PeopleWagon {
    private boolean hasAlcohol;

    public RestaurantWagonWagon(int seatingCapacity, int tables, boolean toilets, boolean hasVentilation, boolean hasAlcohol) {
        super(seatingCapacity, tables, toilets, hasVentilation);
        this.hasAlcohol = hasAlcohol;
    }
}
