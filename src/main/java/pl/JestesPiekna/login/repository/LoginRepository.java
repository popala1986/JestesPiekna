package pl.JestesPiekna.login.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.JestesPiekna.model.User;

import java.util.Optional;

@Repository
public interface LoginRepository extends JpaRepository<User,Integer> {

    Optional<User> findByUsername(String username);


}
