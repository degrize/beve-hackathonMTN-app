package mtn.hackaton.beveapp.repository;

import java.util.List;
import java.util.Optional;
import mtn.hackaton.beveapp.domain.Souscription;
import org.springframework.data.domain.Page;

public interface SouscriptionRepositoryWithBagRelationships {
    Optional<Souscription> fetchBagRelationships(Optional<Souscription> souscription);

    List<Souscription> fetchBagRelationships(List<Souscription> souscriptions);

    Page<Souscription> fetchBagRelationships(Page<Souscription> souscriptions);
}
