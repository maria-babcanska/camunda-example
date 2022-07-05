package com.ibm.camundaexample.service;

import com.ibm.camundaexample.db.LogEntryRepository;
import com.ibm.camundaexample.model.LogEntry;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class EndService implements JavaDelegate {

    private final LogEntryRepository logEntryRepository;

    @Autowired
    public EndService(LogEntryRepository logEntryRepository) {
        this.logEntryRepository = logEntryRepository;
    }

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        String processId = execution.getProcessInstanceId();
        System.out.printf("Entered end service, process id is %s%n", processId);

        String message = "Process is completed";
        LogEntry logEntry = new LogEntry(message, LocalDateTime.now(), processId, this.getClass().getName());
        logEntryRepository.save(logEntry);
    }

}
