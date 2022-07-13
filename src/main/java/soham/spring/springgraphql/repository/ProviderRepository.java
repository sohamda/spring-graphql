package soham.spring.springgraphql.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import soham.spring.springgraphql.entity.Provider;

import java.util.List;

@Transactional
public interface ProviderRepository extends JpaRepository<Provider, Integer> {
    public List<Provider> findByIdIn(List<Integer> ids);

    @Query(value = "SELECT max(id) FROM Provider")
    Integer maxId();
}
