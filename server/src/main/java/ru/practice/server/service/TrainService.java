package ru.practice.server.service;

import org.springframework.stereotype.Service;;
import ru.practice.server.exception.TrainAlreadyExistsException;
import ru.practice.server.exception.TrainNotFoundException;
import ru.practice.server.model.trains.Train;
import ru.practice.server.model.trains.dto.TrainDto;
import ru.practice.server.model.trains.type.FreightTrain;
import ru.practice.server.model.trains.type.PassengerTrain;
import ru.practice.server.repository.TrainRepository;
import java.util.List;
import java.util.UUID;

@Service
public class TrainService {

    private final TrainRepository trainRepository;

    public TrainService(TrainRepository trainRepository) {
        this.trainRepository = trainRepository;
    }

    public void addTrain(TrainDto trainDto) throws TrainAlreadyExistsException, IllegalArgumentException {
        String trainNumber = trainDto.getTrainNumber();

        // Check if the train with the same number already exists
        if (trainRepository.existsByTrainNumber(trainNumber)) {
            throw new TrainAlreadyExistsException("Train with trainNumber " + trainNumber + " already exists.");
        }

        Train train;
        if ("freight".equalsIgnoreCase(trainDto.getTrainType())) {
            train = new FreightTrain();
        } else if ("passenger".equalsIgnoreCase(trainDto.getTrainType())) {
            train = new PassengerTrain();
        } else {
            throw new IllegalArgumentException("Invalid train type. It should be either 'freight' or 'passenger'.");
        }

        train.setTrainNumber(trainNumber);
        trainRepository.save(train);
    }

    public List<Train> getAllTrains() {
        return trainRepository.findAll();
    }

    public void deleteTrain(UUID id) throws TrainNotFoundException {
        if (!trainRepository.existsById(id)) {
            throw new TrainNotFoundException("Train with id " + id + " not found.");
        }
        trainRepository.deleteById(id);
    }
}
