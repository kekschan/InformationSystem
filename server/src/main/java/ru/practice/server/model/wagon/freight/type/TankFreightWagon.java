package ru.practice.server.model.wagon.freight.type;

import lombok.Data;
import ru.practice.server.model.wagon.freight.FreightWagon;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Data
@Entity
@DiscriminatorValue("tank")
public class TankFreightWagon extends FreightWagon {

    private static final double TANK_LENGTH = 7.0;
    private static final double TANK_WIDTH = 2.5;
    private static final double TANK_HEIGHT = 3.1;


    @Override
    protected double calculateVolume() {
        return length * width * height;
    }

    public TankFreightWagon() {
        super(TANK_LENGTH, TANK_WIDTH, TANK_HEIGHT);
    }
}
