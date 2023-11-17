package esercitazionesettimasettimana.repositories;

import esercitazionesettimasettimana.enteties.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.UUID;

@Repository
public interface EventRepository extends JpaRepository<Event, UUID> {
    Page<Event> findByDateAfter(LocalDate now, Pageable pageable);
}
