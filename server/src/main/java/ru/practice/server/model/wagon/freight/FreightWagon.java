package ru.practice.server.model.wagon.freight;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practice.server.model.trains.Train;
import ru.practice.server.model.trains.type.FreightTrain;
import ru.practice.server.model.wagon.Wagon;

import javax.persistence.*;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "wagon_type", discriminatorType = DiscriminatorType.STRING)
public abstract class FreightWagon extends Wagon {

    @Id
    @GeneratedValue
    private UUID id;

    protected Double volume;
    protected Double length;
    protected Double width;
    protected Double height;

    @ManyToOne
    @JoinColumn(name = "train_id")
    @JsonIgnoreProperties("freightWagon")  // Игнорируем поле "train" во избежание циклической ссылки
    private Train train;

    public FreightWagon(Double length, Double width, Double height) {
        this.length = length;
        this.width = width;
        this.height = height;
        this.volume = length * width * height;
    }
}
