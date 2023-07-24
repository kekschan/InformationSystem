package ru.practice.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practice.server.model.wagon.passenger.exemplar.restaurantWagon.RestaurantWagon;

import java.util.List;
import java.util.UUID;

public interface RestaurantWagonRepository extends JpaRepository<RestaurantWagon, UUID> {
    List<RestaurantWagon> findByTrainId(UUID trainId);
}