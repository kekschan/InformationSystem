package ru.practice.server.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Пассажирские вагоны")
public class PassengerWagonController {

    private final PassengerWagonService passengerWagonService;

    public PassengerWagonController(PassengerWagonService passengerWagonService) {
        this.passengerWagonService = passengerWagonService;
    }

    @PostMapping("/{trainId}/passenger-wagons")
    @Operation(summary = "Добавление вагона", description = "В json требуется указать wagonType = ('lux', 'reservedSeat', или 'coupe')")
    public ResponseEntity<String> addPassengerWagonToTrain(@PathVariable UUID trainId, @RequestBody PassengerWagonDto wagonDto) throws TrainNotFoundException, MaxWagonLimitExceededException, MaxWagonLimitExceededException {
        passengerWagonService.addPassengerWagonToTrain(trainId, wagonDto);
        return ResponseEntity.ok("Passenger wagon added successfully to train with id " + trainId + ".");
    }

    @GetMapping("/{trainId}/passenger-wagons")
    @Operation(summary = "Получение всех пассажирских вагонов по Id поезда", description = "В данном примере присутствует ошибка - цикличность," +
            " но если проводить тесты в Postman, то там все ок.")
    public ResponseEntity<List<PassengerWagon>> getAllPassengerWagonsForTrain(@PathVariable UUID trainId) throws TrainNotFoundException {
        List<PassengerWagon> passengerWagons = passengerWagonService.getAllPassengerWagonsForTrain(trainId);
        return ResponseEntity.ok(passengerWagons);
    }

    @DeleteMapping("/{trainId}/passenger-wagons/{wagonId}")
    @Operation(summary = "Удаление вагона по id")
    public ResponseEntity<String> removePassengerWagonFromTrain(@PathVariable UUID trainId, @PathVariable UUID wagonId) throws TrainNotFoundException, PassengerWagonNotFoundException {
        passengerWagonService.removePassengerWagonFromTrain(trainId, wagonId);
        return ResponseEntity.ok("Passenger wagon removed successfully from train with id " + trainId + ".");
    }
}

