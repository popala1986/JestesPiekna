package pl.JestesPiekna.serviceType.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.JestesPiekna.model.ServiceType;

public interface ServiceTypeRepository extends JpaRepository<ServiceType,Integer> {
}
