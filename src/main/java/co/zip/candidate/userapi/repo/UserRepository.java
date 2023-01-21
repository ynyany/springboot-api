package co.zip.candidate.userapi.repo;

import co.zip.candidate.userapi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "user", path = "user")
public interface UserRepository extends PagingAndSortingRepository<User, Long>, JpaRepository<User,Long> {

//	List<User> findByLastName(@Param("name") String name);

}
