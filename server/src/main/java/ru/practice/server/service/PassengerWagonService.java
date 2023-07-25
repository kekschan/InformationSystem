package ru.practice.server.service;

import org.springframework.stereotype.Service;
import ru.practice.server.exception.MaxWagonLimitExceededException;
import ru.practice.server.exception.PassengerWagonNotFoundException;
import ru.practice.server.exception.TrainNotFoundException;
import ru.practice.server.model.trains.Train;
import ru.practice.server.model.wagon.passenger.exemplar.passengerWagon.PassengerWagon;
import ru.practice.server.model.wagon.passenger.exemplar.passengerWagon.dto.PassengerWagonDto;
import ru.practice.server.model.wagon.passenger.exemplar.passengerWagon.type.Lux;
import ru.practice.server.model.wagon.passenger.exemplar.passengerWagon.type.ReservedSeat;
import ru.practice.server.model.wagon.passenger.exemplar.passengerWagon.type.Сoupe;
import ru.practice.server.repository.PassengerWagonRepository;
import ru.practice.server.repository.TrainRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PassengerWagonService {

    private final PassengerWagonRepository passengerWagonRepository;
    private final TrainRepository trainRepository;

    public PassengerWagonService(PassengerWagonRepository passengerWagonRepository, TrainRepository trainRepository) {
        this.passengerWagonRepository = passengerWagonRepository;
        this.trainRepository = trainRepository;
    }

    public void addPassengerWagonToTrain(UUID trainId, PassengerWagonDto wagonDto) throws TrainNotFoundException, MaxWagonLimitExceededException {
        Optional<Train> optionalTrain = trainRepository.findById(trainId);

        if (optionalTrain.isEmpty()) {
            throw new TrainNotFoundException("Train with id " + trainId + " not found.");
        }
        Train train = optionalTrain.get();
        if (train.getPassengerWagons().size() + train.getMailWagons().size() + train.getRestaurantWagons().size() >= 20) {
            throw new MaxWagonLimitExceededException("Maximum number of wagons (20) for this train exceeded.");
        }

        // Проверка на соответствие
        PassengerWagon passengerWagon;
        if ("lux".equalsIgnoreCase(wagonDto.getWagonType())) {
            passengerWagon = new Lux();
        } else if ("reservedSeat".equalsIgnoreCase(wagonDto.getWagonType())) {
            passengerWagon = new ReservedSeat();
        } else if ("coupe".equalsIgnoreCase(wagonDto.getWagonType())) {
            passengerWagon = new Сoupe();
        } else {
            throw new IllegalArgumentException("Invalid wagon type. It should be 'lux', 'reservedSeat', or 'coupe'.");
        }

        train.getPassengerWagons().add(passengerWagon);
        passengerWagon.setTrain(train);
        passengerWagonRepository.save(passengerWagon);
    }

    public List<PassengerWagon> getAllPassengerWagonsForTrain(UUID trainId) throws TrainNotFoundException {
        Optional<Train> optionalTrain = trainRepository.findById(trainId);
        if (optionalTrain.isEmpty()) {
            throw new TrainNotFoundException("Train with id " + trainId + " not found.");
        }
        return passengerWagonRepository.findByTrainId(trainId);
    }

    public void removePassengerWagonFromTrain(UUID trainId, UUID wagonId) throws TrainNotFoundException, PassengerWagonNotFoundException {
        Optional<Train> optionalTrain = trainRepository.findById(trainId);
        if (optionalTrain.isEmpty()) {
            throw new TrainNotFoundException("Train with id " + trainId + " not found.");
        }
        Train train = optionalTrain.get();
        PassengerWagon passengerWagonToRemove = train.getPassengerWagons().stream()
                .filter(wagon -> wagon.getId().equals(wagonId))
                .findFirst()
                .orElse(null);
        if (passengerWagonToRemove == null) {
            throw new PassengerWagonNotFoundException("Passenger wagon with id " + wagonId + " not found in the train.");
        }
        train.getPassengerWagons().remove(passengerWagonToRemove);
        passengerWagonToRemove.setTrain(null);
        passengerWagonRepository.delete(passengerWagonToRemove);
    }
}

