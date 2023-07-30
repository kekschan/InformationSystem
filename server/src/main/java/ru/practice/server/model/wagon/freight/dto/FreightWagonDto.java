package ru.practice.server.model.wagon.freight.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FreightWagonDto implements Serializable {
    private Double volume;
    private Double length;
    private Double width;
    private Double height;

    private String wagonType;
}

