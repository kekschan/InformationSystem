package ru.practice.server.model.trains.type;

import ru.practice.server.model.trains.Train;

import javax.persistence.*;

@Entity
@DiscriminatorValue("freight")
public class FreightTrain extends Train {

}

