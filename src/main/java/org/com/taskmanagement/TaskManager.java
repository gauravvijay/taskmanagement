package org.com.taskmanagement;

import org.com.taskmanagement.sorting.SortBy;

import java.util.List;

/**
 * Api interface for a task manager.
 */
public interface TaskManager {
    /**
     * Adds a process to be managed by the task manager.
     * @param process to be added.
     */
    void add(TaskProcess process);

    /**
     * Returns a sorted list of processes based on the parameter.
     * @param sortBy sorting criteria.
     * @return list of processes sorted by the given sorting criterion.
     */
    List<TaskProcess> list(SortBy sortBy);

    /**
     * Kill a process by its id.
     * @param processId process's unique id.
     */
    void kill(int processId);

    /**
     * Kill a group of processes by their priority.
     * @param priority process's priority.
     */
    void killGroup(int priority);

    /**
     * Kill all processes.
     */
    void killAll();
}
