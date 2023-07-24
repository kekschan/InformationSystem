package ru.practice.server.model.wagon.passenger.exemplar.mailWagon.type;

import lombok.Data;
import ru.practice.server.model.wagon.passenger.exemplar.mailWagon.MailWagon;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("baggageLetters")
public class BaggageLetters extends MailWagon {
    private static final int SEATING_CAPACITY = 10;
    private static final int TABLES = 3;
    private static final boolean HAS_VENTILATION = true;
    private static final boolean TOILETS = true;
    private static final int ACCOMMODATION  = 156526;

    public BaggageLetters() {
        super(SEATING_CAPACITY, TABLES, TOILETS, HAS_VENTILATION, ACCOMMODATION);
    }
}
