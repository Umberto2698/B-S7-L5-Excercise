package esercitazionesettimasettimana.payloads.events;

import jakarta.validation.constraints.NotEmpty;

import java.time.LocalDate;

public record EventDTO(
        @NotEmpty(message = "The title is required.")
        String title,
        @NotEmpty(message = "The description is required.")
        String description,
        @NotEmpty(message = "The date is required.")
        LocalDate date,
        @NotEmpty(message = "The site is required.")
        String site,
        @NotEmpty(message = "The number of seats is required.")
        int numberOfSeats) {
}
