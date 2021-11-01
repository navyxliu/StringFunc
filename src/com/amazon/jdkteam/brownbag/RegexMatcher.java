package com.amazon.jdkteam.brownbag;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Warmup(iterations = 5, time = 200, timeUnit = TimeUnit.MILLISECONDS)
@Measurement(iterations = 20, time = 200, timeUnit = TimeUnit.MILLISECONDS)
@State(Scope.Benchmark)
public class RegexMatcher {
    Pattern invalidMetricPattern;
    List<String> inputList;

    @Setup(Level.Trial)
    public void doSetup() {
        String INVALID_METRIC_CHARACTERS = "[^A-Za-z0-9\\.:@_\\-\\/]";
        invalidMetricPattern = Pattern.compile(INVALID_METRIC_CHARACTERS);

        inputList = new ArrayList<String>();
        inputList.add("George of the @jungle.-!");
        inputList.add("George of t@#@$%&@$%^*\"he @jungle.");
        inputList.add("George of the @jungle");
        inputList.add("George$%^&#%$& of the @jungle.-!");
    }

    @Benchmark
    public void regexMatching(Blackhole bh) {
        for(String input : inputList) {
            bh.consume(invalidMetricPattern.matcher(input).replaceAll(""));
        }
    }
}
