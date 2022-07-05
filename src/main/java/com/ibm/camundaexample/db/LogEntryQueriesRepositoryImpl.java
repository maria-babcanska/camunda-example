package com.ibm.camundaexample.db;

import com.ibm.camundaexample.model.LogEntry;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class LogEntryQueriesRepositoryImpl implements LogEntryQueriesRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<LogEntry> queryByParams(LocalDateTime fromTime, LocalDateTime toTime, String orderByFieldName, String ascOrDescOrder) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<LogEntry> criteriaQuery = criteriaBuilder.createQuery(LogEntry.class);
        Root<LogEntry> table = criteriaQuery.from(LogEntry.class);

        if (orderByFieldName != null) {
            if ("asc".equalsIgnoreCase(ascOrDescOrder) || "ascending".equalsIgnoreCase(ascOrDescOrder)) {
                criteriaQuery = criteriaQuery.orderBy(criteriaBuilder.asc(table.get(orderByFieldName)));
            } else if ("desc".equalsIgnoreCase(ascOrDescOrder) || "descending".equalsIgnoreCase(ascOrDescOrder)) {
                criteriaQuery = criteriaQuery.orderBy(criteriaBuilder.desc(table.get(orderByFieldName)));
            } else { // e.g. wrong user input
                criteriaQuery = criteriaQuery.orderBy(criteriaBuilder.asc(table.get(orderByFieldName)));
            }
        }
        Predicate predicateForTime = getPredicateForTime(fromTime, toTime, criteriaBuilder, table);

        if (predicateForTime != null) {
            criteriaQuery.where(predicateForTime);
        }
        criteriaQuery = criteriaQuery.select(table);

        return entityManager.createQuery(criteriaQuery).getResultList();
    }

    private Predicate getPredicateForTime(LocalDateTime fromTime, LocalDateTime toTime, CriteriaBuilder criteriaBuilder, Root<LogEntry> table) {
        Predicate greaterThanFrom = null;
        if (fromTime != null) {
            greaterThanFrom = criteriaBuilder.greaterThanOrEqualTo(table.get("createdAt"), fromTime);
        }

        Predicate lessThanTo = null;
        if (toTime != null) {
            lessThanTo = criteriaBuilder.lessThanOrEqualTo(table.get("createdAt"), toTime);
        }

        Predicate predicateForTime = null;
        if (greaterThanFrom != null && lessThanTo != null) {
            predicateForTime = criteriaBuilder.and(greaterThanFrom, lessThanTo);
        } else if (greaterThanFrom != null) {
            predicateForTime = greaterThanFrom;
        } else if (lessThanTo != null) {
            predicateForTime = lessThanTo;
        }
        return predicateForTime;
    }
}
