package ru.practice.server.recource;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practice.server.exception.MaxWagonLimitExceededException;
import ru.practice.server.exception.PassengerWagonNotFoundException;
import ru.practice.server.exception.TrainNotFoundException;
import ru.practice.server.model.wagon.passenger.exemplar.passengerWagon.PassengerWagon;
import ru.practice.server.model.wagon.passenger.exemplar.passengerWagon.dto.PassengerWagonDto;
import ru.practice.server.service.PassengerWagonService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/trains")
public class PassengerWagonController {

    private final PassengerWagonService passengerWagonService;

    public PassengerWagonController(PassengerWagonService passengerWagonService) {
        this.passengerWagonService = passengerWagonService;
    }

    @PostMapping("/{trainId}/passenger-wagons")
    public ResponseEntity<String> addPassengerWagonToTrain(@PathVariable UUID trainId, @RequestBody PassengerWagonDto wagonDto) throws TrainNotFoundException, MaxWagonLimitExceededException, MaxWagonLimitExceededException {
        passengerWagonService.addPassengerWagonToTrain(trainId, wagonDto);
        return ResponseEntity.ok("Passenger wagon added successfully to train with id " + trainId + ".");
    }

    @GetMapping("/{trainId}/passenger-wagons")
    public ResponseEntity<List<PassengerWagon>> getAllPassengerWagonsForTrain(@PathVariable UUID trainId) throws TrainNotFoundException {
        List<PassengerWagon> passengerWagons = passengerWagonService.getAllPassengerWagonsForTrain(trainId);
        return ResponseEntity.ok(passengerWagons);
    }

    @DeleteMapping("/{trainId}/passenger-wagons/{wagonId}")
    public ResponseEntity<String> removePassengerWagonFromTrain(@PathVariable UUID trainId, @PathVariable UUID wagonId) throws TrainNotFoundException, PassengerWagonNotFoundException {
        passengerWagonService.removePassengerWagonFromTrain(trainId, wagonId);
        return ResponseEntity.ok("Passenger wagon removed successfully from train with id " + trainId + ".");
    }
}

