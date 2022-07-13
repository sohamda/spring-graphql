package soham.spring.springgraphql.controller;

import graphql.schema.DataFetchingEnvironment;
import lombok.RequiredArgsConstructor;
import org.dataloader.DataLoader;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;
import soham.spring.springgraphql.entity.Provider;
import soham.spring.springgraphql.entity.Service;
import soham.spring.springgraphql.service.ProviderService;
import soham.spring.springgraphql.service.ServicesService;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Controller
@RequiredArgsConstructor
public class ServiceProviderController {

    private final ServicesService servicesService;
    private final ProviderService providerService;

    @SchemaMapping(typeName = "Query", value = "services")
    public List<Service> findAllServices() {
        return servicesService.findAllServices();
    }

    @SchemaMapping(typeName = "Query", value= "serviceById")
    public Service findService(@Argument Integer id) {
        return servicesService.findServiceById(id);
    }

    @SchemaMapping(typeName = "Service")
    public CompletableFuture<Provider> provider(Service service, DataLoader<Integer, Provider> loader) {
        return loader.load(service.getProviderId());
    }

    @SchemaMapping(typeName = "Query", value = "providers")
    public List<Provider> findAllProviders() {
        return providerService.findAllProviders();
    }

    @SchemaMapping(typeName = "Query", value= "providerById")
    public Provider findProvider(@Argument Integer id) {
        return providerService.findProviderById(id);
    }

    //@SchemaMapping(typeName = "Mutation", value = "addService")
    @MutationMapping
    public Service addService(DataFetchingEnvironment dataFetchingEnvironment) {
        String name = dataFetchingEnvironment.getArgument("name");
        String description = dataFetchingEnvironment.getArgument("description");
        String providerId = dataFetchingEnvironment.getArgument("providerId");

        return servicesService.addService(name, description, providerId);
    }

    @MutationMapping
    public Provider addProvider(DataFetchingEnvironment dataFetchingEnvironment) {
        String name = dataFetchingEnvironment.getArgument("name");
        String description = dataFetchingEnvironment.getArgument("description");

        return providerService.addProvider(name, description);
    }
}
