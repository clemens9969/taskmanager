package org.taskmanager.process;

public final class ProcessImpl extends AbstractProcess {

    public ProcessImpl(Priority priority) {
        super(priority, System.nanoTime(), NEXT_ID.getAndIncrement());
        this.isRunning = true;
    }

    @Override
    void kill(long pid) {
        this.isRunning = false;
    }

    @Override
    public boolean isRunning() {
        return isRunning;
    }

    @Override
    public void setRunning(boolean running) {
        isRunning = running;
    }

    @Override
    public long getPid() {
        return this.pid;
    }

    @Override
    public long getCreated() {
        return this.created;
    }

    @Override
    public Priority getPriority() {
        return this.priority;
    }

    @Override
    public String toString() {
        return "org.taskmanager.process.ProcessImpl{" +
                "pid=" + pid +
                ", priority=" + priority +
                ", created=" + created +
                ", isRunning=" + isRunning +
                '}';
    }
}
