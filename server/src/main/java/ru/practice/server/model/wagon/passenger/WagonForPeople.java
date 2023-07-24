package ru.practice.server.model.wagon.passenger;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practice.server.model.trains.Train;

import javax.persistence.*;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class WagonForPeople {
    private int seatingCapacity; //количество сидячих мест
    private int tables;
    private boolean toilets;
    private boolean hasVentilation;

}
