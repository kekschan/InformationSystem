package ru.practice.server.service;

import org.springframework.stereotype.Service;
import ru.practice.server.exception.TrainAlreadyExistsException;
import ru.practice.server.exception.TrainNotFoundException;
import ru.practice.server.model.trains.Train;
import ru.practice.server.model.trains.dto.TrainDto;
import ru.practice.server.model.trains.type.FreightTrain;
import ru.practice.server.model.trains.type.PassengerTrain;
import ru.practice.server.repository.TrainRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TrainService {
    private final TrainRepository trainRepository;

    public TrainService(TrainRepository trainRepository) {
        this.trainRepository = trainRepository;
    }

    public void addTrain(TrainDto trainDto) throws TrainAlreadyExistsException, IllegalArgumentException {
        String trainName = trainDto.getTrainName();

        if (trainRepository.existsByTrainName(trainName)) {
            throw new TrainAlreadyExistsException("Поезд с таким названием " + trainName + " уже существует.");
        }

        String trainType = trainDto.getTrainType();
        Train train;
        if ("freight".equalsIgnoreCase(trainType)) {
            train = new FreightTrain();
        } else if ("passenger".equalsIgnoreCase(trainType)) {
            train = new PassengerTrain();
        } else {
            throw new IllegalArgumentException("Неверный тип поезда. Требуется указать 'freight' или 'passenger'.");
        }

        String startingPoint = trainDto.getStartingPoint();
        if (startingPoint == null || startingPoint.isEmpty()) {
            throw new IllegalArgumentException("Требуется указать стартовую точку поезда.");
        }
        train.setStartingPoint(startingPoint);

        String finishPoint = trainDto.getFinishPoint();
        if (finishPoint == null || finishPoint.isEmpty()) {
            throw new IllegalArgumentException("Требуется указать конечную точку поезда.");
        }
        train.setFinishPoint(finishPoint);

        Integer numberOfWagons = trainDto.getNumberOfWagons();
        if (numberOfWagons == null || numberOfWagons <= 0) {
            throw new IllegalArgumentException("Количество вагонов должно быть больше нуля.");
        }
        train.setNumberOfWagons(numberOfWagons);

        train.setTrainName(trainName);
        trainRepository.save(train);
    }


    public List<Train> getAllTrains() {
        List<Train> trains = trainRepository.findAll();
        Collections.reverse(trains);
        return trains;
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

    public void updateTrain(UUID id, TrainDto trainDto) throws TrainNotFoundException, TrainAlreadyExistsException {
        Optional<Train> optionalTrain = trainRepository.findById(id);
        if (optionalTrain.isEmpty()) {
            throw new TrainNotFoundException("Поезд с id " + id + " не найден.");
        }

        Train train = optionalTrain.get();

        String newTrainName = trainDto.getTrainName();
        if (newTrainName != null && !newTrainName.equals(train.getTrainName())) {
            if (trainRepository.existsByTrainName(newTrainName)) {
                throw new TrainAlreadyExistsException("Поезд с таким названием " + newTrainName + " уже существует.");
            }
            train.setTrainName(newTrainName);
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

        if (trainDto.getNumberOfWagons() != null) {
            int totalWagons = 0;

            if ("freight".equals(trainDto.getTrainType())) {
                totalWagons = train.getFreightWagon().size();
            } else if ("passenger".equals(trainDto.getTrainType())) {
                totalWagons = train.getPeopleWagons().size();
            }

            if (trainDto.getNumberOfWagons() < totalWagons) {
                throw new IllegalArgumentException("Количество вагонов не может быть меньше, чем количество добавленных типов вагонов.");
            }

            train.setNumberOfWagons(trainDto.getNumberOfWagons());
        }




        trainRepository.save(train);
    }


}
