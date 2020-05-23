package org.com.taskmanagement.strategies;

import org.com.taskmanagement.ProcessContainer;

import java.util.Collection;

/**
 * Interface for Task Manager strategy.
 */
public interface TaskManagerStrategy {
    /**
     * Registers a process based on this strategy.
     * @param process the process.
     */
    void add(final ProcessContainer process);

    /**
     * List the current running processes not sorted in any order.
     * @return running processes list.
     */
    Collection<ProcessContainer> list();

    /**
     * Deregister and kill selected processes.
     * @param list of selected processes to kill and deregister.
     */
    void removeAll(Collection<ProcessContainer> list);

    /**
     * Deregister and kill a single process.
     * @param process to kill and remove.
     */
    void remove(ProcessContainer process);
}
