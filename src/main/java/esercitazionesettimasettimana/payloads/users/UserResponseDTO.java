package esercitazionesettimasettimana.payloads.users;

import esercitazionesettimasettimana.enums.Role;

import java.util.UUID;

public record UserResponseDTO(
        UUID id,
        String name,
        String surname,
        String username,
        String email,
        Role role
) {
}
