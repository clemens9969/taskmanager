package org.taskmanager;

import org.taskmanager.process.AddableProcess;
import org.taskmanager.process.IProcess;

import java.util.LinkedList;

public class TaskManagerFiFo extends AbstractTaskmanager implements AddableProcess {

    TaskManagerFiFo() {
        this.processes = new LinkedList<>();
    }

    @Override
    public void add(IProcess process) {
        if (this.processes.size() == Capacity.MAXIMUM_CAPACITY) {
            this.processes.get(processes.size() - 1).setRunning(false);
            ((LinkedList) this.processes).removeLast();
            ((LinkedList) this.processes).addFirst(process);
        } else {
            ((LinkedList) this.processes).addFirst(process);
        }
    }

}
