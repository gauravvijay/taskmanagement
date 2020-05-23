package org.com.taskmanagement;

import org.com.taskmanagement.sorting.SortBy;
import org.com.taskmanagement.wrappers.FifoTaskManagerImpl;
import org.com.taskmanagement.wrappers.PriorityBasedTaskManagerImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Clock;
import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

/**
 * Tests for FifoTaskmanagerImpl.
 */
class FifoTaskManagerImplTest {
    private Clock mockClock;
    private TaskProcess tp3;
    private TaskProcess tp2;
    private TaskProcess tp1;
    FifoTaskManagerImpl fifoTaskManager;

    @BeforeEach
    void setup() {
        tp1 = mock(TaskProcess.class);
        tp2 = mock(TaskProcess.class);
        tp3 = mock(TaskProcess.class);
        mockClock = mock(Clock.class);
    }

    @Test
    void addingAProcessKillsPreviousProcessAtLimit() {
        when(mockClock.instant()).thenReturn(Instant.EPOCH);
        fifoTaskManager = new FifoTaskManagerImpl(1, mockClock);
        fifoTaskManager.add(tp1);
        fifoTaskManager.add(tp2);
        verify(tp1, times(1)).kill();
        assertEquals(1, fifoTaskManager.list(SortBy.PRIORITY).size());
    }

    @Test
    void listFilesById() {
        when(mockClock.instant()).thenReturn(Instant.EPOCH);
        when(tp1.getPriority()).thenReturn(1);
        when(tp2.getPriority()).thenReturn(2);
        when(tp3.getPriority()).thenReturn(1);
        when(tp1.getId()).thenReturn(1);
        when(tp2.getId()).thenReturn(2);
        when(tp3.getId()).thenReturn(3);
        fifoTaskManager = new FifoTaskManagerImpl(3, mockClock);
        // Add the tasks in non id order.
        fifoTaskManager.add(tp1);
        fifoTaskManager.add(tp3);
        fifoTaskManager.add(tp2);
        List<TaskProcess> taskProcesses = fifoTaskManager.list(SortBy.ID);
        assertEquals(1, taskProcesses.get(0).getId());
        assertEquals(2, taskProcesses.get(1).getId());
        assertEquals(3, taskProcesses.get(2).getId());
        assertEquals(taskProcesses.size(), 3);
    }

    @Test
    void listFilesByPriority() {
        when(mockClock.instant()).thenReturn(Instant.EPOCH);
        when(tp1.getPriority()).thenReturn(1);
        when(tp2.getPriority()).thenReturn(2);
        when(tp3.getPriority()).thenReturn(3);
        fifoTaskManager = new FifoTaskManagerImpl(3, mockClock);
        // Add the tasks in non id order.
        fifoTaskManager.add(tp1);
        fifoTaskManager.add(tp3);
        fifoTaskManager.add(tp2);
        List<TaskProcess> taskProcesses = fifoTaskManager.list(SortBy.PRIORITY);
        assertEquals(1, taskProcesses.get(0).getPriority());
        assertEquals(2, taskProcesses.get(1).getPriority());
        assertEquals(3, taskProcesses.get(2).getPriority());
        assertEquals(taskProcesses.size(), 3);
    }

    @Test
    void listFilesByRegistrationTime() {
        when(mockClock.instant())
                .thenReturn(Instant.EPOCH)
                .thenReturn(Instant.EPOCH.plusNanos(1))
                .thenReturn(Instant.EPOCH.plusNanos(2));
        when(tp1.getPriority()).thenReturn(1);
        when(tp2.getPriority()).thenReturn(1);
        when(tp3.getPriority()).thenReturn(1);
        when(tp1.getId()).thenReturn(1);
        when(tp2.getId()).thenReturn(2);
        when(tp3.getId()).thenReturn(3);
        fifoTaskManager = new FifoTaskManagerImpl(3, mockClock);
        // Add the tasks in non id order.
        fifoTaskManager.add(tp1);
        fifoTaskManager.add(tp3);
        fifoTaskManager.add(tp2);
        List<TaskProcess> taskProcesses = fifoTaskManager.list(SortBy.REGISTRATION_TIME);
        assertEquals(1, taskProcesses.get(0).getId());
        assertEquals(3, taskProcesses.get(1).getId());
        assertEquals(2, taskProcesses.get(2).getId());
        assertEquals(taskProcesses.size(), 3);
    }

    @Test
    void killProcessRemovesItFromRegistrationAndKillsIt() {
        when(mockClock.instant())
                .thenReturn(Instant.EPOCH)
                .thenReturn(Instant.EPOCH.plusNanos(1))
                .thenReturn(Instant.EPOCH.plusNanos(2));
        when(tp1.getPriority()).thenReturn(1);
        when(tp2.getPriority()).thenReturn(1);
        when(tp3.getPriority()).thenReturn(1);
        when(tp1.getId()).thenReturn(1);
        when(tp2.getId()).thenReturn(2);
        when(tp3.getId()).thenReturn(3);
        fifoTaskManager = new FifoTaskManagerImpl(3, mockClock);
        // Add the tasks in non id order.
        fifoTaskManager.add(tp1);
        fifoTaskManager.add(tp3);
        fifoTaskManager.add(tp2);
        fifoTaskManager.kill(2);
        verify(tp2, times(1)).kill();
        assertEquals(2, fifoTaskManager.list(SortBy.ID).size());
    }

    @Test
    void killGroupRemovesAllProcessesInGroupFromRegistrationAndKillsIt() {
        when(mockClock.instant())
                .thenReturn(Instant.EPOCH)
                .thenReturn(Instant.EPOCH.plusNanos(1))
                .thenReturn(Instant.EPOCH.plusNanos(2));
        when(tp1.getPriority()).thenReturn(1);
        when(tp2.getPriority()).thenReturn(1);
        when(tp3.getPriority()).thenReturn(1);
        when(tp1.getId()).thenReturn(1);
        when(tp2.getId()).thenReturn(2);
        when(tp3.getId()).thenReturn(3);
        fifoTaskManager = new FifoTaskManagerImpl(3, mockClock);
        // Add the tasks in non id order.
        fifoTaskManager.add(tp1);
        fifoTaskManager.add(tp3);
        fifoTaskManager.add(tp2);
        fifoTaskManager.killGroup(2);

        // Don't remove anything if no process matched priority.
        assertEquals(3, fifoTaskManager.list(SortBy.ID).size());

        // Remove all processes that match the priority.
        fifoTaskManager.killGroup(1);
        verify(tp1, times(1)).kill();
        verify(tp2, times(1)).kill();
        verify(tp3, times(1)).kill();
        assertEquals(0, fifoTaskManager.list(SortBy.ID).size());
    }

    @Test
    void killAllRemovesAllAndKillsThem() {
        when(mockClock.instant())
                .thenReturn(Instant.EPOCH)
                .thenReturn(Instant.EPOCH.plusNanos(1))
                .thenReturn(Instant.EPOCH.plusNanos(2));
        when(tp1.getPriority()).thenReturn(1);
        when(tp2.getPriority()).thenReturn(1);
        when(tp3.getPriority()).thenReturn(1);
        when(tp1.getId()).thenReturn(1);
        when(tp2.getId()).thenReturn(2);
        when(tp3.getId()).thenReturn(3);
        fifoTaskManager = new FifoTaskManagerImpl(3, mockClock);
        // Add the tasks in non id order.
        fifoTaskManager.add(tp1);
        fifoTaskManager.add(tp3);
        fifoTaskManager.add(tp2);

        fifoTaskManager.killAll();
        verify(tp1, times(1)).kill();
        verify(tp2, times(1)).kill();
        verify(tp3, times(1)).kill();
        assertEquals(0, fifoTaskManager.list(SortBy.ID).size());
    }
}