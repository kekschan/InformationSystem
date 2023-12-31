package ru.practice.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practice.server.model.wagon.freight.FreightWagon;

import java.util.List;
import java.util.UUID;
@Repository
public interface FreightWagonRepository extends JpaRepository<FreightWagon, UUID> {
    List<FreightWagon> findByTrainId(UUID trainId);
}
