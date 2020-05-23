package org.com.taskmanagement.wrappers;

import org.com.taskmanagement.TaskManagerImpl;
import org.com.taskmanagement.strategies.BasicStrategy;

import java.time.Clock;

public class BasicTaskManagerImpl extends TaskManagerImpl {
    public BasicTaskManagerImpl(int limit, Clock clock) {
        super(limit, clock, new BasicStrategy(limit));
    }
}