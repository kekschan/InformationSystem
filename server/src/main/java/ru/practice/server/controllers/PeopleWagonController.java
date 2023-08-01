package ru.practice.server.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practice.server.exception.MaxWagonLimitExceededException;
import ru.practice.server.exception.TrainNotFoundException;
import ru.practice.server.exception.WagonNotFoundException;
import ru.practice.server.model.wagon.passenger.PeopleWagon;
import ru.practice.server.model.wagon.passenger.dto.PeopleWagonDto;
import ru.practice.server.service.PeopleWagonService;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/passenger")
@Tag(name = "Пассажирские вагоны")
public class PeopleWagonController {

    private final PeopleWagonService peopleWagonService;

    public PeopleWagonController(PeopleWagonService peopleWagonService) {
        this.peopleWagonService = peopleWagonService;
    }

    @PostMapping("/{trainId}/add")
    @Operation(summary = "Добавление вагона", description = "В json требуется указать wagonType = ('baggageLetters', 'lux', 'reservedSeat', 'coupe', 'bar', 'standartRestaurant', 'envelopeLetters'.)")
    public ResponseEntity<String> addFreightWagonToTrain(@PathVariable UUID trainId, @RequestBody PeopleWagonDto wagonDto) throws MaxWagonLimitExceededException, TrainNotFoundException {
        try {
            peopleWagonService.addPeopleWagonToTrain(trainId, wagonDto);
            return ResponseEntity.ok("Вагон успешно добавлен к поезду с id " + trainId + ".");
        } catch (TrainNotFoundException | MaxWagonLimitExceededException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ошибка добавления вагона.");
        }
    }

    @GetMapping("/{trainId}")
    @Operation(summary = "Получение всех пассажирских вагонов по Id поезда")
    public ResponseEntity<List<PeopleWagon>> getAllWagonsForTrain(@PathVariable UUID trainId) throws TrainNotFoundException {
        try {
            List<PeopleWagon> wagons = peopleWagonService.getAllWagonsForTrain(trainId);
            return ResponseEntity.ok(wagons);
        } catch (TrainNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{trainId}/{wagonId}")
    @Operation(summary = "Удаление вагона по id")
    public ResponseEntity<String> removeFreightWagon(@PathVariable UUID trainId, @PathVariable UUID wagonId) throws TrainNotFoundException, WagonNotFoundException {
        try {
            peopleWagonService.removePeopleWagon(trainId, wagonId);
            return ResponseEntity.ok("Пассажирский вагон был успешно удален из поезда с id " + trainId + ".");
        } catch (TrainNotFoundException | WagonNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ошибка удаления вагона.");
        }
    }
}
