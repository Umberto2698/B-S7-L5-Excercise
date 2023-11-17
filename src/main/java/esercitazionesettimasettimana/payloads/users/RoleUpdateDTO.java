package esercitazionesettimasettimana.payloads.users;

import esercitazionesettimasettimana.enums.Role;
import jakarta.validation.constraints.NotNull;

public record RoleUpdateDTO(@NotNull(message = "The role is required.")
                            Role role) {
}
