package org.taskmanager.process;

public interface IProcess {
    long getCreated();
    Priority getPriority();
    long getPid();
    boolean isRunning();
    void setRunning(boolean b);
}
