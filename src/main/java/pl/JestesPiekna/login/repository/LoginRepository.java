package pl.JestesPiekna.login.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.JestesPiekna.model.User;

@Repository
public interface LoginRepository extends JpaRepository<User,Integer> {

}
