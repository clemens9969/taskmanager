package org.taskmanager.process;

import java.util.concurrent.atomic.AtomicLong;

abstract class AbstractProcess implements IProcess{
    protected static final AtomicLong NEXT_ID = new AtomicLong(0);
    protected final Priority priority;
    protected final long created;
    protected long pid;
    protected boolean isRunning;

    public AbstractProcess(Priority priority, long created, long pid) {
        this.priority = priority;
        this.created = created;
        this.pid = pid;
    }

    abstract void kill(long pid);

    public abstract boolean isRunning();

    public abstract long getPid();

    public abstract long getCreated();
}
