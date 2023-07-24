package ru.practice.server.recource;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practice.server.exception.MailWagonNotFoundException;
import ru.practice.server.exception.MaxWagonLimitExceededException;
import ru.practice.server.exception.TrainNotFoundException;
import ru.practice.server.model.wagon.passenger.exemplar.mailWagon.MailWagon;
import ru.practice.server.model.wagon.passenger.exemplar.mailWagon.dto.MailWagonDto;
import ru.practice.server.service.MailWagonService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/trains")
public class MailWagonController {

    private final MailWagonService mailWagonService;

    public MailWagonController(MailWagonService mailWagonService) {
        this.mailWagonService = mailWagonService;
    }

    @PostMapping("/{trainId}/mail-wagons")
    public ResponseEntity<String> addMailWagonToTrain(@PathVariable UUID trainId, @RequestBody MailWagonDto wagonDto) throws TrainNotFoundException, MaxWagonLimitExceededException {
        mailWagonService.addMailWagonToTrain(trainId, wagonDto);
        return ResponseEntity.ok("Mail wagon added successfully to train with id " + trainId + ".");
    }

    @GetMapping("/{trainId}/mail-wagons")
    public ResponseEntity<List<MailWagon>> getAllMailWagonsForTrain(@PathVariable UUID trainId) throws TrainNotFoundException {
        List<MailWagon> mailWagons = mailWagonService.getAllMailWagonsForTrain(trainId);
        return ResponseEntity.ok(mailWagons);
    }

    @DeleteMapping("/{trainId}/mail-wagons/{wagonId}")
    public ResponseEntity<String> removeMailWagonFromTrain(@PathVariable UUID trainId, @PathVariable UUID wagonId) throws TrainNotFoundException, MailWagonNotFoundException {
        mailWagonService.removeMailWagonFromTrain(trainId, wagonId);
        return ResponseEntity.ok("Mail wagon removed successfully from train with id " + trainId + ".");
    }
}

