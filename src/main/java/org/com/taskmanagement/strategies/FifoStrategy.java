package org.com.taskmanagement.strategies;

import org.com.taskmanagement.ProcessContainer;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Queue;

/**
 * First In First Out strategy for task manager.
 * Removes the first process if we are limit and a new process is added.
 */
public class FifoStrategy implements
        TaskManagerStrategy {
    private final Queue<ProcessContainer> runningProcesses;
    private final int limit;

    public FifoStrategy(int limit) {
        this.runningProcesses = new LinkedList<>();
        this.limit = limit;
    }


    @Override
    public void add(final ProcessContainer process) {
        runningProcesses.add(process);
        if (runningProcesses.size() > limit) {
            this.runningProcesses.poll().getProcess().kill();
        }
    }

    @Override
    public Collection<ProcessContainer> list() {
        return runningProcesses;
    }

    @Override
    public void removeAll(Collection<ProcessContainer> processes) {
        processes.forEach(process -> process.getProcess().kill());
        runningProcesses.removeAll(processes);
    }

    @Override
    public void remove(ProcessContainer process) {
        process.getProcess().kill();
        runningProcesses.remove(process);
    }
}
