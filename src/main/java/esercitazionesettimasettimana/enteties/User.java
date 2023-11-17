package esercitazionesettimasettimana.enteties;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.github.javafaker.Faker;
import esercitazionesettimasettimana.enums.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder(builderClassName = "UserBuilder")
@Entity
@JsonIgnoreProperties({"events", "createdAt", "password", "authorities", "enabled", "credentialsNonExpired", "accountNonExpired", "accountNonLocked"})
public class User implements UserDetails {
    @Id
    private UUID id;
    private String name;
    private String surname;
    private String email;
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role;

    @ManyToMany(mappedBy = "users", cascade = CascadeType.REMOVE)
    private Set<Event> events = new HashSet<>();

    @CreationTimestamp
    @Column(name = "creation_date")
    private Date createdAt;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(this.role.name()));
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public static class UserBuilder {
        Faker faker = new Faker(Locale.ITALY);
        private String name = faker.name().firstName();
        private String surname = faker.name().lastName();
        private String email = name + "." + surname + "@gmail.com";
        private String password = faker.phoneNumber().cellPhone();
        private Role role = Role.USER;
    }


}
