package hello.data.repositories;

import hello.data.entities.Level2Item;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface Level2Repository extends PagingAndSortingRepository<Level2Item, Long> {

}
