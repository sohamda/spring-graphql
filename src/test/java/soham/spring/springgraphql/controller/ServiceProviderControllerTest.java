package soham.spring.springgraphql.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.graphql.tester.AutoConfigureGraphQlTester;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.graphql.test.tester.GraphQlTester;

@SpringBootTest
@AutoConfigureGraphQlTester
public class ServiceProviderControllerTest {

    @Autowired
    private GraphQlTester graphQlTester;

    @Test
    void getAllServices() {
        String query = "{ services { name }}";
        graphQlTester.document(query).execute().path("data.services[*].name").entityList(String.class).hasSize(12);
    }

    @Test
    void getServiceById() {
        String query = "{ serviceById(id:12) { name }}";
        graphQlTester.document(query).execute().path("data.serviceById.name").entity(String.class).isEqualTo("Virtualization");
    }

    @Test
    void getAllProviders() {
        String query = "{ providers { name }}";
        graphQlTester.document(query).execute().path("data.providers[*].name").entityList(String.class).hasSize(4);
    }

    @Test
    void getProviderById() {
        String query = "{ providerById(id:2) { name }}";
        graphQlTester.document(query).execute().path("data.providerById.name").entity(String.class).isEqualTo("Loco Poco");
    }
}
