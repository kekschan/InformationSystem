package ru.practice.server.recource;

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
public class RestaurantWagonController {

    private final RestaurantWagonService restaurantWagonService;

    public RestaurantWagonController(RestaurantWagonService restaurantWagonService) {
        this.restaurantWagonService = restaurantWagonService;
    }

    @PostMapping("/{trainId}/restaurant-wagons")
    public ResponseEntity<String> addRestaurantWagonToTrain(@PathVariable UUID trainId, @RequestBody RestaurantWagonDto wagonDto) throws TrainNotFoundException, MaxWagonLimitExceededException {
        restaurantWagonService.addRestaurantWagonToTrain(trainId, wagonDto);
        return ResponseEntity.ok("Restaurant wagon added successfully to train with id " + trainId + ".");
    }

    @GetMapping("/{trainId}/restaurant-wagons")
    public ResponseEntity<List<RestaurantWagon>> getAllRestaurantWagonsForTrain(@PathVariable UUID trainId) throws TrainNotFoundException {
        List<RestaurantWagon> restaurantWagons = restaurantWagonService.getAllRestaurantWagonsForTrain(trainId);
        return ResponseEntity.ok(restaurantWagons);
    }

    @DeleteMapping("/{trainId}/restaurant-wagons/{wagonId}")
    public ResponseEntity<String> removeRestaurantWagonFromTrain(@PathVariable UUID trainId, @PathVariable UUID wagonId) throws TrainNotFoundException, RestaurantWagonNotFoundException {
        restaurantWagonService.removeRestaurantWagonFromTrain(trainId, wagonId);
        return ResponseEntity.ok("Restaurant wagon removed successfully from train with id " + trainId + ".");
    }
}

