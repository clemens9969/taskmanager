package org.taskmanager;

import org.taskmanager.process.IProcess;
import org.taskmanager.process.Priority;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

abstract class AbstractTaskmanager {

    protected List<IProcess> processes;

    List<IProcess> listRunning() {
        return this.processes.stream().filter(IProcess::isRunning).collect(Collectors.toList());
    }

    void sortByTime() {
        this.processes.sort(Comparator.comparingLong(IProcess::getCreated));
    }

    void sortByPriority() {
        this.processes.sort(Comparator.comparing(IProcess::getPriority).reversed());
    }

    void sortByPid() {
        this.processes.sort(Comparator.comparing(IProcess::getPid));
    }

    public void kill(IProcess process) {
        this.processes.get(this.processes.indexOf(process)).setRunning(false);
    }

    void kill(Priority priority) {
        this.processes.forEach(process -> {
            if (process.getPriority().equals(priority)) {
                process.setRunning(false);
            }
        });
    }

    void killAll() {
        this.processes.forEach(process -> process.setRunning(false));
    }
}
