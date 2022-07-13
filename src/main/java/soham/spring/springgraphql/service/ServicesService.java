package soham.spring.springgraphql.service;

import org.springframework.beans.factory.annotation.Autowired;
import soham.spring.springgraphql.entity.Provider;
import soham.spring.springgraphql.entity.Service;
import soham.spring.springgraphql.error.NoDataFoundError;
import soham.spring.springgraphql.repository.ServiceRepository;

import java.util.List;
import java.util.Optional;

@org.springframework.stereotype.Service
public class ServicesService {

    @Autowired
    private ServiceRepository serviceRepository;

    public List<Service> findAllServices() {
        return serviceRepository.findAll();
    }

    public Service findServiceById(Integer serviceId) {
        Optional<Service> service = serviceRepository.findById(serviceId);

        if(service.isEmpty()) {
            throw new NoDataFoundError("No Provider found", "SER-001");
        }
        return service.get();
    }

    public List<Service>  findProviderOfService(Provider provider) {
        return serviceRepository.findByProviderId(provider.getId());
    }

    public Service addService(String name, String description, String providerId) {
        Integer maxId = serviceRepository.maxId();

        return serviceRepository.save(Service.builder().id(maxId+1)
                .name(name).description(description).providerId(Integer.parseInt(providerId))
                .build());
    }
}
