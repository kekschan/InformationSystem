package ru.practice.server.model.wagon.passenger.exemplar.passengerWagon;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practice.server.model.trains.Train;
import ru.practice.server.model.wagon.passenger.WagonForPeople;

import javax.persistence.*;
import java.util.UUID;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "wagon_type", discriminatorType = DiscriminatorType.STRING)
public abstract class PassengerWagon extends WagonForPeople {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "train_id")
    @JsonIgnoreProperties("passengerWagons")  // Игнорируем поле "passengerWagons" во избежание циклической ссылки
    private Train train;
    private int beds;
    public PassengerWagon( int seatingCapacity, int tables, boolean toilets, boolean hasVentilation, int beds) {
        super(seatingCapacity, tables, toilets, hasVentilation);
        this.beds = beds;
    }
}
