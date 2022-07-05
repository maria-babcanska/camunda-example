package com.ibm.camundaexample.api;

import com.ibm.camundaexample.db.LogEntryRepository;
import com.ibm.camundaexample.db.WaitingProcessRepository;
import com.ibm.camundaexample.model.LogEntry;
import org.camunda.bpm.engine.RuntimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/audit")
public class AuditController {

    private final LogEntryRepository logEntryRepository;

    @Autowired
    public AuditController(RuntimeService runtimeService, LogEntryRepository logEntryRepository) {
        this.logEntryRepository = logEntryRepository;
    }

    @GetMapping("/listLogEntries")
    public ResponseEntity<List<LogEntry>> listLogEntries(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fromTime,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime toTime,
            @RequestParam(required = false) String orderByField,
            @RequestParam(required = false, defaultValue = "asc") String ascOrDescOrder) {

        List<LogEntry> logEntryList = logEntryRepository.queryByParams(fromTime, toTime, orderByField, ascOrDescOrder);
        return ResponseEntity.ok(logEntryList);
    }

}
