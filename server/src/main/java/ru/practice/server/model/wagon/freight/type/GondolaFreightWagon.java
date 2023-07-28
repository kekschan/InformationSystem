package ru.practice.server.model.wagon.freight.type;

import ru.practice.server.model.wagon.freight.FreightWagon;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("gondola")
public class GondolaFreightWagon extends FreightWagon {

    private static final double GONDOLA_LENGTH = 5.0;
    private static final double GONDOLA_WIDTH = 2.0;
    private static final double GONDOLA_HEIGHT = 2.5;

    public GondolaFreightWagon() {
        super(GONDOLA_LENGTH, GONDOLA_WIDTH, GONDOLA_HEIGHT);
    }
}
