package soham.spring.springgraphql.config;

import graphql.GraphqlErrorBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.graphql.execution.BatchLoaderRegistry;
import org.springframework.graphql.execution.DataFetcherExceptionResolver;
import org.springframework.graphql.execution.DataFetcherExceptionResolverAdapter;
import org.springframework.graphql.execution.ErrorType;
import soham.spring.springgraphql.entity.Provider;
import soham.spring.springgraphql.error.NoDataFoundError;
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

    @Bean
    public DataFetcherExceptionResolver exceptionResolver() {
        return DataFetcherExceptionResolverAdapter.from((ex, env) -> {
            if (ex instanceof NoDataFoundError ndf) {
                return GraphqlErrorBuilder.newError(env).message(ndf.getMessage()).errorType(ErrorType.NOT_FOUND).build();
            }
            else {
                return GraphqlErrorBuilder.newError(env).message(ex.getMessage()).errorType(ErrorType.INTERNAL_ERROR).build();
            }
        });
    }
}
