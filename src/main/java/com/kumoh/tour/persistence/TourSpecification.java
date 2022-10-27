package com.kumoh.tour.persistence;

import com.kumoh.tour.entity.TourEntity;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TourSpecification implements Specification<TourEntity> {
    @Override
    public Predicate toPredicate(Root<TourEntity> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        return null;
    }

    @Override
    public Specification<TourEntity> and(Specification<TourEntity> other) {
        return Specification.super.and(other);
    }

    @Override
    public Specification<TourEntity> or(Specification<TourEntity> other) {
        return Specification.super.or(other);
    }

    public static Specification<TourEntity> searchWith(Map<String, Object> searchKeywords) {
        return ((root, query, builder) -> {
            //List<Predicate> predicates = getPredicateWithKeys(searchKeywords, root, builder);

            //return builder.and(predicates.toArray(new Predicate[0]));
            return null;
        });
    }

//    public static List<Predicate> getPredicateWithKeys(Map<String, Object> keywords, Root<TourEntity> root, CriteriaBuilder builder) {
//        List<Predicate> predicates = new ArrayList<>();
//        for (String key : keywords.keySet()) {
//            if (key.equals("title")) {
//                predicates.add(builder.like(root.get(key), "%" + keywords.get("title") + "%"));
//            } else if (key.equals("activity")) {
//                predicates.add(builder.like(root.get(key), "%" + keywords.get("activity") + "%"));
//            }  else if (key.equals("departureLat")) {
//                CriteriaQuer
//                predicates.add(builder)
//            }else {
//                predicates.add(builder.equal(root.get(key), keywords.get(key)));
//            }
//        }
//
//        return predicates;
//    }
}
