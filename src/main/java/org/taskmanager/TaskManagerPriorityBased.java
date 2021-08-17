package org.taskmanager;

import org.taskmanager.process.AddableProcess;
import org.taskmanager.process.IProcess;
import org.taskmanager.process.Priority;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class TaskManagerPriorityBased extends AbstractTaskmanager implements AddableProcess {

    TaskManagerPriorityBased() {
        this.processes = new LinkedList<>();
    }

    @Override
    public void add(IProcess processToAdd) {
        if (this.processes.size() == Capacity.MAXIMUM_CAPACITY) {
            // LOW is skipped
            if (!processToAdd.getPriority().equals(Priority.LOW)) {
                // filter lower priorities and sort by created time
                final List<IProcess> collect = this.processes.stream()
                        .filter(process -> process.getPriority().ordinal() < processToAdd.getPriority().ordinal())
                        .sorted(Comparator.comparingLong(IProcess::getCreated))
                        .collect(Collectors.toList());
                // remove the older with minor priority.
                this.processes.remove(collect.get(0));
                ((LinkedList) this.processes).addFirst(processToAdd);
            }
        } else {
            ((LinkedList) this.processes).addFirst(processToAdd);
        }
    }
}
