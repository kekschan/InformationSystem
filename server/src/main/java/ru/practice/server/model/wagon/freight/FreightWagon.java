package ru.practice.server.model.wagon.freight;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practice.server.model.trains.Train;
import ru.practice.server.model.trains.type.FreightTrain;

import javax.persistence.*;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "wagon_type", discriminatorType = DiscriminatorType.STRING)
public abstract class FreightWagon {

    @Id
    @GeneratedValue
    private UUID id;

    protected double volume;
    protected double length;
    protected double width;
    protected double height;

    @ManyToOne
    @JoinColumn(name = "train_id")
    /*@JsonIgnoreProperties("train")*/  // Игнорируем поле "train" во избежание циклической ссылки
    private FreightTrain freightTrain;

    public FreightWagon(double length, double width, double height) {
        this.length = length;
        this.width = width;
        this.height = height;
        this.volume = length * width * height;
    }
}
