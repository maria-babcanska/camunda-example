package com.ibm.camundaexample.model;


import javax.persistence.*;

@Entity
@Table(name = "waiting_process")
public class WaitingProcess {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column
    private Long id;

    @Column(name="process_id", nullable = false)
    private String processId;

    public WaitingProcess() {
    }

    public WaitingProcess(String processId) {
        this.processId = processId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProcessId() {
        return processId;
    }

    public void setProcessId(String processId) {
        this.processId = processId;
    }

    @Override
    public String toString() {
        return "ProcessInWaitState{" +
                "id=" + id +
                ", processId=" + processId +
                '}';
    }
}
