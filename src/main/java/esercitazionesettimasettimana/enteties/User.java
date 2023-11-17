package esercitazionesettimasettimana.enteties;

import com.github.javafaker.Faker;
import esercitazionesettimasettimana.enums.Role;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Locale;
import java.util.UUID;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder(builderClassName = "UserBuilder")
@Entity
public class User {
    @Id
    private UUID id;
    private String name;
    private String surname;
    private String email;
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role;

    public static class UserBuilder {
        Faker faker = new Faker(Locale.ITALY);
        private String name = faker.name().firstName();
        private String surname = faker.name().lastName();
        private String email = name + "." + surname + "@gmail.com";
        private String password = faker.phoneNumber().cellPhone();
        private Role role = Role.USER;
    }
}
