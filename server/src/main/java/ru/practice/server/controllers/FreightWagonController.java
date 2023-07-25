package ru.practice.server.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practice.server.exception.MaxWagonLimitExceededException;
import ru.practice.server.exception.TrainNotFoundException;
import ru.practice.server.exception.WagonNotFoundException;
import ru.practice.server.model.wagon.freight.FreightWagon;
import ru.practice.server.model.wagon.freight.dto.FreightWagonDto;
import ru.practice.server.service.FreightWagonService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/trains")
@Tag(name = "Грузовые вагоны")
public class FreightWagonController {

    private final FreightWagonService freightWagonService;

    public FreightWagonController(FreightWagonService freightWagonService) {
        this.freightWagonService = freightWagonService;
    }

    @PostMapping("/{trainId}/freight-wagons")
    @Operation(summary = "Добавление вагона", description = "В json требуется указать wagonType = ('flatcar', 'gondola', 'covered' или 'tank')")
    public ResponseEntity<String> addFreightWagonToTrain(@PathVariable UUID trainId, @RequestBody FreightWagonDto wagonDto) throws MaxWagonLimitExceededException, TrainNotFoundException {
        freightWagonService.addFreightWagonToTrain(trainId, wagonDto);
        return ResponseEntity.ok("Wagon added successfully to train with id " + trainId + ".");
    }

    @GetMapping("/{trainId}/freight-wagons")
    @Operation(summary = "Получение всех грузовых вагонов по Id поезда", description = "В данном примере присутствует ошибка - цикличность," +
            " но если проводить тесты в Postman, то там все ок.")
    public ResponseEntity<List<FreightWagon>> getAllWagonsForTrain(@PathVariable UUID trainId) throws TrainNotFoundException {
        List<FreightWagon> wagons = freightWagonService.getAllWagonsForTrain(trainId);
        return ResponseEntity.ok(wagons);
    }

    @DeleteMapping("/{trainId}/freight-wagons/{wagonId}")
    @Operation(summary = "Удаление вагона по id")
    public ResponseEntity<String> removeFreightWagonFromTrain(@PathVariable UUID trainId, @PathVariable UUID wagonId) throws TrainNotFoundException, WagonNotFoundException {
        freightWagonService.removeFreightWagonFromTrain(trainId, wagonId);
        return ResponseEntity.ok("Freight wagon removed successfully from train with id " + trainId + ".");
    }
}




