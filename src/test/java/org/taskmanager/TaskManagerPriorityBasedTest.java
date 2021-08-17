package org.taskmanager;

import org.junit.jupiter.api.Test;
import org.taskmanager.process.IProcess;
import org.taskmanager.process.Priority;
import org.taskmanager.process.ProcessImpl;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class TaskManagerPriorityBasedTest {

    @Test
    void addHigherPriority() {
        final TaskManagerPriorityBased taskManager = new TaskManagerPriorityBased();
        final List<IProcess> processes = createProcesses();
        processes.forEach(taskManager::add);

        // add MEDIUM -> LOW should be removed
        final ProcessImpl process = new ProcessImpl(Priority.MEDIUM);
        taskManager.add(process);

        List<IProcess> list = taskManager.listRunning();
        assertEquals(3, list.size());
        assertEquals(list.get(0).getPriority(), Priority.MEDIUM);
        assertEquals(list.get(1).getPriority(), Priority.HIGH);
        assertEquals(list.get(2).getPriority(), Priority.MEDIUM);

        // add HIGH -> older MEDIUM should be removed
        taskManager.add(new ProcessImpl(Priority.HIGH));
        list = taskManager.listRunning();
        assertEquals(3, list.size());
        assertEquals(list.get(0).getPriority(), Priority.HIGH);
        assertEquals(list.get(1).getPriority(), Priority.MEDIUM);
        assertEquals(process.getPid(), list.get(1).getPid());
        assertEquals(list.get(2).getPriority(), Priority.HIGH);

        // add HIGH -> MEDIUM should be removed
        taskManager.add(new ProcessImpl(Priority.HIGH));
        list = taskManager.listRunning();
        assertEquals(3, list.size());
        assertEquals(list.get(0).getPriority(), Priority.HIGH);
        assertEquals(list.get(1).getPriority(), Priority.HIGH);
        assertEquals(list.get(2).getPriority(), Priority.HIGH);
    }

    @Test
    void addSamePriorityToSkip() {
        final TaskManagerPriorityBased taskManager = new TaskManagerPriorityBased();
        createProcesses().forEach(taskManager::add);

        final ProcessImpl processToSkip = new ProcessImpl(Priority.LOW);
        taskManager.add(processToSkip);

        final List<IProcess> list = taskManager.listRunning();
        assertEquals(3, list.size());
        assertEquals(list.get(0).getPriority(), Priority.HIGH);
        assertEquals(list.get(1).getPriority(), Priority.MEDIUM);
        assertEquals(list.get(2).getPriority(), Priority.LOW);
        assertNotEquals(list.get(2).getPid(), processToSkip.getPid());
    }

    private List<IProcess> createProcesses() {
        return List.of(new ProcessImpl(Priority.LOW), new ProcessImpl(Priority.MEDIUM), new ProcessImpl(Priority.HIGH));
    }
}