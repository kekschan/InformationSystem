package ru.practice.server.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
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
@RequestMapping("/freight")
@Tag(name = "Грузовые вагоны")
public class FreightWagonController {
    private final FreightWagonService freightWagonService;

    public FreightWagonController(FreightWagonService freightWagonService) {
        this.freightWagonService = freightWagonService;
    }

    @PostMapping("/{trainId}/add")
    @Operation(summary = "Добавление вагона", description = "В json требуется указать wagonType = ('flatcar', 'gondola', 'covered' или 'tank')")
    public ResponseEntity<String> addFreightWagonToTrain(@PathVariable UUID trainId, @RequestBody FreightWagonDto wagonDto) throws MaxWagonLimitExceededException, TrainNotFoundException {
        try {
            freightWagonService.addFreightWagonToTrain(trainId, wagonDto);
            return ResponseEntity.ok("Вагон успешно добавлен к поезду с id " + trainId + ".");
        } catch (TrainNotFoundException | MaxWagonLimitExceededException | IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ошибка добавления вагона.");
        }
    }

    @GetMapping("/{trainId}")
    @Operation(summary = "Получение всех грузовых вагонов по Id поезда")
    public ResponseEntity<List<FreightWagon>> getAllWagonsForTrain(@PathVariable UUID trainId) throws TrainNotFoundException {
        List<FreightWagon> wagons = freightWagonService.getAllWagons(trainId);
        return ResponseEntity.ok(wagons);
    }

    @DeleteMapping("/{trainId}/{wagonId}")
    @Operation(summary = "Удаление вагона по id")
    public ResponseEntity<String> removeFreightWagon(@PathVariable UUID trainId, @PathVariable UUID wagonId) throws TrainNotFoundException, WagonNotFoundException {
        try {
            freightWagonService.removeFreightWagon(trainId, wagonId);
            return ResponseEntity.ok("Грузовой вагон был успешно удален из поезда с id " + trainId + ".");
        } catch (TrainNotFoundException | WagonNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ошибка удаления вагона.");
        }
    }
}
