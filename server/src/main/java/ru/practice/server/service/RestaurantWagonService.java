package ru.practice.server.service;

import org.springframework.stereotype.Service;
import ru.practice.server.exception.MaxWagonLimitExceededException;
import ru.practice.server.exception.RestaurantWagonNotFoundException;
import ru.practice.server.exception.TrainNotFoundException;
import ru.practice.server.model.trains.Train;
import ru.practice.server.model.wagon.passenger.exemplar.restaurantWagon.RestaurantWagon;
import ru.practice.server.model.wagon.passenger.exemplar.restaurantWagon.dto.RestaurantWagonDto;
import ru.practice.server.model.wagon.passenger.exemplar.restaurantWagon.type.Bar;
import ru.practice.server.model.wagon.passenger.exemplar.restaurantWagon.type.StandartRestaurant;
import ru.practice.server.repository.RestaurantWagonRepository;
import ru.practice.server.repository.TrainRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class RestaurantWagonService {

    private final RestaurantWagonRepository restaurantWagonRepository;
    private final TrainRepository trainRepository;

    public RestaurantWagonService(RestaurantWagonRepository restaurantWagonRepository, TrainRepository trainRepository) {
        this.restaurantWagonRepository = restaurantWagonRepository;
        this.trainRepository = trainRepository;
    }

    public void addRestaurantWagonToTrain(UUID trainId, RestaurantWagonDto wagonDto) throws TrainNotFoundException, MaxWagonLimitExceededException {
        Optional<Train> optionalTrain = trainRepository.findById(trainId);

        if (optionalTrain.isEmpty()) {
            throw new TrainNotFoundException("Train with id " + trainId + " not found.");
        }
        Train train = optionalTrain.get();
        if (train.getPassengerWagons().size() + train.getMailWagons().size() + train.getRestaurantWagons().size() >= 20) {
            throw new MaxWagonLimitExceededException("Maximum number of wagons (20) for this train exceeded.");
        }

        // Проверка на соответствие
        RestaurantWagon restaurantWagon;
        if ("bar".equalsIgnoreCase(wagonDto.getWagonType())) {
            restaurantWagon = new Bar();
        } else if ("standartRestaurant".equalsIgnoreCase(wagonDto.getWagonType())) {
            restaurantWagon = new StandartRestaurant();
        } else {
            throw new IllegalArgumentException("Invalid wagon type. It should be 'bar', 'standartRestaurant'.");
        }

        train.getRestaurantWagons().add(restaurantWagon);
        restaurantWagon.setTrain(train);
        restaurantWagonRepository.save(restaurantWagon);
    }

    public List<RestaurantWagon> getAllRestaurantWagonsForTrain(UUID trainId) throws TrainNotFoundException {
        Optional<Train> optionalTrain = trainRepository.findById(trainId);
        if (optionalTrain.isEmpty()) {
            throw new TrainNotFoundException("Train with id " + trainId + " not found.");
        }
        return restaurantWagonRepository.findByTrainId(trainId);
    }

    public void removeRestaurantWagonFromTrain(UUID trainId, UUID wagonId) throws TrainNotFoundException, RestaurantWagonNotFoundException {
        Optional<Train> optionalTrain = trainRepository.findById(trainId);
        if (optionalTrain.isEmpty()) {
            throw new TrainNotFoundException("Train with id " + trainId + " not found.");
        }
        Train train = optionalTrain.get();
        RestaurantWagon restaurantWagonToRemove = train.getRestaurantWagons().stream()
                .filter(wagon -> wagon.getId().equals(wagonId))
                .findFirst()
                .orElse(null);
        if (restaurantWagonToRemove == null) {
            throw new RestaurantWagonNotFoundException("Restaurant wagon with id " + wagonId + " not found in the train.");
        }
        train.getRestaurantWagons().remove(restaurantWagonToRemove);
        restaurantWagonToRemove.setTrain(null);
        restaurantWagonRepository.delete(restaurantWagonToRemove);
    }
}

