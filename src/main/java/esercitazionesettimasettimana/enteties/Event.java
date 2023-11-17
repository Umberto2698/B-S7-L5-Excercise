package esercitazionesettimasettimana.enteties;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.javafaker.Faker;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

@Getter
@Setter
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
    @Column(name = "image_url")
    private String image;
    @JsonIgnore
    @Column(name = "public_id")
    private String publicId;
    @Column(name = "n_of_seats")
    private int numberOfSeats;

    @ManyToMany(cascade = CascadeType.REMOVE)
    @JoinTable(name = "Event_Participants", joinColumns = {@JoinColumn(name = "user_id")}, inverseJoinColumns = {@JoinColumn(name = "event_id")})
    private Set<User> partecipants = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "event_organizer_id", nullable = false)
    private User eventOrganizer;

    public static class EventBuilder {
        Faker faker = new Faker(Locale.ITALY);
        private String title = faker.book().title();
        private UUID id = UUID.randomUUID();
        private String description = faker.lorem().characters(20, 40);
        private String site = faker.address().fullAddress();
        private LocalDate date = faker.date().between(Date.from(
                        LocalDate.now().minusYears(10).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()),
                Date.from(LocalDate.now().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant())
        ).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        private int numberOfSeats = new Random().nextInt(10, 50);
    }
}
