package com.amazon.jdkteam.brownbag;

import java.util.concurrent.TimeUnit;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Warmup;

@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Warmup(iterations = 5, time = 100, timeUnit = TimeUnit.MILLISECONDS)
@Measurement(iterations = 20, time = 100, timeUnit = TimeUnit.MILLISECONDS)
public class SubstrToStringBuilder {
    private static final String sample = "Tom|Jerry|Fred|George|Elaine|Ross|Rachel|Chandler|Monica|Leonard|Sheldon";

    @Benchmark
    public String substring2SB() {
        StringBuilder sb = new StringBuilder();
        int pos = 0;

        for (int sep; (sep = sample.indexOf('|', pos)) != -1; pos = sep + 1) {
            String sub = sample.substring(pos, sep);
            pos = sep + 1;
            sb.append(sub);
        }
        sb.append(sample.substring(pos));
        return sb.toString();
    }

    @Benchmark
    public String substring2SB_noalloc() {
        StringBuilder sb = new StringBuilder();
        int pos = 0;

        for (int sep; (sep = sample.indexOf('|', pos)) != -1; pos = sep + 1) {
            sb.append(sample, pos, sep);
            pos = sep + 1;
        }

        sb.append(sample.substring(pos));
        return sb.toString();
    }

    public static void main(String[] args) {
        SubstrToStringBuilder bench = new SubstrToStringBuilder();
        System.out.println(bench.substring2SB());
        System.out.println(bench.substring2SB_noalloc());
    }
}

