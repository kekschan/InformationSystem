package ru.practice.server.service;

import org.springframework.stereotype.Service;
import ru.practice.server.exception.MaxWagonLimitExceededException;
import ru.practice.server.exception.TrainNotFoundException;
import ru.practice.server.exception.WagonNotFoundException;
import ru.practice.server.model.trains.Train;
import ru.practice.server.model.wagon.freight.FreightWagon;
import ru.practice.server.model.wagon.freight.dto.FreightWagonDto;
import ru.practice.server.model.wagon.freight.type.CoveredFreightWagon;
import ru.practice.server.model.wagon.freight.type.FlatcarFreightWagon;
import ru.practice.server.model.wagon.freight.type.GondolaFreightWagon;
import ru.practice.server.model.wagon.freight.type.TankFreightWagon;
import ru.practice.server.repository.FreightWagonRepository;
import ru.practice.server.repository.TrainRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class FreightWagonService {

    private final FreightWagonRepository freightWagonRepository;
    private final TrainRepository trainRepository;

    public FreightWagonService(FreightWagonRepository freightWagonRepository, TrainRepository trainRepository) {
        this.freightWagonRepository = freightWagonRepository;
        this.trainRepository = trainRepository;
    }

    public void addFreightWagonToTrain(UUID trainId, FreightWagonDto wagonDto) throws MaxWagonLimitExceededException, TrainNotFoundException {
        Optional<Train> optionalTrain = trainRepository.findById(trainId);
        if (optionalTrain.isEmpty()) {
            throw new TrainNotFoundException("Train with id " + trainId + " not found.");
        }
        Train train = optionalTrain.get();
        if (train.getFreightWagon().size() >= 15) {
            throw new MaxWagonLimitExceededException("Maximum number of wagons (15) for this train exceeded.");
        }

        //проверка на соответствие
        FreightWagon freightWagon;
        if ("flatcar".equalsIgnoreCase(wagonDto.getWagonType())) {
            freightWagon = new FlatcarFreightWagon();
        } else if ("gondola".equalsIgnoreCase(wagonDto.getWagonType())) {
            freightWagon = new GondolaFreightWagon();
        } else if ("tank".equalsIgnoreCase(wagonDto.getWagonType())) {
            freightWagon = new TankFreightWagon();
        } else if ("covered".equalsIgnoreCase(wagonDto.getWagonType())) {
            freightWagon = new CoveredFreightWagon();
        }else {
            throw new IllegalArgumentException("Invalid wagon type. It should be 'flatcar', 'gondola', 'covered' or 'tank'.");
        }

        train.getFreightWagon().add(freightWagon);
        freightWagon.setTrain(train);
        freightWagonRepository.save(freightWagon);
    }

    public List<FreightWagon> getAllWagonsForTrain(UUID trainId) throws TrainNotFoundException {
        Optional<Train> optionalTrain = trainRepository.findById(trainId);
        if (optionalTrain.isEmpty()) {
            throw new TrainNotFoundException("Train with id " + trainId + " not found.");
        }
        return freightWagonRepository.findByTrainId(trainId);
    }

    public void removeFreightWagonFromTrain(UUID trainId, UUID wagonId) throws TrainNotFoundException, WagonNotFoundException {
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
    }
}
