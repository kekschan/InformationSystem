package ru.practice.server.model.wagon.passenger.exemplar.mailWagon;

import lombok.Data;
import ru.practice.server.model.wagon.passenger.WagonForPeople;
@Data
public abstract class MailWagon extends WagonForPeople {
    private int accommodation; //вмещаемость писем

    public MailWagon(int seatingCapacity, int tables, boolean toilets, boolean hasVentilation, int accommodation) {
        super(seatingCapacity, tables, toilets, hasVentilation);
        this.accommodation = accommodation;
    }
}
