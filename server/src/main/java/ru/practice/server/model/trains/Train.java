package ru.practice.server.model.trains;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practice.server.model.wagon.freight.FreightWagon;
import ru.practice.server.model.wagon.passenger.PeopleWagon;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "train_type", discriminatorType = DiscriminatorType.STRING)
public abstract class Train {

    @Id
    @GeneratedValue
    private UUID id;
    @JsonProperty("trainType")
    public abstract String getTrainType();

    private String trainName;
    private String startingPoint;
    private String finishPoint;
    private Integer numberOfWagons;//пользователь с клиента задает количество вагонов в поезде

    @OneToMany(mappedBy = "train", cascade = CascadeType.REMOVE)
    @JsonIgnoreProperties("train")  // Игнорируем поле "train" во избежание циклической ссылки
    private List<FreightWagon> freightWagon = new ArrayList<>();

    @OneToMany(mappedBy = "train", cascade = CascadeType.REMOVE)
    @JsonIgnoreProperties("train")  // Игнорируем поле "train" во избежание циклической ссылки
    private List<PeopleWagon> peopleWagons = new ArrayList<>();

}
