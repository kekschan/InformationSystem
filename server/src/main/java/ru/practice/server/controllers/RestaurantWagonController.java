package ru.practice.server.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practice.server.exception.MaxWagonLimitExceededException;
import ru.practice.server.exception.RestaurantWagonNotFoundException;
import ru.practice.server.exception.TrainNotFoundException;
import ru.practice.server.model.wagon.passenger.exemplar.restaurantWagon.RestaurantWagon;
import ru.practice.server.model.wagon.passenger.exemplar.restaurantWagon.dto.RestaurantWagonDto;
import ru.practice.server.service.RestaurantWagonService;

import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/trains")
@Tag(name = "Вагоны-рестораны")
public class RestaurantWagonController {

    private final RestaurantWagonService restaurantWagonService;

    public RestaurantWagonController(RestaurantWagonService restaurantWagonService) {
        this.restaurantWagonService = restaurantWagonService;
    }

    @PostMapping("/{trainId}/restaurant-wagons")
    @Operation(summary = "Добавление вагона", description = "В json требуется указать wagonType = ('bar' или 'standartRestaurant')")
    public ResponseEntity<String> addRestaurantWagonToTrain(@PathVariable UUID trainId, @RequestBody RestaurantWagonDto wagonDto) throws TrainNotFoundException, MaxWagonLimitExceededException {
        restaurantWagonService.addRestaurantWagonToTrain(trainId, wagonDto);
        return ResponseEntity.ok("Restaurant wagon added successfully to train with id " + trainId + ".");
    }

    @GetMapping("/{trainId}/restaurant-wagons")
    @Operation(summary = "Получение всех вагонов-ресторанов по Id поезда", description = "В данном примере присутствует ошибка - цикличность," +
            " но если проводить тесты в Postman, то там все ок.")
    public ResponseEntity<List<RestaurantWagon>> getAllRestaurantWagonsForTrain(@PathVariable UUID trainId) throws TrainNotFoundException {
        List<RestaurantWagon> restaurantWagons = restaurantWagonService.getAllRestaurantWagonsForTrain(trainId);
        return ResponseEntity.ok(restaurantWagons);
    }

    @DeleteMapping("/{trainId}/restaurant-wagons/{wagonId}")
    @Operation(summary = "Удаление вагона по id")
    public ResponseEntity<String> removeRestaurantWagonFromTrain(@PathVariable UUID trainId, @PathVariable UUID wagonId) throws TrainNotFoundException, RestaurantWagonNotFoundException {
        restaurantWagonService.removeRestaurantWagonFromTrain(trainId, wagonId);
        return ResponseEntity.ok("Restaurant wagon removed successfully from train with id " + trainId + ".");
    }
}

