package mtn.hackaton.beveapp.repository;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import mtn.hackaton.beveapp.domain.CreateurAfricain;
import org.hibernate.annotations.QueryHints;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

/**
 * Utility repository to load bag relationships based on https://vladmihalcea.com/hibernate-multiplebagfetchexception/
 */
public class CreateurAfricainRepositoryWithBagRelationshipsImpl implements CreateurAfricainRepositoryWithBagRelationships {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<CreateurAfricain> fetchBagRelationships(Optional<CreateurAfricain> createurAfricain) {
        return createurAfricain.map(this::fetchInspirations);
    }

    @Override
    public Page<CreateurAfricain> fetchBagRelationships(Page<CreateurAfricain> createurAfricains) {
        return new PageImpl<>(
            fetchBagRelationships(createurAfricains.getContent()),
            createurAfricains.getPageable(),
            createurAfricains.getTotalElements()
        );
    }

    @Override
    public List<CreateurAfricain> fetchBagRelationships(List<CreateurAfricain> createurAfricains) {
        return Optional.of(createurAfricains).map(this::fetchInspirations).orElse(Collections.emptyList());
    }

    CreateurAfricain fetchInspirations(CreateurAfricain result) {
        return entityManager
            .createQuery(
                "select createurAfricain from CreateurAfricain createurAfricain left join fetch createurAfricain.inspirations where createurAfricain is :createurAfricain",
                CreateurAfricain.class
            )
            .setParameter("createurAfricain", result)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getSingleResult();
    }

    List<CreateurAfricain> fetchInspirations(List<CreateurAfricain> createurAfricains) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, createurAfricains.size()).forEach(index -> order.put(createurAfricains.get(index).getId(), index));
        List<CreateurAfricain> result = entityManager
            .createQuery(
                "select distinct createurAfricain from CreateurAfricain createurAfricain left join fetch createurAfricain.inspirations where createurAfricain in :createurAfricains",
                CreateurAfricain.class
            )
            .setParameter("createurAfricains", createurAfricains)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}
