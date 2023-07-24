package ru.practice.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practice.server.model.wagon.freight.FreightWagon;
import java.util.UUID;

@Repository
public interface WagonForPeopleRepository extends JpaRepository<FreightWagon, UUID> {
}
