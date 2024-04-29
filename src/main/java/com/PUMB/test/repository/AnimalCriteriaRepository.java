package com.PUMB.test.repository;

import com.PUMB.test.domain.entity.Animal;
import com.PUMB.test.viewLayer.dto.AnimalCriteria;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.*;

@Repository
@RequiredArgsConstructor
public class AnimalCriteriaRepository {

    @PersistenceContext
    private final EntityManager entityManager;

    public Page<Animal> findWithFilters(AnimalCriteria animalCriteria, Pageable pageable) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

        CriteriaQuery<Animal> criteriaQuery = criteriaBuilder.createQuery(Animal.class);
        Root<Animal> animalRoot = criteriaQuery.from(Animal.class);

        Predicate predicate = getPredicate(animalCriteria, animalRoot);
        criteriaQuery.where(predicate);

        List<Order> orders = getOrders(animalCriteria.getSortDirection(), animalCriteria.getSortField(), animalRoot);
        criteriaQuery.orderBy(orders);

        TypedQuery<Animal> typedQuery = entityManager.createQuery(criteriaQuery);

        List<Animal> animals = typedQuery.getResultList();

        return new PageImpl<>(animals, pageable, getAnimalCount(predicate));
    }

    private List<Order> getOrders(Sort.Direction sortDirection, String sortField, Root<Animal> animalRoot) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        List<Order> orders = new ArrayList<>();

        if (sortDirection != null && sortField != null) {
            Map<String, Expression<String>> fieldMap = new HashMap<>();

            fieldMap.put("name", animalRoot.get("name"));
            fieldMap.put("weight", animalRoot.get("weight"));
            fieldMap.put("cost", animalRoot.get("cost"));
            fieldMap.put("category", animalRoot.get("category"));
            fieldMap.put("type", animalRoot.get("type"));
            fieldMap.put("sex", animalRoot.get("sex"));

            Expression<?> sortExpression = fieldMap.get(sortField);

            if (sortExpression != null) {
                orders.add(sortDirection == Sort.Direction.ASC
                        ? criteriaBuilder.asc(sortExpression)
                        : criteriaBuilder.desc(sortExpression)
                );
            }
        }

        return orders;
    }

    private Predicate getPredicate(AnimalCriteria animalCriteria, Root<Animal> animalRoot) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

        List<Predicate> predicates = new ArrayList<>();

        if (Objects.nonNull(animalCriteria.getCategory())) {
            predicates.add(
                    criteriaBuilder.equal(animalRoot.get("category"), animalCriteria.getCategory())
            );
        }

        if (Objects.nonNull(animalCriteria.getTypes())) {
            predicates.add(animalRoot.get("type").in(animalCriteria.getTypes()));
        }

        if (Objects.nonNull(animalCriteria.getSexes())) {
            predicates.add(animalRoot.get("sex").in(animalCriteria.getSexes()));
        }

        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }

    private long getAnimalCount(Predicate predicate) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

        CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
        Root<Animal> countRoot = countQuery.from(Animal.class);

        countQuery.select(criteriaBuilder.count(countRoot)).where(predicate);

        return entityManager.createQuery(countQuery).getSingleResult();
    }
}