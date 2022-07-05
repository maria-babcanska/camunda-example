package com.ibm.camundaexample.service;

import com.ibm.camundaexample.db.LogEntryRepository;
import com.ibm.camundaexample.db.WaitingProcessRepository;
import com.ibm.camundaexample.model.LogEntry;
import com.ibm.camundaexample.model.WaitingProcess;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class BeforeWaitStateService implements JavaDelegate {

    private final WaitingProcessRepository waitingProcessRepository;

    private final LogEntryRepository logEntryRepository;

    @Autowired
    public BeforeWaitStateService(WaitingProcessRepository waitingProcessRepository, LogEntryRepository logEntryRepository) {
        this.waitingProcessRepository = waitingProcessRepository;
        this.logEntryRepository = logEntryRepository;
    }

    @Override
    @Transactional
    public void execute(DelegateExecution execution) throws Exception {
        String processId = execution.getProcessInstanceId();
        System.out.printf("Entered BeforeWaitStateService, process id is %s%n", processId);

        String message = "Process is entering wait state, going to insert process in waiting_process db.";
        LogEntry logEntry = new LogEntry(message, LocalDateTime.now(), processId, this.getClass().getName());
        logEntryRepository.save(logEntry);

        WaitingProcess waitingProcess = new WaitingProcess(processId);
        waitingProcessRepository.save(waitingProcess);
    }

}
