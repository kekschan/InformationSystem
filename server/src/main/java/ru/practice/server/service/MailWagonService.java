package ru.practice.server.service;

import org.springframework.stereotype.Service;
import ru.practice.server.exception.MailWagonNotFoundException;
import ru.practice.server.exception.MaxWagonLimitExceededException;
import ru.practice.server.exception.TrainNotFoundException;
import ru.practice.server.model.trains.Train;
import ru.practice.server.model.wagon.freight.FreightWagon;
import ru.practice.server.model.wagon.freight.type.FlatcarFreightWagon;
import ru.practice.server.model.wagon.freight.type.GondolaFreightWagon;
import ru.practice.server.model.wagon.freight.type.TankFreightWagon;
import ru.practice.server.model.wagon.passenger.exemplar.mailWagon.MailWagon;
import ru.practice.server.model.wagon.passenger.exemplar.mailWagon.dto.MailWagonDto;
import ru.practice.server.model.wagon.passenger.exemplar.mailWagon.type.BaggageLetters;
import ru.practice.server.model.wagon.passenger.exemplar.mailWagon.type.EnvelopeLetters;
import ru.practice.server.repository.MailWagonRepository;
import ru.practice.server.repository.TrainRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class MailWagonService {

    private final MailWagonRepository mailWagonRepository;
    private final TrainRepository trainRepository;

    public MailWagonService(MailWagonRepository mailWagonRepository, TrainRepository trainRepository) {
        this.mailWagonRepository = mailWagonRepository;
        this.trainRepository = trainRepository;
    }

    public void addMailWagonToTrain(UUID trainId, MailWagonDto wagonDto) throws TrainNotFoundException, MaxWagonLimitExceededException {
        Optional<Train> optionalTrain = trainRepository.findById(trainId);

        if (optionalTrain.isEmpty()) {
            throw new TrainNotFoundException("Train with id " + trainId + " not found.");
        }
        Train train = optionalTrain.get();
        if (train.getPassengerWagons().size() + train.getMailWagons().size() + train.getRestaurantWagons().size() >= 20) {
            throw new MaxWagonLimitExceededException("Maximum number of wagons (20) for this train exceeded.");
        }

        //проверка на соответствие
        MailWagon mailWagon;
        if ("baggageLetters".equalsIgnoreCase(wagonDto.getWagonType())) {
            mailWagon = new BaggageLetters();
        } else if ("envelopeLetters".equalsIgnoreCase(wagonDto.getWagonType())) {
            mailWagon = new EnvelopeLetters();
        } else {
            throw new IllegalArgumentException("Invalid wagon type. It should be 'baggageLetters', 'envelopeLetters'.");
        }
        train.getMailWagons().add(mailWagon);
        mailWagon.setTrain(train);
        mailWagonRepository.save(mailWagon);
    }

    public List<MailWagon> getAllMailWagonsForTrain(UUID trainId) throws TrainNotFoundException {
        Optional<Train> optionalTrain = trainRepository.findById(trainId);
        if (optionalTrain.isEmpty()) {
            throw new TrainNotFoundException("Train with id " + trainId + " not found.");
        }
        return mailWagonRepository.findByTrainId(trainId);
    }

    public void removeMailWagonFromTrain(UUID trainId, UUID wagonId) throws TrainNotFoundException, MailWagonNotFoundException {
        Optional<Train> optionalTrain = trainRepository.findById(trainId);
        if (optionalTrain.isEmpty()) {
            throw new TrainNotFoundException("Train with id " + trainId + " not found.");
        }
        Train train = optionalTrain.get();
        MailWagon mailWagonToRemove = train.getMailWagons().stream()
                .filter(wagon -> wagon.getId().equals(wagonId))
                .findFirst()
                .orElse(null);
        if (mailWagonToRemove == null) {
            throw new MailWagonNotFoundException("Mail wagon with id " + wagonId + " not found in the train.");
        }
        train.getMailWagons().remove(mailWagonToRemove);
        mailWagonToRemove.setTrain(null);
        mailWagonRepository.delete(mailWagonToRemove);
    }
}

