package com.github.tkutcher.jgrade.gradedtest;

//import org.junit.runner.RunWith;
//import org.junit.runners.Suite;
import org.junit.jupiter.api.extension;
import org.junit.platform.suite.api;

//@RunWith(Suite.class)
//@Suite.SuiteClasses({
@Suite
@SelectClasses({
        GradedTestListenerTest.class,
        GradedTestResultTest.class,
})
public class AllGraderTests { }
