package org.taskmanager;

import org.junit.jupiter.api.Test;
import org.taskmanager.process.IProcess;
import org.taskmanager.process.Priority;
import org.taskmanager.process.ProcessImpl;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TaskManagerDefaultTest {

    @Test
    void add() {
        TaskManagerDefault taskManager = new TaskManagerDefault();
        taskManager.add(new ProcessImpl(Priority.LOW));

        assertEquals(1, taskManager.listRunning().size());
    }

    @Test
    void addException() {
        TaskManagerDefault taskManager = new TaskManagerDefault();
        createProcesses().forEach(taskManager::add);

        final RuntimeException exception = assertThrows(RuntimeException.class, () -> taskManager.add(new ProcessImpl(Priority.LOW)));

        assertEquals("No more processes allowed due to maximum capacity of " + Capacity.MAXIMUM_CAPACITY, exception.getMessage());
    }

    @Test
    void list() {
        TaskManagerDefault taskManager = new TaskManagerDefault();
        assertEquals(0, taskManager.listRunning().size());

        taskManager.add(new ProcessImpl(Priority.LOW));

        assertEquals(1, taskManager.listRunning().size());
    }

    @Test
    void listByTime() {
        TaskManagerDefault taskManager = new TaskManagerDefault();
        createProcesses().forEach(taskManager::add);

        taskManager.sortByTime();

        final List<IProcess> list = taskManager.listRunning();
        assertTrue(list.get(0).getCreated() < list.get(1).getCreated());
        assertTrue(list.get(1).getCreated() < list.get(2).getCreated());
    }

    @Test
    void listByPriority() {
        TaskManagerDefault taskManager = new TaskManagerDefault();
        createProcesses().forEach(taskManager::add);

        taskManager.sortByPriority();

        final List<IProcess> list = taskManager.listRunning();
        assertEquals(list.get(0).getPriority(), Priority.HIGH);
        assertEquals(list.get(1).getPriority(), Priority.MEDIUM);
        assertEquals(list.get(2).getPriority(), Priority.LOW);
    }

    @Test
    void listByPid() {
        TaskManagerDefault taskManager = new TaskManagerDefault();
        final List<IProcess> processes = createProcesses();
        processes.forEach(taskManager::add);

        taskManager.sortByPid();

        final List<IProcess> list = taskManager.listRunning();
        assertEquals(list.get(0).getPid(), processes.get(0).getPid());
        assertEquals(list.get(1).getPid(), processes.get(1).getPid());
        assertEquals(list.get(2).getPid(), processes.get(2).getPid());
    }

    @Test
    void kill() {
        TaskManagerDefault taskManager = new TaskManagerDefault();
        final List<IProcess> processes = createProcesses();
        processes.forEach(taskManager::add);

        taskManager.kill(processes.get(0));

        assertEquals(2, taskManager.listRunning().size());
        assertFalse(taskManager.listRunning().contains(processes.get(0)));
    }

    @Test
    void killByPriority() {
        TaskManagerDefault taskManager = new TaskManagerDefault();
        final List<IProcess> processes = createProcesses();
        processes.forEach(taskManager::add);

        taskManager.kill(Priority.HIGH);

        assertEquals(2, taskManager.listRunning().size());
        taskManager.listRunning().forEach(process -> assertNotEquals(process.getPriority(), Priority.HIGH));
    }

    @Test
    void killAll() {
        TaskManagerDefault taskManager = new TaskManagerDefault();
        createProcesses().forEach(taskManager::add);

        taskManager.killAll();

        assertEquals(0, taskManager.listRunning().size());
    }

    private List<IProcess> createProcesses() {
        return List.of(new ProcessImpl(Priority.LOW), new ProcessImpl(Priority.MEDIUM), new ProcessImpl(Priority.HIGH));
    }

}