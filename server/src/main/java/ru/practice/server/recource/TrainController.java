package ru.practice.server.recource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practice.server.model.trains.Train;
import ru.practice.server.model.trains.dto.TrainDto;
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

    public TrainController(TrainRepository trainRepository) {
        this.trainRepository = trainRepository;
    }

    @PostMapping
    public ResponseEntity<String> addTrain(@RequestBody TrainDto trainDto) {
        try {
            String trainNumber = trainDto.getTrainNumber();

            // Проверяем, есть ли уже поезд с таким номером
            if (trainRepository.existsByTrainNumber(trainNumber)) {
                return ResponseEntity.badRequest().body("Train with trainNumber " + trainNumber + " already exists.");
            }

            Train train;
            if ("freight".equalsIgnoreCase(trainDto.getTrainType())) {
                train = new FreightTrain();
            } else if ("passenger".equalsIgnoreCase(trainDto.getTrainType())) {
                train = new PassengerTrain();
            } else {
                return ResponseEntity.badRequest().body("Invalid train type. It should be either 'freight' or 'passenger'.");
            }

            train.setTrainNumber(trainNumber);
            trainRepository.save(train);
            return ResponseEntity.ok("Train added successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to add train.");
        }
    }

    @GetMapping
    public ResponseEntity<List<Train>> getAllTrains() {
        List<Train> trains = trainRepository.findAll();
        return ResponseEntity.ok(trains);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTrain(@PathVariable UUID id) {
        try {
            trainRepository.deleteById(id);
            return ResponseEntity.ok("Train deleted successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete train.");
        }
    }
}

