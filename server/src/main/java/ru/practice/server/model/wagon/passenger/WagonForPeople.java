package ru.practice.server.model.wagon.passenger;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practice.server.model.trains.Train;

import javax.persistence.*;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "wagon_people", discriminatorType = DiscriminatorType.STRING)
public abstract class WagonForPeople {
    @Id
    @GeneratedValue
    private UUID id;
    private int seatingCapacity; //количество сидячих мест
    private int tables;
    private boolean toilets;
    private boolean hasVentilation;
    
    @ManyToOne
    @JoinColumn(name = "train_id")
    @JsonIgnoreProperties("wagons")  // Игнорируем поле "wagons" во избежание циклической ссылки
    private Train train;

    public WagonForPeople(int seatingCapacity, int tables, boolean toilets, boolean hasVentilation) {
        this.seatingCapacity = seatingCapacity;
        this.tables = tables;
        this.toilets = toilets;
        this.hasVentilation = hasVentilation;
    }

}
