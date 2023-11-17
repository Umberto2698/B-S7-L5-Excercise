package esercitazionesettimasettimana.enteties;

import com.github.javafaker.Faker;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Locale;
import java.util.Random;
import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder(builderClassName = "EventBuilder")
@Entity
public class Event {
    @Id
    private UUID id;
    private String title;
    private String description;
    private LocalDate date;
    private String site;
    @Column(name = "n_of_seats")
    private int numberOfSeats;

    public static class EventBuilder {
        Faker faker = new Faker(Locale.ITALY);
        private String title = faker.book().title();
        private String description = faker.lorem().characters(20, 40);
        private String site = faker.address().fullAddress();
        private LocalDate date = faker.date().between(Date.from(
                        LocalDate.now().minusYears(10).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()),
                Date.from(LocalDate.now().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant())
        ).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        private int numberOfSeats = new Random().nextInt(10, 50);
    }
}
