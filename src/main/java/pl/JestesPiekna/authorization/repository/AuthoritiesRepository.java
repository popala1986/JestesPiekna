package pl.JestesPiekna.authorization.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.JestesPiekna.model.Authorities;

import java.util.Optional;

@Repository
public interface AuthoritiesRepository extends JpaRepository<Authorities, Integer> {

    Optional<Authorities> findByUsernameAndAuthority(String admin, String roleAdmin);
}
