package com.amazon.jdkteam.brownbag;

import java.nio.ByteBuffer;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.zip.CRC32;
import java.util.zip.CRC32C;

import org.openjdk.jmh.annotations.*;

@State(Scope.Benchmark)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@Warmup(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 15, time = 1, timeUnit = TimeUnit.SECONDS)
public class CRCBenchmark {
    @Param({"1000", "100000", "1000000"})
    public int dataSize;

    byte[] data;
    ByteBuffer direct;

    @Setup
    public void setup() throws Exception {
        data = new byte[dataSize];
        ThreadLocalRandom.current().nextBytes(data);

        direct = ByteBuffer.allocateDirect(dataSize);
        direct.put(data);
        direct.flip();
    }

    @Benchmark
    public long jdkStdDirect() throws Exception {
        CRC32 crc = new CRC32();
        crc.update(direct.duplicate());
        return crc.getValue();
    }

    @Benchmark
    public long jdkCastagnoliDirect() throws Exception {
        CRC32C crc = new CRC32C();
        crc.update(direct.duplicate());
        return crc.getValue();
    }
}