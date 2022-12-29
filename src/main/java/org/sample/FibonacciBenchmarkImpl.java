package org.sample;

import org.openjdk.jmh.annotations.*;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;


@Fork(warmups = 2, value = 2)
@Warmup(iterations = 3, time = 2)
@BenchmarkMode(value = Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Measurement(iterations = 3, time = 2)
public class FibonacciBenchmarkImpl {
    private static final HashMap<Integer, Integer> memo = new HashMap<>();
    private static final HashMap<Integer, Long> memoIterative = new HashMap<>();

    public static int fibRecursive(int n) {
        if (n <= 1) return n;
        return fibRecursive(n - 1) + fibRecursive(n - 2);
    }

    public static int fibRecursiveMemoization(int n) {
        if (memo.containsKey(n)) {
            return memo.get(n);
        }
        if (n <= 1){
            return n;
        }
        int result = fibRecursiveMemoization(n - 1) + fibRecursiveMemoization(n - 2);
        memo.put(n, result);
        return result;
    }

    public static int fibIterative(int n) {
        // Si n es menor o igual a 1, devuelve n
        if (n <= 1) {
            return n;
        }
        int prev = 0;
        int curr = 1;
        for (int i = 2; i <= n; i++) {
            //Suma el valor previo y actual, este numero será el próximo a utilizar como actual
            int next = prev + curr;
            prev = curr;
            curr = next;
        }
        return curr;
    }

    public static long fibIterativeMemoization(int n) {
        // Verifica si el numero ya existe en el hashmap y si es así, lo devuelve
        if (memoIterative.containsKey(n)) {
            return memoIterative.get(n);
        }

        // Si n es menor o igual a 1, devuelve n
        if (n <= 1) {
            return n;
        }

        // Cálculo del número de fibonacci
        long result = 0;
        long a = 0;
        long b = 1;
        for (int i = 2; i <= n; i++) {
            result = a + b;
            a = b;
            b = result;
        }

        // Se almacena el valor antes de devolver el resultado
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