package com.ibm.camundaexample.db;

import com.ibm.camundaexample.model.LogEntry;
import org.springframework.data.repository.CrudRepository;

public interface LogEntryRepository extends CrudRepository<LogEntry, Long>, LogEntryQueriesRepository {

}
