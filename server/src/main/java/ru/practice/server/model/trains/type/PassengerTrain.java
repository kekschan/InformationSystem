package ru.practice.server.model.trains.type;

import ru.practice.server.model.trains.Train;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@DiscriminatorValue("passenger")
public class PassengerTrain extends Train {

}
