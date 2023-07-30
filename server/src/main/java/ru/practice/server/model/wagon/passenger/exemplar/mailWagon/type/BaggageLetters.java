package ru.practice.server.model.wagon.passenger.exemplar.mailWagon.type;

import ru.practice.server.model.wagon.passenger.exemplar.mailWagon.MailWagonWagon;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("baggageLetters")
public class BaggageLetters extends MailWagonWagon {

    private static final int SEATING_CAPACITY = 10;
    private static final int TABLES = 3;
    private static final boolean HAS_VENTILATION = true;
    private static final boolean TOILETS = true;
    private static final int ACCOMMODATION = 156526;

    public BaggageLetters() {
        super(SEATING_CAPACITY, TABLES, TOILETS, HAS_VENTILATION, ACCOMMODATION);
    }

    @Override
    public String getWagonType() {
        return "baggageLetters";
    }
}
