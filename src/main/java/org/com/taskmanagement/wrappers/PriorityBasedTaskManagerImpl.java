package org.com.taskmanagement.wrappers;

import org.com.taskmanagement.TaskManagerImpl;
import org.com.taskmanagement.strategies.PriorityBasedStrategy;

import java.time.Clock;

public class PriorityBasedTaskManagerImpl extends TaskManagerImpl {
    public PriorityBasedTaskManagerImpl(int limit, Clock clock) {
        super(limit, clock, new PriorityBasedStrategy(limit));
    }
}