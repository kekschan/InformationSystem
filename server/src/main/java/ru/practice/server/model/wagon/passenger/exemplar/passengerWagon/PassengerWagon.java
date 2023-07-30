package ru.practice.server.model.wagon.passenger.exemplar.passengerWagon;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import ru.practice.server.model.wagon.passenger.PeopleWagon;
import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
public abstract class PassengerWagon extends PeopleWagon {
    @JsonProperty("beds")
    private Integer beds;
    public PassengerWagon(Integer seatingCapacity, Integer tables, boolean toilets, boolean hasVentilation, Integer beds) {
        super(seatingCapacity, tables, toilets, hasVentilation);
        this.beds = beds;
    }
}
