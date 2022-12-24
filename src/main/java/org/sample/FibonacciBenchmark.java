/*
 * The MIT License
 *
 * Copyright 2018 Maikel Chandika <mkdika@gmail.com>.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package org.sample;

import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Warmup;

/**
 * Fibonacci Naive Recursive vs Memoization Recursive vs Bottom-Up vs Stream API
 *
 * This class is to compare the performance result of simple common recursive
 * use case "The Fibonacci". One is use Naive Recursive algorithm, second is use
 * Dynamic programming Memoization algorithm, third is bottom-up, and last is
 * with Java 8 Stream API.
 *
 * The function is to calculate Fibonacci position N, then return the value.
 *
 * @author Maikel Chandika (mkdika@gmail.com)
 */
@Fork(warmups = 2, value = 2)
@Warmup(iterations = 3, time = 2, timeUnit = TimeUnit.SECONDS)
@BenchmarkMode(value = Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Measurement(iterations = 3, time = 2, timeUnit = TimeUnit.SECONDS)
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