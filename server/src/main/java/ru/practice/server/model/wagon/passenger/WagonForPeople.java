package ru.practice.server.model.wagon.passenger;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.MappedSuperclass;

@Data
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
public abstract class WagonForPeople {
    private int seatingCapacity; //количество сидячих мест
    private int tables;
    private boolean toilets;
    private boolean hasVentilation;

}
