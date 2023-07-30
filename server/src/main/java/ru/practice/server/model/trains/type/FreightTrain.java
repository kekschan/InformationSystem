package ru.practice.server.model.trains.type;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import ru.practice.server.model.trains.Train;
import javax.persistence.*;
@Data
@Entity
@DiscriminatorValue("freight")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class FreightTrain extends Train {

    @Override
    public String getTrainType() {
        return "freight";
    }
}

