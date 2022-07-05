package com.ibm.camundaexample.model;


import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "log_entry")
public class LogEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String message;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "process_id")
    private String processId;

    @Column(name = "source_class")
    private String sourceClass;

    public LogEntry() {
    }

    public LogEntry(String message, LocalDateTime createdAt, String processId, String sourceClass) {
        this.message = message;
        this.createdAt = createdAt;
        this.processId = processId;
        this.sourceClass = sourceClass;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getProcessId() {
        return processId;
    }

    public void setProcessId(String processId) {
        this.processId = processId;
    }

    public String getSourceClass() {
        return sourceClass;
    }

    public void setSourceClass(String sourceClass) {
        this.sourceClass = sourceClass;
    }

    @Override
    public String toString() {
        return "LogEntry{" +
                "id=" + id +
                ", message='" + message + '\'' +
                ", createdAt=" + createdAt +
                ", createdBy='" + processId + '\'' +
                ", sourceClass='" + sourceClass + '\'' +
                '}';
    }
}
