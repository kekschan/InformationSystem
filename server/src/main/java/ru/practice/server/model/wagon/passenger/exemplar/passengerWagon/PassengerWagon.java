package ru.practice.server.model.wagon.passenger.exemplar.passengerWagon;

import lombok.Data;
import ru.practice.server.model.trains.Train;
import ru.practice.server.model.wagon.passenger.WagonForPeople;

import java.util.UUID;

@Data
public abstract class PassengerWagon extends WagonForPeople {
    private int beds;
    public PassengerWagon( int seatingCapacity, int tables, boolean toilets, boolean hasVentilation, int beds) {
        super(seatingCapacity, tables, toilets, hasVentilation);
        this.beds = beds;
    }
}
