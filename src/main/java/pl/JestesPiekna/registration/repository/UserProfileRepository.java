package pl.JestesPiekna.registration.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.JestesPiekna.model.UserProfile;


@Repository
public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {

    UserProfile findByPhone(String phone);

}
