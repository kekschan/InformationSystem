package ru.practice.server.model.wagon.passenger.exemplar.mailWagon.type;

import ru.practice.server.model.wagon.passenger.exemplar.mailWagon.MailWagonWagon;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("envelopeLetters")
public class EnvelopeLetters extends MailWagonWagon {

    private static final int SEATING_CAPACITY = 4;
    private static final int TABLES = 3;
    private static final boolean HAS_VENTILATION = true;
    private static final boolean TOILETS = true;
    private static final int ACCOMMODATION  = 302526;

    public EnvelopeLetters() {
        super(SEATING_CAPACITY, TABLES, TOILETS, HAS_VENTILATION, ACCOMMODATION);
    }
}
