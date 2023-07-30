package ru.practice.server.model.wagon.passenger.exemplar.passengerWagon.type;

import ru.practice.server.model.wagon.passenger.exemplar.passengerWagon.PassengerWagon;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("lux")
public class Lux extends PassengerWagon {

    private static final int SEATING_CAPACITY = 20;
    private static final int TABLES = 10;
    private static final boolean HAS_VENTILATION = true;
    private static final boolean TOILETS = true;
    private static final int BEDS = 10;

    public Lux() {
        super(SEATING_CAPACITY, TABLES, TOILETS, HAS_VENTILATION, BEDS);
    }
}
