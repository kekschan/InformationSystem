package ru.practice.server.model.wagon.passenger;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practice.server.model.trains.type.PassengerTrain;
import javax.persistence.*;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "wagon_type", discriminatorType = DiscriminatorType.STRING)
public abstract class PeopleWagon {

    @Id
    @GeneratedValue
    private UUID id;

    private int seatingCapacity; //количество сидячих мест
    private int tables;
    private boolean toilets;
    private boolean hasVentilation;

    @ManyToOne
    @JoinColumn(name = "train_id")
    /*@JsonIgnoreProperties("train")*/  // Игнорируем поле "train" во избежание циклической ссылки
    private PassengerTrain passengerTrain;

    public PeopleWagon(int seatingCapacity, int tables, boolean toilets, boolean hasVentilation) {
        this.seatingCapacity = seatingCapacity;
        this.tables = tables;
        this.toilets = toilets;
        this.hasVentilation = hasVentilation;
    }
}
