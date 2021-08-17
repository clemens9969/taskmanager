package org.taskmanager;

import org.taskmanager.process.AddableProcess;
import org.taskmanager.process.IProcess;

import java.util.ArrayList;

public class TaskManagerDefault extends AbstractTaskmanager implements AddableProcess {

    TaskManagerDefault() {
        this.processes = new ArrayList<>();
    }

    @Override
    public void add(IProcess process) {
        if (this.processes.size() < Capacity.MAXIMUM_CAPACITY) {
            this.processes.add(process);
        } else {
            throw new RuntimeException("No more processes allowed due to maximum capacity of " + Capacity.MAXIMUM_CAPACITY);
        }
    }

}
