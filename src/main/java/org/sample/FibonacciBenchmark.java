package org.sample;

import org.openjdk.jmh.annotations.*;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

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
    private static final HashMap<Integer, Integer> memo = new HashMap<>();
    private static final HashMap<Integer, Long> memoIterative = new HashMap<>();

    public static int fibRecursive(int n) {
        if (n <= 1) return n;
        return fibRecursive(n - 1) + fibRecursive(n - 2);
    }

    public static int fibRecursiveMemoization(int n) {
        if (n == 0 || n == 1) {
            return n;
        }
        if (memo.containsKey(n)) {
            return memo.get(n);
        }
        int result = fibRecursiveMemoization(n - 1) + fibRecursiveMemoization(n - 2);
        memo.put(n, result);
        return result;
    }

    public static int fibIterative(int n) {
        if (n == 0) {
            return 0;
        }
        if (n == 1) {
            return 1;
        }
        int prev = 0;
        int curr = 1;
        for (int i = 2; i <= n; i++) {
            int next = prev + curr;
            prev = curr;
            curr = next;
        }
        return curr;
    }

    public static long fibIterativeMemoization(int n) {
        // Check if the result is already stored in the memo. If it is, return it.
        if (memoIterative.containsKey(n)) {
            return memoIterative.get(n);
        }

        // If n is 0 or 1, the Fibonacci number is n.
        if (n == 0 || n == 1) {
            return n;
        }

        // Calculate the Fibonacci number using the iterative approach.
        long result = 0;
        long a = 0;
        long b = 1;
        for (int i = 2; i <= n; i++) {
            result = a + b;
            a = b;
            b = result;
        }

        // Store the result in the memo before returning it.
        memoIterative.put(n, result);
        return result;
    }

    public static long fibBinetFormula(int n) {
        double phi = (1 + Math.sqrt(5)) / 2;
        return Math.round(Math.pow(phi, n) / Math.sqrt(5));
    }

    @Benchmark
    public void recursive() {
        fibRecursive(10);
    }

    @Benchmark
    public void recursiveMemoization(){
        fibRecursiveMemoization(10);
    }

    @Benchmark
    public void iterative() {
        fibIterative(10);
    }

    @Benchmark
    public void iterativeMemoization() {
        fibIterativeMemoization(10);
    }

    @Benchmark
    public void binetFormula() {
        fibBinetFormula(10);
    }
}