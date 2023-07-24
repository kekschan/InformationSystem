package ru.practice.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practice.server.model.wagon.passenger.exemplar.restaurantWagon.RestaurantWagon;

import java.util.UUID;

public interface RestaurantWagonRepository extends JpaRepository<RestaurantWagon, UUID> {
}