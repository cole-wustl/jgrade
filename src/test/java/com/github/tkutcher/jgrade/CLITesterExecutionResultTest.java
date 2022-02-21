package com.github.tkutcher.jgrade;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class CLITesterExecutionResultTest {

    @Test
    public void getStdout() {
        CLIResult unit = new CLITester.ExecutionResult("test1", "", 1);
        assertEquals(unit.getOutput(CLIResult.STREAM.STDOUT), "test1");
    }

    @Test
    public void getByLineOnEmptyString() {
        CLIResult unit = new CLITester.ExecutionResult("test1", "", 1);
        assertEquals(unit.getOutputByLine(CLIResult.STREAM.STDERR).size(), 0);
    }

    @Test
    public void getExitValue() {
        CLIResult unit = new CLITester.ExecutionResult("test1", "", 1);
        assertEquals(1, unit.exitValue());
    }

    @Test
    public void defaultGetStd() {
        CLIResult unit = new CLITester.ExecutionResult("stdout", "stderr", 0);
        assertEquals("stdout", unit.getOutput());
    }
}
