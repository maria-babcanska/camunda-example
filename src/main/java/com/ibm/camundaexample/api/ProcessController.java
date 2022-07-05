package com.ibm.camundaexample.api;

import com.ibm.camundaexample.db.WaitingProcessRepository;
import com.ibm.camundaexample.model.ProcessResponse;
import com.ibm.camundaexample.model.WaitingProcess;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.runtime.MessageCorrelationResult;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/camunda")
public class ProcessController {

    public static final String urlToStartNewProcess = "localhost:8081/camunda/start?skipWait={skipWait}";
    public static final String urlToContinueProcess = "localhost:8081/camunda/continue?processId={processId}&message=stop_waiting";

    private final RuntimeService runtimeService;

    private final WaitingProcessRepository waitingProcessRepository;

    public ProcessController(RuntimeService runtimeService, WaitingProcessRepository waitingProcessRepository) {
        this.runtimeService = runtimeService;
        this.waitingProcessRepository = waitingProcessRepository;
    }

    @GetMapping("/start")
    public ResponseEntity<ProcessResponse> startProcess(@RequestParam String skipWait) {
        Boolean gonnaSkipWait = "TRUE".equalsIgnoreCase(skipWait);

        Map<String, Object> params = new HashMap<>();
        params.put("skipWait", gonnaSkipWait);

        ProcessInstance pi = runtimeService.startProcessInstanceByKey("maria_process", params);
        String processId = pi.getProcessInstanceId();

        ProcessResponse processResponse = initializeProcessResponse();
        if (!gonnaSkipWait) {
            processResponse.setUrlToContinueProcess(urlToContinueProcess);
            String message =
                    "Process is going to enter waiting state. You can proceed by calling {urlToContinueProcess} or start a new one by calling {urlToStartNewProcess}";
            processResponse.setMessage(message);
            processResponse.getOptions().put("processId", Arrays.asList(processId));
            processResponse.getOptions().put("message", Arrays.asList("stop_waiting"));
            return ResponseEntity.ok(processResponse);
        } else {
            String message = "Process is not entering a wait state, process will be finished. You can start a new one by calling {urlToStartNewProcess}";
            processResponse.setMessage(message);
            return ResponseEntity.ok(processResponse);
        }
    }

    @GetMapping("/continue")
    public ResponseEntity<ProcessResponse> continueProcess(@RequestParam String processId, @RequestParam (defaultValue = "stop_waiting") String message) {

        ProcessResponse processResponse = initializeProcessResponse();

        MessageCorrelationResult result = runtimeService.createMessageCorrelation(message)
                .processInstanceId(processId).setVariables(new HashMap<>()).correlateWithResult();

        String processResponseMessage =
                String.format("Process with id %s exited wait state and was resumed, result of execution is %s, you can start a new one by calling {urlToStartNewProcess}",
                        processId, result.getResultType().toString());

        processResponse.setMessage(processResponseMessage);
        return ResponseEntity.ok(processResponse);
    }

    private ProcessResponse initializeProcessResponse() {
        ProcessResponse processResponse = new ProcessResponse();
        processResponse.setUrlToStartNewProcess(urlToStartNewProcess);
        Map<String, List<String>> options = new HashMap<>();
        processResponse.setOptions(options);
        options.put("skipWait", Arrays.asList("true", "false"));
        return processResponse;
    }

    @GetMapping("/listWaiting")
    public ResponseEntity<List<String>> listWaitingProcesses() {
        List<WaitingProcess> waitingProcessList = waitingProcessRepository.findAll();
        return ResponseEntity.ok(waitingProcessList.stream().map(WaitingProcess::getProcessId).collect(Collectors.toList()));
    }

}
