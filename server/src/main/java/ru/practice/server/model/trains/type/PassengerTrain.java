package ru.practice.server.model.trains.type;

import ru.practice.server.model.trains.Train;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("passenger")
public class PassengerTrain extends Train {

}
