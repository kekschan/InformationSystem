package ru.practice.server.model.wagon.freight.type;

import ru.practice.server.model.wagon.freight.FreightWagon;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("covered")
public class CoveredFreightWagon extends FreightWagon {

    private static final double COVERED_LENGTH = 10.0;
    private static final double COVERED_WIDTH = 2.5;
    private static final double COVERED_HEIGHT = 3.0;

    public CoveredFreightWagon() {
        super(COVERED_LENGTH, COVERED_WIDTH, COVERED_HEIGHT);
    }
}
