package ru.practice.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practice.server.model.wagon.passenger.exemplar.mailWagon.MailWagon;

import java.util.List;
import java.util.UUID;

public interface MailWagonRepository extends JpaRepository<MailWagon, UUID> {
    List<MailWagon> findByTrainId(UUID trainId);
}