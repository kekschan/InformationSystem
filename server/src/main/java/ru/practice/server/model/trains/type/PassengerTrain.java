package ru.practice.server.model.trains.type;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import ru.practice.server.model.trains.Train;
import ru.practice.server.model.wagon.passenger.exemplar.mailWagon.MailWagon;
import ru.practice.server.model.wagon.passenger.exemplar.passengerWagon.PassengerWagon;
import ru.practice.server.model.wagon.passenger.exemplar.restaurantWagon.RestaurantWagon;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
@DiscriminatorValue("passenger")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class PassengerTrain extends Train {


}
