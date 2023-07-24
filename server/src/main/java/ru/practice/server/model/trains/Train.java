package ru.practice.server.model.trains;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practice.server.model.wagon.freight.FreightWagon;

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
    @JsonIgnore  // Игнорируем поле "wagons" при сериализации
    private List<FreightWagon> wagons = new ArrayList<>();



}
