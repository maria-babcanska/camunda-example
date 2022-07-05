package com.ibm.camundaexample.service;

import com.ibm.camundaexample.db.LogEntryRepository;
import com.ibm.camundaexample.db.WaitingProcessRepository;
import com.ibm.camundaexample.model.LogEntry;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AfterWaitStateService implements JavaDelegate {

    private final WaitingProcessRepository waitingProcessRepository;

    private final LogEntryRepository logEntryRepository;

    @Autowired
    public AfterWaitStateService(WaitingProcessRepository waitingProcessRepository, LogEntryRepository logEntryRepository) {
        this.waitingProcessRepository = waitingProcessRepository;
        this.logEntryRepository = logEntryRepository;
    }

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        String processId = execution.getProcessInstanceId();
        System.out.printf("Entered AfterWaitStateService, process id is %s%n", processId);

        String message = "Process is exiting wait state and continues, going to delete process from waiting_process db.";
        LogEntry logEntry = new LogEntry(message, LocalDateTime.now(), processId, this.getClass().getName());
        logEntryRepository.save(logEntry);
        waitingProcessRepository.deleteByProcessId(processId);
    }

}
