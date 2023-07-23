package ru.practice.server.model.wagon.freight.type;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.practice.server.model.wagon.freight.Wagon;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Data
public class FlatcarWagon extends Wagon {

    private static final double FLATCAR_LENGTH = 10.0;
    private static final double FLATCAR_WIDTH = 2.5;
    private static final double FLATCAR_HEIGHT = 3.0;

    public FlatcarWagon() {
        super(FLATCAR_LENGTH, FLATCAR_WIDTH, FLATCAR_HEIGHT);
    }
    @Override
    protected double calculateVolume() {
        return length * width * height;
    }


}
