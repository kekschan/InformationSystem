package ru.practice.server.service;

import org.springframework.stereotype.Service;
import ru.practice.server.exception.TrainAlreadyExistsException;
import ru.practice.server.exception.TrainNotFoundException;
import ru.practice.server.model.trains.Train;
import ru.practice.server.model.trains.dto.TrainDto;
import ru.practice.server.model.trains.type.FreightTrain;
import ru.practice.server.model.trains.type.PassengerTrain;
import ru.practice.server.repository.TrainRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TrainService {
    private final TrainRepository trainRepository;

    public TrainService(TrainRepository trainRepository) {
        this.trainRepository = trainRepository;
    }

    public void addTrain(TrainDto trainDto) throws TrainAlreadyExistsException {
        String trainName = trainDto.getTrainName();

        if (trainRepository.existsByTrainName(trainName)) {
            throw new TrainAlreadyExistsException("Поезд с таким названием " + trainName + " уже существует.");
        }

        Train train;
        if ("freight".equalsIgnoreCase(trainDto.getTrainType())) {
            train = new FreightTrain();
        } else if ("passenger".equalsIgnoreCase(trainDto.getTrainType())) {
            train = new PassengerTrain();
        } else {
            throw new IllegalArgumentException("Неверный тип поезда. Требуется указать 'freight' или 'passenger'.");
        }

        train.setStartingPoint(trainDto.getStartingPoint());
        train.setFinishPoint(trainDto.getFinishPoint());
        train.setNumberOfWagons(trainDto.getNumberOfWagons());
        train.setTrainName(trainName);
        trainRepository.save(train);
    }

    public List<Train> getAllTrains() {
        return trainRepository.findAll();
    }

    public void deleteTrain(UUID id) throws TrainNotFoundException {
        if (!trainRepository.existsById(id)) {
            throw new TrainNotFoundException("Поезд с таким id " + id + " не найден.");
        }
        trainRepository.deleteById(id);
    }

    public Train getTrainById(UUID id) throws TrainNotFoundException {
        Optional<Train> optionalTrain = trainRepository.findById(id);
        if (optionalTrain.isEmpty()) {
            throw new TrainNotFoundException("Поезд с id " + id + " не найден.");
        }
        return optionalTrain.get();
    }

    public void updateTrain(UUID id, TrainDto trainDto) throws TrainNotFoundException {
        Optional<Train> optionalTrain = trainRepository.findById(id);
        if (optionalTrain.isEmpty()) {
            throw new TrainNotFoundException("Поезд с id " + id + " не найден.");
        }

        Train train = optionalTrain.get();

        //временная проверка на нули, далее будем реализовывать через аннотацию @Validation в модели
        // Проверяем, что значения в TrainDto не являются нулевыми, и только тогда обновляем поля
        if (trainDto.getTrainName() != null) {
            train.setTrainName(trainDto.getTrainName());
        }

        if (trainDto.getStartingPoint() != null) {
            train.setStartingPoint(trainDto.getStartingPoint());
        }

        if (trainDto.getFinishPoint() != null) {
            train.setFinishPoint(trainDto.getFinishPoint());
        }

        if (trainDto.getNumberOfWagons() != null) {
            train.setNumberOfWagons(trainDto.getNumberOfWagons());
        }

        trainRepository.save(train);
    }
}
