package ru.practice.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practice.server.model.trains.Train;

import java.util.UUID;

@Repository
public interface TrainRepository extends JpaRepository<Train, UUID> {
    boolean existsByTrainNumber(String trainNumber);
}