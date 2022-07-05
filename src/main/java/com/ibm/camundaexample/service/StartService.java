package com.ibm.camundaexample.service;

import com.ibm.camundaexample.db.LogEntryRepository;
import com.ibm.camundaexample.model.LogEntry;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class StartService implements JavaDelegate {

    private final LogEntryRepository logEntryRepository;

    @Autowired
    public StartService(LogEntryRepository logEntryRepository) {
        this.logEntryRepository = logEntryRepository;
    }

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        String processId = execution.getProcessInstanceId();
        System.out.printf("Entered start service, process id is %s%n", processId);

        Object skipWaitParam = execution.getVariable("skipWait");
        Boolean skipWait = getSkipWait(skipWaitParam);
        String message = null;
        if (skipWait != null) {
            message = String.format("Process is started, skipWait parameter is set to %s, process %s enter wait state",
                    skipWait, skipWait ? "will not" : "will");
        } else {
            message = "Process is started, skipWait parameter could not be determined";
        }

        LogEntry logEntry = new LogEntry(message, LocalDateTime.now(), processId, this.getClass().getName());
        logEntryRepository.save(logEntry);
    }

    private Boolean getSkipWait(Object skipWaitParam) {
        Boolean skipWait = null;
        if (skipWaitParam instanceof Boolean) {
            skipWait = (Boolean) skipWaitParam;
        }
        return skipWait;
    }
}
