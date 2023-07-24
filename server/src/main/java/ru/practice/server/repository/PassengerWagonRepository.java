package ru.practice.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practice.server.model.wagon.passenger.exemplar.passengerWagon.PassengerWagon;

import java.util.UUID;

public interface PassengerWagonRepository extends JpaRepository<PassengerWagon, UUID> {
}