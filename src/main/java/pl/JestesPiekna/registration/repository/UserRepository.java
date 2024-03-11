package pl.JestesPiekna.registration.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import pl.JestesPiekna.model.User;


import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {


    Optional<User> findByUsername(String username);

    User findByEmail(String email);

}