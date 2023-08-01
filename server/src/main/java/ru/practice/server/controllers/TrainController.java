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
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/train")
@Tag(name = "Поезд")
public class TrainController {
    private final TrainService trainService;

    public TrainController(TrainService trainService) {
        this.trainService = trainService;
    }

    @PostMapping("/add")
    @Operation(summary = "Добавление поезда", description = "В json требуется указать trainType = ('freight' или 'passenger')")
    public ResponseEntity<String> addTrain(@RequestBody TrainDto trainDto) {
        try {
            trainService.addTrain(trainDto);
            return ResponseEntity.ok("Поезд успешно добавлен.");
        } catch (TrainAlreadyExistsException | IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ошибка добавления поезда.");
        }
    }

    @GetMapping
    @Operation(summary = "Получение всех поездов вместе с их вагонами")
    public ResponseEntity<List<Train>> getAllTrains() {
        List<Train> trains = trainService.getAllTrains();
        return ResponseEntity.ok(trains);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получение одного поезда по его Id вместе с его вагонами")
    public ResponseEntity<Train> getTrainById(@PathVariable UUID id) {
        try {
            Train train = trainService.getTrainById(id);
            return ResponseEntity.ok(train);
        } catch (TrainNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удаление поезда по его Id")
    public ResponseEntity<String> deleteTrain(@PathVariable UUID id) {
        try {
            trainService.deleteTrain(id);
            return ResponseEntity.ok("Поезд успешно удален.");
        } catch (TrainNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ошибка удаления поезда.");
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Обновление информации о поезде по его Id")
    public ResponseEntity<String> updateTrain(@PathVariable UUID id, @RequestBody TrainDto trainDto) {
        try {
            trainService.updateTrain(id, trainDto);
            return ResponseEntity.ok("Поезд успешно обновлен.");
        } catch (TrainNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ошибка обновления поезда.");
        }
    }
}
