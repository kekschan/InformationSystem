package ru.practice.server.model.wagon.freight;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practice.server.model.trains.type.FreightTrain;

import javax.persistence.*;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class Wagon {

    // В данном классе мы используем длину, ширину и высоту,
    // чтобы вычесть у определенного типа грузового выгона объем.
    // В дальнейшем это удобно будет применять вместе с грузами


    private UUID id;

    protected double volume;
    protected double length;
    protected double width;
    protected double height;


    private FreightTrain train;

    protected abstract double calculateVolume();
    // Конструктор для установки константных размеров вагона
    public Wagon(double length, double width, double height) {
        this.length = length;
        this.width = width;
        this.height = height;
        this.volume = calculateVolume();
    }


}
