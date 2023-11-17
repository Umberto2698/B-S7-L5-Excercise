package esercitazionesettimasettimana.payloads.users;

import esercitazionesettimasettimana.enums.Role;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record UserDTO(@NotEmpty(message = "The name is required.")
                      String name,
                      @NotEmpty(message = "The surname is required.")
                      String surname,
                      @NotEmpty(message = "The password is required.")
                      String password,
                      @NotEmpty(message = "The email is required.")
                      @Pattern(regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$", message = "Insert a valid email.")
                      String email,
                      @NotNull(message = "The role is required.")
                      Role role) {
}
