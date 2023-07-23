package ru.practice.server.model.trains;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
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

    @Column(unique = true)
    private String trainNumber;



}
