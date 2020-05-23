package org.com.taskmanagement;

import org.com.taskmanagement.sorting.SortBy;
import org.com.taskmanagement.strategies.TaskManagerStrategy;
import org.com.taskmanagement.sorting.TaskProcessComparators;

import java.time.Clock;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Implementation for TaskManager.
 */
public class TaskManagerImpl implements TaskManager {
    protected final Clock clock;
    protected final int limit;
    private final TaskManagerStrategy strategy;

    public TaskManagerImpl(int limit, Clock clock, TaskManagerStrategy strategy) {
        this.limit = limit;
        this.clock = clock;
        this.strategy = strategy;
    }

    @Override
    public void add(final TaskProcess process) {
        if (limit <= 0) {
            // We chose to return without doing anything.
            return;
        }
        strategy.add(new ProcessContainer(process, clock.instant()));
    }

    @Override
    public List<TaskProcess> list(SortBy sortBy) {
        Optional<Comparator<ProcessContainer>> comparatorOptional =
                TaskProcessComparators.getComparator(sortBy);
        if (comparatorOptional.isEmpty()) {
            return Collections.emptyList();
        }
        return strategy.list()
                .stream()
                .sorted(comparatorOptional.get())
                .map(ProcessContainer::getProcess)
                .collect(Collectors.toList());
    }

    @Override
    public void kill(int processId) {
        strategy.list()
                .stream()
                .filter(x -> x.getProcess().getId() == processId)
                .findFirst()
                .ifPresent(strategy::remove);
    }

    @Override
    public void killGroup(int priority) {
        strategy.removeAll(strategy.list()
                .stream()
                .filter(x -> x.getProcess().getPriority() == priority)
                .collect(Collectors.toList()));
    }

    @Override
    public void killAll() {
        strategy.removeAll(strategy.list());
    }

}
