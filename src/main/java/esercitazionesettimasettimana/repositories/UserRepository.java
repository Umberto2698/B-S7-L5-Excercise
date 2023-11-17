package esercitazionesettimasettimana.repositories;

import esercitazionesettimasettimana.enteties.User;
import esercitazionesettimasettimana.payloads.users.UserResponseDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<UserResponseDTO> findByEmail(String email);
}
