package ru.practice.server.model.trains.type;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import ru.practice.server.model.trains.Train;
import ru.practice.server.model.wagon.freight.FreightWagon;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@DiscriminatorValue("freight")
public class FreightTrain extends Train {

}

