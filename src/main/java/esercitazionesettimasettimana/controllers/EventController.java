package esercitazionesettimasettimana.controllers;

import esercitazionesettimasettimana.enteties.Event;
import esercitazionesettimasettimana.enteties.User;
import esercitazionesettimasettimana.exceptions.BadRequestException;
import esercitazionesettimasettimana.payloads.events.BookEventDTO;
import esercitazionesettimasettimana.payloads.events.EventDTO;
import esercitazionesettimasettimana.services.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/events")
public class EventController {
    @Autowired
    private EventService eventService;

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('ADMIN','EVENT_ORGANIZER')")
    public Page<Event> getAllEvents(@RequestParam(defaultValue = "0") int page,
                                    @RequestParam(defaultValue = "10") int size,
                                    @RequestParam(defaultValue = "id") String orderBy) {
        return eventService.getAllEvents(page, size, orderBy);
    }

    @GetMapping("/me")
    @PreAuthorize("hasAuthority('ADMIN','EVENT_ORGANIZER')")
    public Page<Event> getOrganizedEvents(@AuthenticationPrincipal User currentUser,
                                          @RequestParam(defaultValue = "0") int page,
                                          @RequestParam(defaultValue = "10") int size,
                                          @RequestParam(defaultValue = "id") String orderBy) {
        return eventService.getOrganizedEvents(currentUser, page, size, orderBy);
    }

    @GetMapping("")
    public Page<Event> getEvents(@RequestParam(defaultValue = "0") int page,
                                 @RequestParam(defaultValue = "10") int size,
                                 @RequestParam(defaultValue = "id") String orderBy) {
        return eventService.getEvents(page, size, orderBy);
    }


    @GetMapping("/{id}")
    public Event getById(@PathVariable UUID id) {
        return eventService.getById(id);
    }

    @PostMapping("/{organizerId}")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('ADMIN','EVENT_ORGANIZER')")
    public Event save(@PathVariable UUID organizerId, @RequestBody EventDTO body, BindingResult validation) {
        if (validation.hasErrors()) {
            throw new BadRequestException(validation.getAllErrors());
        } else {
            return eventService.save(organizerId, body);
        }
    }

    @DeleteMapping("/{id}/{organizerId}")
    @PreAuthorize("hasAuthority('ADMIN','EVENT_ORGANIZER')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteEvent(@PathVariable UUID id, @PathVariable UUID organizerId) throws IOException {
        eventService.delete(id, organizerId);
    }

    @PatchMapping("/me/bookEvent")
    public void bookEvent(@AuthenticationPrincipal User currentUser, @RequestBody BookEventDTO body) {
        eventService.bookEvent(currentUser.getId(), body.id());
    }

    @PatchMapping("/{organizerId}/upload/{id}")
    @PreAuthorize("hasAuthority('ADMIN','EVENT_ORGANIZER')")
    public Event updateUserPicture(@RequestParam("picure_event") MultipartFile body, @PathVariable UUID id, @PathVariable UUID organizerId) throws Exception {
        return eventService.uploadImage(body, organizerId, id);
    }
}
