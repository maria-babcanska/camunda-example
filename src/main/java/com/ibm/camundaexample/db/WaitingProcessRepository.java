package com.ibm.camundaexample.db;

import com.ibm.camundaexample.model.WaitingProcess;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface WaitingProcessRepository extends CrudRepository<WaitingProcess, Long> {
    List<WaitingProcess> findAll();

    void deleteByProcessId(String processId);

}
