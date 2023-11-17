package esercitazionesettimasettimana.payloads.events;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record BookEventDTO(
        @NotNull(message = "The event id is required.")
        UUID id) {
}
