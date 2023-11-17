package esercitazionesettimasettimana.services;

import esercitazionesettimasettimana.enteties.User;
import esercitazionesettimasettimana.exceptions.BadRequestException;
import esercitazionesettimasettimana.exceptions.UnauthorizedException;
import esercitazionesettimasettimana.payloads.users.RoleUpdateDTO;
import esercitazionesettimasettimana.payloads.users.UserDTO;
import esercitazionesettimasettimana.payloads.users.UserLoginDTO;
import esercitazionesettimasettimana.payloads.users.UserUpdateInfoDTO;
import esercitazionesettimasettimana.repositories.UserRepository;
import esercitazionesettimasettimana.security.JWTTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AuthServices {
    @Autowired
    private UserService userService;
    @Autowired
    private JWTTools jwtTools;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder bcrypt;

    public String authenticateUser(UserLoginDTO body) {
        User user = userService.findByEmail(body.email());

        if (bcrypt.matches(body.password(), user.getPassword())) {
            return jwtTools.createToken(user);
        } else {
            throw new UnauthorizedException("Email or password invalid.");
        }
    }

    public User save(UserDTO body) {
        userRepository.findByEmail(body.email()).ifPresent(a -> {
            throw new BadRequestException("The email" + a.getEmail() + " is alredy used.");
        });
        User user = User.builder().name(body.name()).email(body.email()).surname(body.surname()).password(bcrypt.encode(body.password())).build();
        return userRepository.save(user);
    }

    public User update(UUID id, UserUpdateInfoDTO body) {
        User found = userService.getById(id);
        if (!body.email().isEmpty()) {
            found.setEmail(body.email());
        }
        if (!body.name().isEmpty()) {
            found.setName(body.name());
        }
        if (!body.surname().isEmpty()) {
            found.setSurname(body.surname());
        }
        if (!body.password().isEmpty()) {
            found.setPassword(bcrypt.encode(body.password()));
        }
        return userRepository.save(found);
    }

    public User updateRole(UUID id, RoleUpdateDTO body) {
        User found = userService.getById(id);
        found.setRole(body.role());
        return userRepository.save(found);
    }
}
