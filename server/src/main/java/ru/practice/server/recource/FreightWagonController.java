package ru.practice.server.recource;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practice.server.exception.MaxWagonLimitExceededException;
import ru.practice.server.exception.TrainNotFoundException;
import ru.practice.server.exception.WagonNotFoundException;
import ru.practice.server.model.trains.Train;
import ru.practice.server.model.trains.type.FreightTrain;
import ru.practice.server.model.wagon.freight.FreightWagon;
import ru.practice.server.model.wagon.freight.dto.FreightWagonDto;
import ru.practice.server.model.wagon.freight.type.FlatcarFreightWagon;
import ru.practice.server.model.wagon.freight.type.GondolaFreightWagon;
import ru.practice.server.model.wagon.freight.type.TankFreightWagon;
import ru.practice.server.repository.FreightWagonRepository;
import ru.practice.server.repository.TrainRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/trains")
public class FreightWagonController {

    private final FreightWagonRepository freightWagonRepository;
    private final TrainRepository trainRepository;

    public FreightWagonController(FreightWagonRepository freightWagonRepository, TrainRepository trainRepository) {
        this.freightWagonRepository = freightWagonRepository;
        this.trainRepository = trainRepository;
    }

    @PostMapping("/{trainId}/wagons")
    public ResponseEntity<String> addFreightWagonToTrain(@PathVariable UUID trainId, @RequestBody FreightWagonDto wagonDto) throws MaxWagonLimitExceededException, TrainNotFoundException {
        Optional<Train> optionalTrain = trainRepository.findById(trainId);
        if (optionalTrain.isEmpty()) {
            throw new TrainNotFoundException("Train with id " + trainId + " not found.");
        }
        Train train = optionalTrain.get();
        if (train.getFreightWagon().size() >= 15) {
            throw new MaxWagonLimitExceededException("Maximum number of wagons (15) for this train exceeded.");
        }
        // Check if the train is a FreightTrain
        if (!(train instanceof FreightTrain)) {
            return ResponseEntity.badRequest().body("Train with id " + trainId + " is not a FreightTrain.");
        }
        FreightWagon freightWagon;
        if ("flatcar".equalsIgnoreCase(wagonDto.getWagonType())) {
            freightWagon = new FlatcarFreightWagon();
        } else if ("gondola".equalsIgnoreCase(wagonDto.getWagonType())) {
            freightWagon = new GondolaFreightWagon();
        } else if ("tank".equalsIgnoreCase(wagonDto.getWagonType())) {
            freightWagon = new TankFreightWagon();
        } else {
            return ResponseEntity.badRequest().body("Invalid wagon type. It should be 'flatcar', 'gondola', or 'tank'.");
        }

        train.getFreightWagon().add(freightWagon);
        freightWagon.setTrain(train);
        freightWagonRepository.save(freightWagon);
        return ResponseEntity.ok("Wagon added successfully to train with id " + trainId + ".");
    }

    @GetMapping("/{trainId}/wagons")
    public ResponseEntity<List<FreightWagon>> getAllWagonsForTrain(@PathVariable UUID trainId) throws TrainNotFoundException {
        Optional<Train> optionalTrain = trainRepository.findById(trainId);
        if (optionalTrain.isEmpty()) {
            throw new TrainNotFoundException("Train with id " + trainId + " not found.");
        }
        List<FreightWagon> wagons = freightWagonRepository.findByTrainId(trainId);
        return ResponseEntity.ok(wagons);
    }

    @DeleteMapping("/{trainId}/wagons/{wagonId}")
    public ResponseEntity<String> removeFreightWagonFromTrain(@PathVariable UUID trainId, @PathVariable UUID wagonId) throws TrainNotFoundException, WagonNotFoundException {
        Optional<Train> optionalTrain = trainRepository.findById(trainId);
        if (optionalTrain.isEmpty()) {
            throw new TrainNotFoundException("Train with id " + trainId + " not found.");
        }
        Train train = optionalTrain.get();
        FreightWagon freightWagonToRemove = train.getFreightWagon().stream()
                .filter(wagon -> wagon.getId().equals(wagonId))
                .findFirst()
                .orElse(null);
        if (freightWagonToRemove == null) {
            throw new WagonNotFoundException("Freight wagon with id " + wagonId + " not found in the train.");
        }
        train.getFreightWagon().remove(freightWagonToRemove);
        freightWagonToRemove.setTrain(null);
        freightWagonRepository.delete(freightWagonToRemove);
        return ResponseEntity.ok("Freight wagon removed successfully from train with id " + trainId + ".");
    }
}



