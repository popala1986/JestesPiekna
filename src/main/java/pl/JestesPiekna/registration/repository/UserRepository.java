package pl.JestesPiekna.registration.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.JestesPiekna.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByUsername(String username);


    User findByEmail(String email);

    @Query("SELECT u FROM User u WHERE u.activationToken = :activation_token")
    User findUserByActivationToken(@Param("activation_token") String activation_token);

}