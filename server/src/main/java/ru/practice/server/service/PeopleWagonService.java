package ru.practice.server.service;

import org.springframework.stereotype.Service;
import ru.practice.server.exception.MaxWagonLimitExceededException;
import ru.practice.server.exception.TrainNotFoundException;
import ru.practice.server.exception.WagonNotFoundException;
import ru.practice.server.model.trains.Train;
import ru.practice.server.model.trains.type.PassengerTrain;
import ru.practice.server.model.wagon.freight.FreightWagon;
import ru.practice.server.model.wagon.passenger.PeopleWagon;
import ru.practice.server.model.wagon.passenger.dto.PeopleWagonDto;
import ru.practice.server.model.wagon.passenger.exemplar.mailWagon.type.BaggageLetters;
import ru.practice.server.model.wagon.passenger.exemplar.mailWagon.type.EnvelopeLetters;
import ru.practice.server.model.wagon.passenger.exemplar.passengerWagon.type.Coupe;
import ru.practice.server.model.wagon.passenger.exemplar.passengerWagon.type.Lux;
import ru.practice.server.model.wagon.passenger.exemplar.passengerWagon.type.ReservedSeat;
import ru.practice.server.model.wagon.passenger.exemplar.restaurantWagon.type.Bar;
import ru.practice.server.model.wagon.passenger.exemplar.restaurantWagon.type.StandartRestaurant;
import ru.practice.server.repository.PeopleWagonRepository;
import ru.practice.server.repository.TrainRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PeopleWagonService {
    private final PeopleWagonRepository peopleWagonRepository;
    private final TrainRepository trainRepository;

    public PeopleWagonService(PeopleWagonRepository peopleWagonRepository, TrainRepository trainRepository) {
        this.peopleWagonRepository = peopleWagonRepository;
        this.trainRepository = trainRepository;
    }

    public void addPeopleWagonToTrain(UUID trainId, PeopleWagonDto peopleWagonDto) throws TrainNotFoundException, MaxWagonLimitExceededException, IllegalArgumentException {

        Optional<Train> optionalTrain = trainRepository.findById(trainId);
        if (optionalTrain.isEmpty()) {
            throw new TrainNotFoundException("Поезд с id " + trainId + " не найден.");
        }
        PassengerTrain passengerTrain = (PassengerTrain) optionalTrain.get();

        PeopleWagon peopleWagon;
        if ("baggageLetters".equalsIgnoreCase(peopleWagonDto.getWagonType())) {
            peopleWagon = new BaggageLetters();
        } else if ("envelopeLetters".equalsIgnoreCase(peopleWagonDto.getWagonType())) {
            peopleWagon = new EnvelopeLetters();
        } else if ("lux".equalsIgnoreCase(peopleWagonDto.getWagonType())) {
            peopleWagon = new Lux();
        } else if ("reservedSeat".equalsIgnoreCase(peopleWagonDto.getWagonType())) {
            peopleWagon = new ReservedSeat();
        }else if ("coupe".equalsIgnoreCase(peopleWagonDto.getWagonType())) {
            peopleWagon = new Coupe();
        }else if ("bar".equalsIgnoreCase(peopleWagonDto.getWagonType())) {
            peopleWagon = new Bar();
        }else if ("standartRestaurant".equalsIgnoreCase(peopleWagonDto.getWagonType())) {
            peopleWagon = new StandartRestaurant();
        }else {
            throw new IllegalArgumentException("Неверный тип вагона. Требуется указать 'baggageLetters', 'lux', 'reservedSeat'," +
                    " 'coupe', 'bar', 'standartRestaurant', 'envelopeLetters'.");
        }

        if(passengerTrain.getPeopleWagons().size() < trainRepository.findTrainById(trainId).getNumberOfWagons()) {
            passengerTrain.getPeopleWagons().add(peopleWagon);
            peopleWagon.setTrain(passengerTrain);
            peopleWagonRepository.save(peopleWagon);
        }else throw new MaxWagonLimitExceededException("Превышен максимальный лимит вагонов "
                + trainRepository.findTrainById(trainId).getNumberOfWagons());
    }

    public List<PeopleWagon> getAllWagonsForTrain(UUID trainId) throws TrainNotFoundException {
        Optional<Train> optionalTrain = trainRepository.findById(trainId);
        if (optionalTrain.isEmpty()) {
            throw new TrainNotFoundException("Поезд с id " + trainId + " не найден.");
        }
        return peopleWagonRepository.findByTrainId(trainId);
    }

    public void removePeopleWagon(UUID trainId, UUID wagonId) throws TrainNotFoundException, WagonNotFoundException {
        Optional<Train> optionalTrain = trainRepository.findById(trainId);
        if (optionalTrain.isEmpty()) {
            throw new TrainNotFoundException("Поезд с id " + trainId + " не найден");
        }
        Train train = optionalTrain.get();
        PeopleWagon peopleWagonToRemove = train.getPeopleWagons().stream()
                .filter(wagon -> wagon.getId().equals(wagonId))
                .findFirst()
                .orElse(null);
        if (peopleWagonToRemove == null) {
            throw new WagonNotFoundException("Пассажирский вагон с id " + wagonId + " не найден в поезде.");
        }
        train.getPeopleWagons().remove(peopleWagonToRemove);
        peopleWagonToRemove.setTrain(null);
        peopleWagonRepository.delete(peopleWagonToRemove);
    }
}
