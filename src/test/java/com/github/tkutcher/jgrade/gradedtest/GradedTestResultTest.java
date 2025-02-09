package com.github.tkutcher.jgrade.gradedtest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GradedTestResultTest {

    private GradedTestResult unit;

    @BeforeEach
    public void initUnit() {
        this.unit = new GradedTestResult(
                GradedTestResult.DEFAULT_NAME,
                GradedTestResult.DEFAULT_NUMBER,
                GradedTestResult.DEFAULT_POINTS,
                GradedTestResult.DEFAULT_VISIBILITY
        );
    }

    @Test
    public void hasCorrectParams() {
        assertEquals(GradedTestResult.DEFAULT_NAME, unit.getName());
        assertEquals(GradedTestResult.DEFAULT_NUMBER, unit.getNumber());
        assertEquals(unit.getPoints(), 0.0, GradedTestResult.DEFAULT_POINTS);
        assertEquals(GradedTestResult.DEFAULT_VISIBILITY, unit.getVisibility());
    }

    @Test
    public void startsWithNoOutput() {
        assertEquals("", unit.getOutput());
    }

    @Test
    public void startsWithNoScore() {
        assertEquals(unit.getScore(), 0.0, 0);
    }

    @Test
    public void canAddOutput() {
        String s1 = "Ex@mpl3";
        String s2 = "OuTPUt";
        assertEquals("", unit.getOutput());
        unit.addOutput(s1);
        assertEquals(s1, unit.getOutput());
        unit.addOutput(s2);
        assertEquals(s1 + s2, unit.getOutput());
    }

    @Test
    public void canAddScore() {
        double score1 = 0.5;
        double score2 = 0.75;
        assertEquals(unit.getScore(), 0.0, 0);
        unit.setScore(score1);
        assertEquals(unit.getScore(), 0.0, score1);
        unit.setScore(score2);
        assertEquals(unit.getScore(), 0.0, score2);
    }

    @Test
    public void cannotAddScoreGreaterThanPoints() {
        assertThrows(RuntimeException.class, () -> {
          unit.setScore(15.0);
        });
    }

    @Test
    public void visibilityMustBeValid() {
        assertThrows(IllegalArgumentException.class, () -> {
        new GradedTestResult(GradedTestResult.DEFAULT_NAME, GradedTestResult.DEFAULT_NUMBER, GradedTestResult.DEFAULT_POINTS, "INVALID");
        });
    }
}
