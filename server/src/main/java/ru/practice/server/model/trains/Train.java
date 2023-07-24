package ru.practice.server.model.trains;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practice.server.model.wagon.freight.FreightWagon;
import ru.practice.server.model.wagon.passenger.WagonForPeople;
import ru.practice.server.model.wagon.passenger.exemplar.mailWagon.MailWagon;
import ru.practice.server.model.wagon.passenger.exemplar.passengerWagon.PassengerWagon;
import ru.practice.server.model.wagon.passenger.exemplar.restaurantWagon.RestaurantWagon;

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
    private String trainNumber;

    @OneToMany(mappedBy = "train")
    @JsonIgnoreProperties("train")  // Игнорируем поле "train" во избежание циклической ссылки
    private List<FreightWagon> freightWagon = new ArrayList<>();

    @OneToMany(mappedBy = "train")
    @JsonIgnoreProperties("train")  // Игнорируем поле "train" во избежание циклической ссылки
    private List<MailWagon> mailWagons = new ArrayList<>();

    @OneToMany(mappedBy = "train")
    @JsonIgnoreProperties("train")  // Игнорируем поле "train" во избежание циклической ссылки
    private List<PassengerWagon> passengerWagons = new ArrayList<>();

    @OneToMany(mappedBy = "train")
    @JsonIgnoreProperties("train")  // Игнорируем поле "train" во избежание циклической ссылки
    private List<RestaurantWagon> restaurantWagons = new ArrayList<>();

}
