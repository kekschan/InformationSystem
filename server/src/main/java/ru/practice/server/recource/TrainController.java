package ru.practice.server.recource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practice.server.model.trains.Train;
import ru.practice.server.model.trains.type.FreightTrain;
import ru.practice.server.model.trains.type.PassengerTrain;
import ru.practice.server.repository.TrainRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/trains")
public class TrainController {

    private final TrainRepository trainRepository;

    @Autowired
    public TrainController(TrainRepository trainRepository) {
        this.trainRepository = trainRepository;
    }

    @PostMapping("/freight")
    public ResponseEntity<String> addFreightTrain(@RequestBody FreightTrain freightTrain) {
        if (freightTrain.getTrainNumber() == null || freightTrain.getTrainNumber().isEmpty()) {
            return ResponseEntity.badRequest().body("Грузовой поезд не удалось добавить!");
        }
        if (isTrainNumberUnique(freightTrain.getTrainNumber())) {
            trainRepository.save(freightTrain);
            return ResponseEntity.ok("Грузовой поезд успешно добавлен!");
        } else {
            return ResponseEntity.badRequest().body("Поезд с таким названием уже существует!");
        }
    }

    @PostMapping("/passenger")
    public ResponseEntity<String> addPassengerTrain(@RequestBody PassengerTrain passengerTrain) {
        if (passengerTrain.getTrainNumber() == null || passengerTrain.getTrainNumber().isEmpty()) {
            return ResponseEntity.badRequest().body("Пассажирский поезд не удалось добавить!");
        }
        if (isTrainNumberUnique(passengerTrain.getTrainNumber())) {
            trainRepository.save(passengerTrain);
            return ResponseEntity.ok("Пассажирский поезд успешно добавлен!");
        } else {
            return ResponseEntity.badRequest().body("Поезд с таким названием уже существует!");
        }
    }

    @GetMapping
    public ResponseEntity<List<Train>> getAllTrains() {
        List<Train> trains = trainRepository.findAll();
        return ResponseEntity.ok(trains);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTrainById(@PathVariable UUID id) {
        Optional<Train> optionalTrain = trainRepository.findById(id);
        if (optionalTrain.isPresent()) {
            Train train = optionalTrain.get();
            String trainNumber = train.getTrainNumber();
            trainRepository.delete(train);
            return ResponseEntity.ok("Поезд с номером " + trainNumber  + " удален.");
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    private boolean isTrainNumberUnique(String trainNumber) {
        // Проверяем, существует ли уже поезд с таким номером в базе данных
        return trainRepository.findByTrainNumber(trainNumber) == null;
    }
}
