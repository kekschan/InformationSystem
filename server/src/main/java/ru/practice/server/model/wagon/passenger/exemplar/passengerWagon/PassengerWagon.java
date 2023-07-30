package ru.practice.server.model.wagon.passenger.exemplar.passengerWagon;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import ru.practice.server.model.wagon.passenger.PeopleWagon;
import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
public abstract class PassengerWagon extends PeopleWagon {

    private int beds;
    public PassengerWagon(int seatingCapacity, int tables, boolean toilets, boolean hasVentilation, int beds) {
        super(seatingCapacity, tables, toilets, hasVentilation);
        this.beds = beds;
    }
}
