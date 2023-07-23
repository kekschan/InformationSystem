package ru.practice.server.model.trains.type;

import lombok.NoArgsConstructor;
import ru.practice.server.model.trains.Train;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.practice.server.model.wagon.freight.Wagon;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@DiscriminatorValue("freight")
@NoArgsConstructor
public class FreightTrain extends Train {
    @OneToMany(mappedBy = "train", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Wagon> wagons = new ArrayList<>();


}

