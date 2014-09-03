package hello.data.repositories;

import hello.data.entities.Level0Item;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface Level0Repository extends PagingAndSortingRepository<Level0Item, Long> {

}
