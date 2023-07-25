package ru.practice.server.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practice.server.exception.TrainAlreadyExistsException;
import ru.practice.server.exception.TrainNotFoundException;
import ru.practice.server.model.trains.Train;
import ru.practice.server.model.trains.dto.TrainDto;
import ru.practice.server.service.TrainService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/trains")
@Tag(name = "Поезд")
public class TrainController {

    private final TrainService trainService;

    public TrainController(TrainService trainService) {
        this.trainService = trainService;
    }

    @PostMapping
    @Operation(summary = "Добавление поезда", description = "В json требуется указать trainNumber = ('freight' или 'passenger') и trainType = (любые символы)")
    public ResponseEntity<String> addTrain(@RequestBody TrainDto trainDto) {
        try {
            trainService.addTrain(trainDto);
            return ResponseEntity.ok("Train added successfully.");
        } catch (TrainAlreadyExistsException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to add train.");
        }
    }

    @GetMapping
    @Operation(summary = "Получение всех поездов вместе с их вагонами")
    public ResponseEntity<List<Train>> getAllTrains() {
        List<Train> trains = trainService.getAllTrains();
        return ResponseEntity.ok(trains);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удаление поезда по его Id")
    public ResponseEntity<String> deleteTrain(@PathVariable UUID id) {
        try {
            trainService.deleteTrain(id);
            return ResponseEntity.ok("Train deleted successfully.");
        } catch (TrainNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete train.");
        }
    }
}


