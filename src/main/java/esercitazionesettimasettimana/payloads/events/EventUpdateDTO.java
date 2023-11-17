package esercitazionesettimasettimana.payloads.events;

import java.time.LocalDate;

public record EventUpdateDTO(
        String title,
        String description,
        LocalDate date,
        String site,
        int numberOfSeats) {
}
