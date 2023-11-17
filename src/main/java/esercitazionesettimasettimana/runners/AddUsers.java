package esercitazionesettimasettimana.runners;

import com.github.javafaker.Faker;
import esercitazionesettimasettimana.enums.Role;
import esercitazionesettimasettimana.payloads.users.UserDTO;
import esercitazionesettimasettimana.repositories.UserRepository;
import esercitazionesettimasettimana.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;

import java.util.Locale;
import java.util.function.Supplier;

//@Component
@Order(1)
public class AddUsers implements CommandLineRunner {

    @Autowired
    private AuthService authService;

    @Autowired
    private UserRepository userRepository;

    @Override
    public void run(String... args) {
        Faker faker = new Faker(Locale.ITALY);
        Supplier<UserDTO> userSupplier = () -> new UserDTO(faker.name().firstName(), faker.name().lastName(), faker.phoneNumber().cellPhone(), faker.name().firstName() + "." + faker.name().lastName() + "@gmail.com", Role.USER);
        Supplier<UserDTO> organizerSupplier = () -> new UserDTO(faker.name().firstName(), faker.name().lastName(), faker.phoneNumber().cellPhone(), faker.name().firstName() + "." + faker.name().lastName() + "@gmail.com", Role.EVENT_ORGANIZER);
        for (int i = 0; i < 30; i++) {
            UserDTO user = userSupplier.get();
            if (userRepository.findByEmail(user.email()).isEmpty()) {
                authService.save(user);
            }
        }
        for (int i = 0; i < 20; i++) {
            UserDTO organizer = organizerSupplier.get();
            if (userRepository.findByEmail(organizer.email()).isEmpty()) {
                authService.save(organizerSupplier.get());
            }
        }
    }
}
