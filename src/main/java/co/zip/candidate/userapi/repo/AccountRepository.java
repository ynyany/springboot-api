package co.zip.candidate.userapi.repo;

import co.zip.candidate.userapi.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "account", path = "account")
public interface AccountRepository extends PagingAndSortingRepository<Account, Long>, JpaRepository<Account, Long> {


}
