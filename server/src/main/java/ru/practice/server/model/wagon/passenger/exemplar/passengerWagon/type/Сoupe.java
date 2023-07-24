package ru.practice.server.model.wagon.passenger.exemplar.passengerWagon.type;

import lombok.Data;
import ru.practice.server.model.wagon.passenger.exemplar.passengerWagon.PassengerWagon;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("coupe")
public class Сoupe extends PassengerWagon {
    private static final int SEATING_CAPACITY = 120;
    private static final int TABLES = 20;
    private static final boolean HAS_VENTILATION = true;
    private static final boolean TOILETS = true;
    private static final int BEDS = 40;

    public Сoupe() {
        super(SEATING_CAPACITY,TABLES,TOILETS,HAS_VENTILATION,BEDS);
    }
}
