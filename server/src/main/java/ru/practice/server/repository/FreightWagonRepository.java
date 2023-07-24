package ru.practice.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practice.server.model.wagon.freight.FreightWagon;

import java.util.List;
import java.util.UUID;

public interface FreightWagonRepository extends JpaRepository<FreightWagon, UUID> {
    List<FreightWagon> findByTrainId(UUID trainId);
}
