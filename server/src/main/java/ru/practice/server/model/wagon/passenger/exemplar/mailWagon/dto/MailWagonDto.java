package ru.practice.server.model.wagon.passenger.exemplar.mailWagon.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * A DTO for the {@link ru.practice.server.model.wagon.passenger.exemplar.mailWagon.MailWagon} entity
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MailWagonDto{
    private int accommodation;
}