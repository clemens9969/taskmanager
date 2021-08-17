package org.taskmanager.process;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AbstractProcessTest {

    @Test
    void kill() {
        AbstractProcess process = new ProcessImpl(Priority.LOW);

        process.kill(process.getPid());

        assertFalse(process.isRunning());
    }

    @Test
    void isRunning() {
        assertTrue(new ProcessImpl(Priority.LOW).isRunning());
    }
}