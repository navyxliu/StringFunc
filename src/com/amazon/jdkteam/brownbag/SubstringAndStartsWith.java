package com.amazon.jdkteam.brownbag;

import java.util.concurrent.TimeUnit;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;

@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Warmup(iterations = 5, time = 200, timeUnit = TimeUnit.MILLISECONDS)
@Measurement(iterations = 20, time = 200, timeUnit = TimeUnit.MILLISECONDS)
@State(Scope.Benchmark)
public class SubstringAndStartsWith {
    @Param({"1", "8", "32", "128", "256", "512"})
    public int substrLength;
    public String sample;
    public String prefix = "a";

    @Setup(Level.Trial)
    public void doSetup() {
       StringBuilder sb = new StringBuilder();
       for (int i=0; i<512 * 2; i = i + 6) {
           sb.append("abcdef");
       }
       sample = sb.toString();
    }

    @Benchmark
    public boolean substr2StartsWith() {
        return sample.substring(substrLength, substrLength * 2).startsWith(prefix);
    }

    @Benchmark
    public boolean substr2StartsWith_noalloc() {
        // compare prefix length with the length of substring
        if (prefix.length() > substrLength) return false;
        return sample.startsWith(prefix, substrLength); // substrLength here is actually the beginIdex of substring
    }
}
