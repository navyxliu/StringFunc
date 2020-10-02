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
@Warmup(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 20, time = 1, timeUnit = TimeUnit.SECONDS)
@State(Scope.Benchmark)
public class SplitAndPick {
    //
    // the fastpath uses the following algorithm. churn out a lot of substrings
    // while ((next = indexOf(ch, off)) != -1) {
    //     if (!limited || list.size() < limit - 1) {
    //         list.add(substring(off, next));
    //         off = next + 1;
    //     } else {    // last one
    //         //assert (list.size() == limit - 1);
    //         int last = length();
    //         list.add(substring(off, last));
    //         off = last;
    //         break;
    //     }
    // }
    //
    // separator cannot be one of ".$|()[{^?*+\\"
    private static final String SEP = "/";
    private static final String base = "Jerry" + SEP + "George";
    @Param({"8", "32", "128", "256", "512", "1024", "4096"})
    public int length;

    private String input;
    @Setup(Level.Trial)
    public void doSetup() {
        StringBuilder sb = new StringBuilder(base);

        while(sb.length() < length) {
            sb.append(base);
        }
        input = sb.toString();
    }

    @Benchmark
    public String splitAndPick() {
        return input.split(SEP)[1];
    }

    @Benchmark
    public String splitAndPick_wisely() {
        return input.split(SEP, 2)[1];
    }

    public static void main(String[] args) {
       System.out.print(base.split(SEP, 2)[1]);
    }
}
