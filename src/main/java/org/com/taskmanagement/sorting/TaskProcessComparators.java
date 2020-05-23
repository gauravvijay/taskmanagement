package org.com.taskmanagement.sorting;

import org.com.taskmanagement.ProcessContainer;

import java.util.Comparator;
import java.util.Optional;

/**
 * Utility pure functions to help with sorting.
 */
public class TaskProcessComparators {
    static final Comparator<ProcessContainer> ID_COMPARATOR =
            Comparator.comparing(p -> p.getProcess().getId());
    public static final Comparator<ProcessContainer> PRIORITY_COMPARATOR =
            Comparator.comparing(p -> p.getProcess().getPriority());
    static final Comparator<ProcessContainer> REGISTRATION_TIME_COMPARATOR =
                    Comparator.comparing(ProcessContainer::getTimeOfRegistration);

    /**
     * Pure function for getting a comparator for a given sort by option.
     * @param sortBy given sorting criteria.
     * @return Optional of comparator if a supported sortBy option is passed, empty otherwise.
     */
    public static Optional<Comparator<ProcessContainer>> getComparator(SortBy sortBy) {
        switch (sortBy) {
            case ID:
                return Optional.of(ID_COMPARATOR);
            case PRIORITY:
                return Optional.of(PRIORITY_COMPARATOR);
            case REGISTRATION_TIME:
                return Optional.of(REGISTRATION_TIME_COMPARATOR);
        }
        // Unsupported sortBy
        return Optional.empty();
    }
}
