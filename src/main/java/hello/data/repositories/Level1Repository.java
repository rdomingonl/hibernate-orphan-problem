package hello.data.repositories;

import hello.data.entities.Level1Item;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface Level1Repository extends PagingAndSortingRepository<Level1Item, Long> {

}
