package org.com.taskmanagement.strategies;

import org.com.taskmanagement.TaskProcess;
import org.com.taskmanagement.ProcessContainer;
import org.com.taskmanagement.sorting.TaskProcessComparators;

import java.util.Collection;
import java.util.PriorityQueue;

/**
 * Priority strategy for Task manager.
 * Removes lowest priority process, when a new process is added at limit.
 */
public class PriorityBasedStrategy implements TaskManagerStrategy {
    private final int limit;
    PriorityQueue<ProcessContainer> runningProcesses;

    public PriorityBasedStrategy(int limit) {
        runningProcesses = new PriorityQueue<>(TaskProcessComparators.PRIORITY_COMPARATOR);
        this.limit = limit;
    }

    @Override
    public void add(final ProcessContainer process) {
        runningProcesses.add(process);
        if (runningProcesses.size() > limit) {
            TaskProcess processToKill = runningProcesses.poll().getProcess();
            processToKill.kill();
        }
    }

    @Override
    public Collection<ProcessContainer> list() {
        return runningProcesses;
    }

    @Override
    public void removeAll(Collection<ProcessContainer> list) {
        list.forEach(process -> process.getProcess().kill());
        runningProcesses.removeAll(list);
    }

    @Override
    public void remove(ProcessContainer process) {
        process.getProcess().kill();
        runningProcesses.remove(process);
    }
}
