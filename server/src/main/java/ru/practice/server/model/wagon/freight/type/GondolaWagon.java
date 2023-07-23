package ru.practice.server.model.wagon.freight.type;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.practice.server.model.wagon.freight.Wagon;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Data
public class GondolaWagon extends Wagon {

    private static final double GONDOLA_LENGTH = 5.0;
    private static final double GONDOLA_WIDTH = 2.0;
    private static final double GONDOLA_HEIGHT = 2.5;

    public GondolaWagon() {
        super(GONDOLA_LENGTH, GONDOLA_WIDTH, GONDOLA_HEIGHT);
    }
    @Override
    protected double calculateVolume() {
        return length * width * height;
    }
}
