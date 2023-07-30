package ru.practice.server.model.trains.type;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import ru.practice.server.model.trains.Train;
import ru.practice.server.model.wagon.passenger.PeopleWagon;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
@Data
@Entity
@DiscriminatorValue("passenger")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class PassengerTrain extends Train {

    @OneToMany(mappedBy = "passengerTrain")
    /*@JsonIgnoreProperties("train")*/  // Игнорируем поле "train" во избежание циклической ссылки
    private List<PeopleWagon> peopleWagons = new ArrayList<>();
    @Override
    public String getTrainType() {
        return "passenger";
    }
}
