package com.ibm.camundaexample.db;

import com.ibm.camundaexample.model.LogEntry;

import java.time.LocalDateTime;
import java.util.List;

public interface LogEntryQueriesRepository {
    List<LogEntry> queryByParams(LocalDateTime fromTime, LocalDateTime toTime, String orderByFieldName, String ascOrDescOrder);
}
