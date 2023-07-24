package ru.practice.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practice.server.model.wagon.passenger.exemplar.passengerWagon.PassengerWagon;

import java.util.List;
import java.util.UUID;

public interface PassengerWagonRepository extends JpaRepository<PassengerWagon, UUID> {
    List<PassengerWagon> findByTrainId(UUID trainId);
}