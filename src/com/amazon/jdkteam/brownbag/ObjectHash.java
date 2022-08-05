package com.amazon.jdkteam.brownbag;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

@BenchmarkMode({Mode.Throughput})
@OutputTimeUnit(TimeUnit.MICROSECONDS)
public class ObjectHash {
    private static Object object1 = new Object();

    @Benchmark
    public int singleObjectsHash() {
        return Objects.hash(object1);
    }

    @Benchmark
    public int singleObjectsHash2() {
        return 31 + (object1 == null ? 0 : object1.hashCode());
    }
}
