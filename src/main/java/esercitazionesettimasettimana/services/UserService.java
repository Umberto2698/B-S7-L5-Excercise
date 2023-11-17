package esercitazionesettimasettimana.services;

import esercitazionesettimasettimana.enteties.Event;
import esercitazionesettimasettimana.enteties.User;
import esercitazionesettimasettimana.exceptions.ItemNotFoundException;
import esercitazionesettimasettimana.payloads.events.BookEventSuccessDTO;
import esercitazionesettimasettimana.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EventService eventService;

    public Page<User> getUsers(int page, int size, String orderBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(orderBy));
        return userRepository.findAll(pageable);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ItemNotFoundException(email));
    }

    public User getById(UUID id) {
        return userRepository.findById(id).orElseThrow(() -> new ItemNotFoundException(id));
    }

    public void delete(UUID id) {
        User found = this.getById(id);
        userRepository.delete(found);
    }

    public BookEventSuccessDTO bookEvent(UUID eventId, UUID id) {
        User user = this.getById(id);
        Event event = eventService.getById(eventId);
        user.setEvents(Set.of(event));
        userRepository.save(user);
        return new BookEventSuccessDTO(user, event);
    }

    public void cancelReservation(UUID eventId, UUID id) {
        User user = this.getById(id);
        user.setEvents((user.getEvents().stream().filter(event -> event.getId() != eventId)).collect(Collectors.toSet()));
        userRepository.save(user);
    }
}
