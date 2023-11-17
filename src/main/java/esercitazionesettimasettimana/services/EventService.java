package esercitazionesettimasettimana.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import esercitazionesettimasettimana.enteties.Event;
import esercitazionesettimasettimana.enteties.User;
import esercitazionesettimasettimana.enums.Role;
import esercitazionesettimasettimana.exceptions.ItemNotFoundException;
import esercitazionesettimasettimana.exceptions.UnauthorizedException;
import esercitazionesettimasettimana.payloads.events.EventDTO;
import esercitazionesettimasettimana.repositories.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.UUID;

@Service
public class EventService {
    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private Cloudinary cloudinary;

    @Autowired
    private UserService userService;

    public Page<Event> getEvents(int page, int size, String orderBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(orderBy));
        return eventRepository.findByDateAfter(LocalDate.now(), pageable);
    }

    public Page<Event> getAllEvents(int page, int size, String orderBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(orderBy));
        return eventRepository.findAll(pageable);
    }

    public Event save(UUID organizerId, EventDTO body) {
        Event event = Event.builder().title(body.title()).description(body.description()).date(body.date()).numberOfSeats(body.numberOfSeats()).site(body.site()).build();
        event.setEventOrganizer(userService.getById(organizerId));
        return eventRepository.save(event);
    }

    public Event getById(UUID id) {
        return eventRepository.findById(id).orElseThrow(() -> new ItemNotFoundException(id));
    }

    public void delete(UUID id, UUID organizerId) throws IOException {
        User user = userService.getById(organizerId);
        Event found = this.getById(id);
        if (found.getEventOrganizer().getId().compareTo(organizerId) == 0 || user.getRole() == Role.ADMIN) {
            cloudinary.uploader().destroy(found.getPublicId(), ObjectUtils.emptyMap());
            eventRepository.delete(found);
        } else {
            throw new UnauthorizedException("You can only delete your events.");
        }
    }

    public Event uploadImage(MultipartFile file, UUID organizerId, UUID id) throws Exception {
        User user = userService.getById(organizerId);
        Event found = this.getById(id);
        if (found.getEventOrganizer().getId().compareTo(organizerId) == 0 || user.getRole() == Role.ADMIN) {
            String public_id = (String) cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap()).get("public_id");
            String url = (String) cloudinary.api().resource(public_id, ObjectUtils.emptyMap()).get("url");
            found.setImage(url);
            found.setPublicId(public_id);
            return eventRepository.save(found);
        } else {
            throw new UnauthorizedException("You can only change the image of your events.");
        }
    }
}
