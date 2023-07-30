package ru.practice.server.model.trains.type;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import ru.practice.server.model.trains.Train;
import ru.practice.server.model.wagon.freight.FreightWagon;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
@Data
@Entity
@DiscriminatorValue("freight")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class FreightTrain extends Train {

    @OneToMany(mappedBy = "freightTrain")
    /*@JsonIgnoreProperties("train")*/  // Игнорируем поле "train" во избежание циклической ссылки
    private List<FreightWagon> freightWagon = new ArrayList<>();

    @Override
    public String getTrainType() {
        return "freight";
    }
}

