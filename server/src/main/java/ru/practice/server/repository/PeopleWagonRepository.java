package ru.practice.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practice.server.model.wagon.passenger.PeopleWagon;

import java.util.List;
import java.util.UUID;

@Repository
public interface PeopleWagonRepository extends JpaRepository<PeopleWagon, UUID> {

    List<PeopleWagon> findByTrainId(UUID trainId);
}