package ru.practice.server.controllers;

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
public class FreightWagonController {

    private final FreightWagonService freightWagonService;

    public FreightWagonController(FreightWagonService freightWagonService) {
        this.freightWagonService = freightWagonService;
    }

    @PostMapping("/{trainId}/freight-wagons")
    public ResponseEntity<String> addFreightWagonToTrain(@PathVariable UUID trainId, @RequestBody FreightWagonDto wagonDto) throws MaxWagonLimitExceededException, TrainNotFoundException {
        freightWagonService.addFreightWagonToTrain(trainId, wagonDto);
        return ResponseEntity.ok("Wagon added successfully to train with id " + trainId + ".");
    }

    @GetMapping("/{trainId}/freight-wagons")
    public ResponseEntity<List<FreightWagon>> getAllWagonsForTrain(@PathVariable UUID trainId) throws TrainNotFoundException {
        List<FreightWagon> wagons = freightWagonService.getAllWagonsForTrain(trainId);
        return ResponseEntity.ok(wagons);
    }

    @DeleteMapping("/{trainId}/freight-wagons/{wagonId}")
    public ResponseEntity<String> removeFreightWagonFromTrain(@PathVariable UUID trainId, @PathVariable UUID wagonId) throws TrainNotFoundException, WagonNotFoundException {
        freightWagonService.removeFreightWagonFromTrain(trainId, wagonId);
        return ResponseEntity.ok("Freight wagon removed successfully from train with id " + trainId + ".");
    }
}




