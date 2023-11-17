package esercitazionesettimasettimana.payloads.events;

import esercitazionesettimasettimana.enteties.Event;
import esercitazionesettimasettimana.enteties.User;

public record BookEventSuccessDTO(User user, Event event) {
}
