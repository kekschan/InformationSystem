package ru.practice.server.model.wagon.freight.type;

import ru.practice.server.model.wagon.freight.FreightWagon;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("tank")
public class TankFreightWagon extends FreightWagon {

    private static final double TANK_LENGTH = 7.0;
    private static final double TANK_WIDTH = 2.5;
    private static final double TANK_HEIGHT = 3.1;

    public TankFreightWagon() {
        super(TANK_LENGTH, TANK_WIDTH, TANK_HEIGHT);
    }
}
