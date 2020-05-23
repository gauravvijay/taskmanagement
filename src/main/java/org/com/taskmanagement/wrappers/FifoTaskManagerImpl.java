package org.com.taskmanagement.wrappers;

import org.com.taskmanagement.TaskManagerImpl;
import org.com.taskmanagement.strategies.FifoStrategy;

import java.time.Clock;

public class FifoTaskManagerImpl extends TaskManagerImpl {
    public FifoTaskManagerImpl(int limit, Clock clock) {
        super(limit, clock, new FifoStrategy(limit));
    }
}