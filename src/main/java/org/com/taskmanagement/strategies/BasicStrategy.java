package org.com.taskmanagement.strategies;

import org.com.taskmanagement.ProcessContainer;

import java.util.Collection;
import java.util.LinkedList;

/**
 * Basic strategy for Task Manager.
 * Doesn't accept new processes, if already we are at "limit".
 */
public class BasicStrategy implements TaskManagerStrategy {
    private final LinkedList<ProcessContainer> runningProcesss;
    private final int limit;

    public BasicStrategy(int limit) {
        this.runningProcesss = new LinkedList<>();
        this.limit = limit;
    }

    @Override
    public void add(ProcessContainer process) {
        if (runningProcesss.size() == limit) {
            return;
        }
        runningProcesss.add(process);
    }

    @Override
    public Collection<ProcessContainer> list() {
        return runningProcesss;
    }

    @Override
    public void removeAll(Collection<ProcessContainer> processes) {
        processes.forEach(process -> process.getProcess().kill());
        runningProcesss.removeAll(processes);
    }

    @Override
    public void remove(ProcessContainer process) {
        process.getProcess().kill();
        runningProcesss.remove(process);
    }
}
