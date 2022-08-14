package mtn.hackaton.beveapp.repository;

import java.util.List;
import java.util.Optional;
import mtn.hackaton.beveapp.domain.CreateurAfricain;
import org.springframework.data.domain.Page;

public interface CreateurAfricainRepositoryWithBagRelationships {
    Optional<CreateurAfricain> fetchBagRelationships(Optional<CreateurAfricain> createurAfricain);

    List<CreateurAfricain> fetchBagRelationships(List<CreateurAfricain> createurAfricains);

    Page<CreateurAfricain> fetchBagRelationships(Page<CreateurAfricain> createurAfricains);
}
