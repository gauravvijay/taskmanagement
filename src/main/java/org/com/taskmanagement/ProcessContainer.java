package org.com.taskmanagement;

import java.time.Instant;

/**
 * Wrapping container to store auxiliary information for a process.
 */
public class ProcessContainer {
    private TaskProcess process;
    private Instant timeOfRegistration;

    ProcessContainer(TaskProcess process, Instant timeOfRegistration) {
        this.setProcess(process);
        this.setTimeOfRegistration(timeOfRegistration);
    }

    public TaskProcess getProcess() {
        return process;
    }

    public Instant getTimeOfRegistration() {
        return timeOfRegistration;
    }

    public void setProcess(TaskProcess process) {
        this.process = process;
    }

    public void setTimeOfRegistration(Instant timeOfRegistration) {
        this.timeOfRegistration = timeOfRegistration;
    }
}
