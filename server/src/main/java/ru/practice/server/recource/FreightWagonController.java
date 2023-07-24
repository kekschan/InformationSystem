package ru.practice.server.recource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practice.server.model.trains.Train;
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
    public ResponseEntity<String> addFreightWagonToTrain(@PathVariable UUID trainId, @RequestBody FreightWagonDto wagonDto) {
        Optional<Train> optionalTrain = trainRepository.findById(trainId);
        if (optionalTrain.isEmpty()) {
            return ResponseEntity.badRequest().body("Train with id " + trainId + " not found.");
        }

        Train train = optionalTrain.get();
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

        /*train.getWagons().add(freightWagon);*/
        freightWagon.setTrain(train);
        freightWagonRepository.save(freightWagon);
        return ResponseEntity.ok("Wagon added successfully to train with id " + trainId + ".");
    }

    @GetMapping("/{trainId}/wagons")
    public ResponseEntity<List<FreightWagon>> getAllWagonsForTrain(@PathVariable UUID trainId) {
        List<FreightWagon> wagons = freightWagonRepository.findByTrainId(trainId);
        return ResponseEntity.ok(wagons);
    }
}


