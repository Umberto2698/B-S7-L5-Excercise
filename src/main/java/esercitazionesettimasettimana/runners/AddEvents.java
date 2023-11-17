package esercitazionesettimasettimana.runners;

import com.github.javafaker.Faker;
import esercitazionesettimasettimana.enteties.User;
import esercitazionesettimasettimana.enums.Role;
import esercitazionesettimasettimana.payloads.events.EventDTO;
import esercitazionesettimasettimana.services.EventService;
import esercitazionesettimasettimana.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.function.Supplier;

//@Component
@Order(2)
public class AddEvents implements CommandLineRunner {
    @Autowired
    private UserService userService;

    @Autowired
    private EventService eventService;

    @Override
    public void run(String... args) {
        Faker faker = new Faker(Locale.ITALY);
        List<User> organizerList = userService.getUsers(0, 50, "id").stream().filter(user -> user.getRole() == Role.EVENT_ORGANIZER).toList();
        int size = organizerList.size();
        Supplier<EventDTO> eventSupplier = () -> new EventDTO(faker.book().title(),
                faker.lorem().characters(20, 40),
                faker.date().between(Date.from(
                                LocalDate.now().minusYears(10).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()),
                        Date.from(LocalDate.now().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant())
                ).toInstant().atZone(ZoneId.systemDefault()).toLocalDate(),
                faker.address().fullAddress(),
                new Random().nextInt(10, 50));
        for (int i = 0; i < 40; i++) {
            int n = new Random().nextInt(0, size);
            eventService.save(organizerList.get(n).getId(), eventSupplier.get());
        }
    }
}
