package pl.JestesPiekna.serviceType.service;

import org.springframework.stereotype.Service;
import pl.JestesPiekna.model.ServiceType;
import pl.JestesPiekna.serviceType.repository.ServiceTypeRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ServiceTypeService {

    private final ServiceTypeRepository serviceTypeRepository;

    public ServiceTypeService(ServiceTypeRepository serviceTypeRepository) {
        this.serviceTypeRepository = serviceTypeRepository;
    }

    public List<ServiceType> getAllServiceTypes() {
        return serviceTypeRepository.findAll();
    }

    public ServiceType getServiceTypeById(Integer id) {
        Optional<ServiceType> serviceTypeOptional = serviceTypeRepository.findById(id);
        return serviceTypeOptional.orElse(null);
    }
}
