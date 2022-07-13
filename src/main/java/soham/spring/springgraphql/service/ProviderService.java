package soham.spring.springgraphql.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import soham.spring.springgraphql.entity.Provider;
import soham.spring.springgraphql.error.NoDataFoundError;
import soham.spring.springgraphql.repository.ProviderRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class ProviderService {

    @Autowired
    private ProviderRepository providerRepository;

    public List<Provider> findAllProviders() {
        return providerRepository.findAll();
    }

    public Provider findProviderById(Integer providerId) {
        Optional<Provider> provider = providerRepository.findById(providerId);

        if(provider.isEmpty()) {
            throw new NoDataFoundError("No Provider found", "PRV-001");
        }
        return provider.get();
    }

    public Flux<Provider> findAllProvidersByIds(Set<Integer> ids) {
        return Flux.fromStream(providerRepository.findByIdIn(ids.stream().toList()).stream());
    }

    public Provider addProvider(String name, String description) {
        Integer maxId = providerRepository.maxId();

        return providerRepository.save(Provider.builder().id(maxId+1).name(name).description(description).build());
    }
}
