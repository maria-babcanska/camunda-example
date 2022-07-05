package com.ibm.camundaexample.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.util.MultiValueMap;

import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProcessResponse {

    private String message;

    private String urlToContinueProcess;

    private String urlToStartNewProcess;

    private Map<String, List<String>> options;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUrlToContinueProcess() {
        return urlToContinueProcess;
    }

    public void setUrlToContinueProcess(String urlToContinueProcess) {
        this.urlToContinueProcess = urlToContinueProcess;
    }

    public Map<String, List<String>> getOptions() {
        return options;
    }

    public void setOptions(Map<String, List<String>> options) {
        this.options = options;
    }

    public String getUrlToStartNewProcess() {
        return urlToStartNewProcess;
    }

    public void setUrlToStartNewProcess(String urlToStartNewProcess) {
        this.urlToStartNewProcess = urlToStartNewProcess;
    }

}
