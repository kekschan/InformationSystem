package ru.practice.server.model.wagon.passenger.exemplar.passengerWagon.type;

import ru.practice.server.model.wagon.passenger.exemplar.passengerWagon.PassengerWagon;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("reservedSeat")
public class ReservedSeat extends PassengerWagon {

    private static final int SEATING_CAPACITY = 150;
    private static final int TABLES = 15;
    private static final boolean HAS_VENTILATION = false;
    private static final boolean TOILETS = true;
    private static final int BEDS = 60;

    public ReservedSeat() {
        super(SEATING_CAPACITY, TABLES, TOILETS, HAS_VENTILATION, BEDS);
    }

    @Override
    public String getWagonType() {
        return "reservedSeat";
    }
}
