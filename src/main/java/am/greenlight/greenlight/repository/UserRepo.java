package am.greenlight.greenlight.repository;

import am.greenlight.greenlight.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepo extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);
    List<User> findByNameAndSurname(String name, String surname);
}
