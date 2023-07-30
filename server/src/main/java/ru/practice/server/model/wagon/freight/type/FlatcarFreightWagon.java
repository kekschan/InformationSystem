package ru.practice.server.model.wagon.freight.type;

import ru.practice.server.model.wagon.freight.FreightWagon;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("flatcar")
public class FlatcarFreightWagon extends FreightWagon {

    private static final double FLATCAR_LENGTH = 10.0;
    private static final double FLATCAR_WIDTH = 2.5;
    private static final double FLATCAR_HEIGHT = 3.0;

    public FlatcarFreightWagon() {
        super(FLATCAR_LENGTH, FLATCAR_WIDTH, FLATCAR_HEIGHT);
    }


    @Override
    public String getWagonType() {
        return "flatcar";
    }
}
