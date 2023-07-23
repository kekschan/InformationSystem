package ru.practice.server.model.wagon.freight.type;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.practice.server.model.wagon.freight.Wagon;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Data
public class TankWagon extends Wagon {

    private static final double TANK_LENGTH = 7.0;
    private static final double TANK_WIDTH = 2.5;
    private static final double TANK_HEIGHT = 3.1;


    @Override
    protected double calculateVolume() {
        return length * width * height;
    }

    public TankWagon() {
        super(TANK_LENGTH, TANK_WIDTH, TANK_HEIGHT);
    }
}
