package com.github.tkutcher.jgrade;

import com.github.tkutcher.jgrade.gradedtest.GradedTestListener;
import com.github.tkutcher.jgrade.gradedtest.GradedTestResult;
//import org.junit.runner.JUnitCore;
import org.junit.jupiter.api.extension;
import org.junit.platform.suite.api;


import java.util.ArrayList;
import java.util.List;


/**
 * A class to encompass all of the options for grading tests. Bundles together
 * {@link GradedTestResult}s, a total score, a total max score, any output
 * relevant to the entire submission, and a timer feature for timing execution
 * time. Is Observable to {@link OutputFormatter}s.
 *
 * <p>
 *     Has undocumented accessors for score, max score, execution time,
 *     graded test results, and output.
 * </p>
 */
public class Grader {
    private static final int NOT_SET = -1;

    private class DefaultGraderStrategy implements GraderStrategy {
        public void grade(List<GradedTestResult> l) {
            // Nothing to do.
        }
    }

    private GraderStrategy graderStrategy;
    private List<GradedTestResult> gradedTestResults;
    private long startTime;
    private long executionTime;
    private Double score;
    private Double maxScore;
    private StringBuilder output;

    /** Create a new Grader. */
    public Grader() {
        this.gradedTestResults = new ArrayList<>();
        this.executionTime = NOT_SET;
        this.output = new StringBuilder();
        this.graderStrategy = new DefaultGraderStrategy();
    }

    // <editor-fold desc="accessors">

    /**
     * Has a score set.
     * @return True if a score was set.
     */
    public boolean hasScore() {
        return this.score != null;
    }

    /**
     * Has a max score set.
     * @return True if max score was set.
     */
    public boolean hasMaxScore() {
        return this.maxScore != null;
    }

    /**
     * Has an execution time.
     * @return True if the Graders has an execution time.
     */
    public boolean hasExecutionTime() {
        return this.executionTime != NOT_SET;
    }

    /**
     * Has any {@link GradedTestResult}s.
     * @return True if the list contains at least one result.
     */
    public boolean hasGradedTestResults() {
        return this.gradedTestResults.size() > 0;
    }

    /**
     * Has output for the Grader.
     * @return True if there is any output to include.
     */
    public boolean hasOutput() {
        return this.output.length() > 0;
    }

    /**
     * Set the score (student's score) for the Grader.
     * @param score The score to set.
     */
    public void setScore(double score) {
        this.score = score;
    }

    /**
     * Set the max potential score for the Grader.
     * @param maxScore The max potential score to set.
     */
    public void setMaxScore(double maxScore) {
        this.maxScore = maxScore;
    }

    /**
     * Set the execution time for the Grader.
     * @param executionTime The execution time.
     */
    public void setExecutionTime(long executionTime) {
        this.executionTime = executionTime;
    }

    /**
     * Add a {@link GradedTestResult} to the Grader.
     * @param result The {@link GradedTestResult} to add.
     */
    public void addGradedTestResult(GradedTestResult result) {
        gradedTestResults.add(result);
    }

    /**
     * Add output to the Grader overall.
     * @param output The string to append to the output.
     */
    public void addOutput(String output) {
        this.output.append(output);
    }

    /**
     * Get the (student) score for the Grader.
     * @return The student score.
     */
    public double getScore() {
        return this.score;
    }

    /**
     * Get the max potential score.
     * @return The max potential score.
     */
    public double getMaxScore() {
        return this.maxScore;
    }

    /**
     * Get the execution time.
     * @return The execution time.
     */
    public long getExecutionTime() {
        return this.executionTime;
    }

    /**
     * Get the list of {@link GradedTestResult}s.
     * @return The list of {@link GradedTestResult}s.
    */
    public List<GradedTestResult> getGradedTestResults() {
        return gradedTestResults;
    }

    /**
     * Get the output for the Grader.
     * @return All of the output that has been added to the Grader.
     */
    public String getOutput() {
        return this.output.toString();
    }

    // </editor-fold>

    /**
     * Set the strategy to use to grade. By default, the strategy is to
     * add {@link GradedTestResult}s as they are. To change that, you can
     * alter them via the strategy and it's grade method.
     * @param s The strategy to set.
     */
    public void setGraderStrategy(GraderStrategy s) {
        this.graderStrategy = s;
    }

    /** Starts (or resumes) the timer for the Grader. */
    public void startTimer() {
        this.startTime = System.currentTimeMillis();
    }

    /**
     * Stops the timer for the Grader. Can start again after stopping.
     * @throws IllegalStateException If the timer has not been started.
     */
    public void stopTimer() throws IllegalStateException {
        if (this.startTime == 0) {
            throw new IllegalStateException("cannot stop timer not started");
        }
        this.setExecutionTime(this.getExecutionTime() + System.currentTimeMillis() - this.startTime);
        this.startTime = 0;
    }

    /**
     * Runs JUnit tests and attaches a {@link GradedTestListener} to listen
     * for all {@link com.github.tkutcher.jgrade.gradedtest.GradedTest}s and add the
     * created {@link GradedTestResult}s. If class <code>MyTests</code> has
     * graded test JUnit test methods, then call this method with
     * <code>MyTests.class</code>. Similarly can use JUnit's
     * {@link org.junit.runners.Suite}. Can alter the list of results added from the
     * run by setting the {@link GraderStrategy}.
     * @param testSuite The class containing the tests.
     */
    public void runJUnitGradedTests(Class testSuite) {
        GradedTestListener listener = new GradedTestListener();
        JUnitCore runner = new JUnitCore();
        runner.addListener(listener);
        runner.run(testSuite);
        List<GradedTestResult> results = listener.getGradedTestResults();
        this.graderStrategy.grade(results);
        this.gradedTestResults.addAll(results);
    }
}
