package ru.practice.server.service;

import org.springframework.stereotype.Service;
import ru.practice.server.exception.MaxWagonLimitExceededException;
import ru.practice.server.exception.TrainNotFoundException;
import ru.practice.server.exception.WagonNotFoundException;
import ru.practice.server.model.trains.Train;
import ru.practice.server.model.trains.type.FreightTrain;
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

    public void addFreightWagonToTrain(UUID trainId, FreightWagonDto freightWagonDto) throws TrainNotFoundException, MaxWagonLimitExceededException, IllegalArgumentException {

        Optional<Train> optionalTrain = trainRepository.findById(trainId);
        if (optionalTrain.isEmpty()) {
            throw new TrainNotFoundException("Поезд с id " + trainId + " не найден.");
        }
        FreightTrain freightTrain = (FreightTrain) optionalTrain.get();

        FreightWagon freightWagon;
        if ("flatcar".equalsIgnoreCase(freightWagonDto.getWagonType())) {
            freightWagon = new FlatcarFreightWagon();
        } else if ("gondola".equalsIgnoreCase(freightWagonDto.getWagonType())) {
            freightWagon = new GondolaFreightWagon();
        } else if ("tank".equalsIgnoreCase(freightWagonDto.getWagonType())) {
            freightWagon = new TankFreightWagon();
        } else if ("covered".equalsIgnoreCase(freightWagonDto.getWagonType())) {
            freightWagon = new CoveredFreightWagon();
        }else {
            throw new IllegalArgumentException("Неверный тип вагона. Требуется указать 'flatcar' или 'gondola', 'covered' или 'tank'.");
        }

        if(freightTrain.getFreightWagon().size() < trainRepository.findTrainById(trainId).getNumberOfWagons()) {
            freightTrain.getFreightWagon().add(freightWagon);
            freightWagon.setTrain(freightTrain);
            freightWagonRepository.save(freightWagon);
        }else throw new MaxWagonLimitExceededException("Превышен максимальный лимит вагонов "
                + trainRepository.findTrainById(trainId).getNumberOfWagons());
    }

    public List<FreightWagon> getAllWagons(UUID trainId) throws TrainNotFoundException {
        Optional<Train> optionalTrain = trainRepository.findById(trainId);
        if (optionalTrain.isEmpty()) {
            throw new TrainNotFoundException("Поезд с id " + trainId + " не найден.");
        }
        return freightWagonRepository.findByTrainId(trainId);
    }


    public void removeFreightWagon(UUID trainId, UUID wagonId) throws TrainNotFoundException, WagonNotFoundException {
        Optional<Train> optionalTrain = trainRepository.findById(trainId);
        if (optionalTrain.isEmpty()) {
            throw new TrainNotFoundException("Поезд с id " + trainId + " не найден");
        }
        Train train = optionalTrain.get();
        FreightWagon freightWagonToRemove = train.getFreightWagon().stream()
                .filter(wagon -> wagon.getId().equals(wagonId))
                .findFirst()
                .orElse(null);
        if (freightWagonToRemove == null) {
            throw new WagonNotFoundException("Грузовой вагон с id " + wagonId + " не найден в поезде.");
        }
        train.getFreightWagon().remove(freightWagonToRemove);
        freightWagonToRemove.setTrain(null);
        freightWagonRepository.delete(freightWagonToRemove);
    }

}
