package ru.practice.server.model.trains.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TrainDto {
    private String trainName;
    private String trainType;
    private String startingPoint;
    private String finishPoint;
    private Integer numberOfWagons;

}
