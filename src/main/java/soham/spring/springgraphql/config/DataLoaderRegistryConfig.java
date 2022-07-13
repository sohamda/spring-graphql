package soham.spring.springgraphql.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.graphql.execution.BatchLoaderRegistry;
import soham.spring.springgraphql.entity.Provider;
import soham.spring.springgraphql.service.ProviderService;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.stream.Collectors;

@Configuration
@RequiredArgsConstructor
public class DataLoaderRegistryConfig {

    private final BatchLoaderRegistry registry;
    private final ProviderService providerService;

    @PostConstruct
    public void registerDataLoader() {
        registry.forTypePair(Integer.class, Provider.class)
                .registerMappedBatchLoader((providerIds, env) -> providerService.findAllProvidersByIds(providerIds).
                map(v -> Map.entry(v.getId(), v))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)));
    }
}
