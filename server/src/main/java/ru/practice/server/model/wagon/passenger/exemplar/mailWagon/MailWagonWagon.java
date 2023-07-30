package ru.practice.server.model.wagon.passenger.exemplar.mailWagon;
;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import ru.practice.server.model.wagon.passenger.PeopleWagon;
import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
public abstract class MailWagonWagon extends PeopleWagon {
    private int accommodation; //вмещаемость писем

    public MailWagonWagon(int seatingCapacity, int tables, boolean toilets, boolean hasVentilation, int accommodation) {
        super(seatingCapacity, tables, toilets, hasVentilation);
        this.accommodation = accommodation;
    }
}
