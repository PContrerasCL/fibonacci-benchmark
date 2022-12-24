package org.sample;

import org.openjdk.jmh.annotations.*;

import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

/**
 * Fibonacci Naive Recursive vs Memoization Recursive vs Bottom-Up vs Stream API
 *
 * This class is to compare the performance result of simple common recursive
 * use case "The Fibonacci". One is use Naive Recursive algorithm, second is use
 * Dynamic programming Memoization algorithm, third is bottom-up, and last is
 * with Java 8 Stream API.
 *
 * The function is to calculate Fibonacci position N, then return the value.
 */
@Fork(warmups = 2, value = 2)
@Warmup(iterations = 3, time = 2)
@BenchmarkMode(value = Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Measurement(iterations = 3, time = 2)
public class FibonacciBenchmark {

    public int fibNaiveRecursive(int x) {
        return (x == 1 || x == 2)?1:fibNaiveRecursive(x - 1) + fibNaiveRecursive(x - 2);
    }

    public int fibTailRecursive(int x) {
        return fibTailRec(x, 0,1);
    }

    private int fibTailRec(int n, int a, int b) {
        if (n == 0) return a;
        if (n == 1) return b;
        return fibTailRec(n - 1, b, a + b);
    }

    public int fibMemoization(int x, int[] mem) {
        if (mem[x] != 0) return mem[x];
        if (x == 1 || x == 2)  return 1;
        int n = fibMemoization(x - 1, mem) + fibMemoization(x - 2,mem);
        mem[x] = n;
        return n;
    }

    public int fibBottomUp(int x) {
        if (x == 1 || x == 2) return 1;
        int[] memory = new int[x + 1];
        memory[1] = 1;
        memory[2] = 1;
        for (int i = 3; i <= x; i++) memory[i] = memory[i - 1] + memory[i - 2];
        return memory[x];
    }

    public int fibStream(int n) {
          return Objects.requireNonNull(Stream.iterate(new Integer[]{0, 1}, s -> new Integer[]{s[1], s[0] + s[1]})
                  .limit(n)
                  .reduce((x, y) -> y).orElse(null))[1];
    }

    @Benchmark
    public void naiveRecursive() {
        fibNaiveRecursive(45);
    }

    @Benchmark
    public void tailRecursive(){
        fibTailRecursive(45);
    }

    @Benchmark
    public void memoization() {
        fibMemoization(45,new int[45+1]);
    }

    @Benchmark
    public void bottomUp() {
        fibBottomUp(45);
    }

    @Benchmark
    public void stream() {
        fibStream(45);
    }
}