package org.com.taskmanagement;

/**
 * Assumed interface for a process.
 */
public interface TaskProcess {
    /**
     * Id of the process.
     * @return id
     */
    int getId();

    /**
     * Priority of the process. Higher number is higher priority.
     * @return priority
     */
    int getPriority();

    /**
     * Kills the process.
     */
    void kill();
}
