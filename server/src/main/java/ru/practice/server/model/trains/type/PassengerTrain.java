package ru.practice.server.model.trains.type;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import ru.practice.server.model.trains.Train;
import javax.persistence.*;
@Data
@Entity
@DiscriminatorValue("passenger")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class PassengerTrain extends Train {

    @Override
    public String getTrainType() {
        return "passenger";
    }
}
