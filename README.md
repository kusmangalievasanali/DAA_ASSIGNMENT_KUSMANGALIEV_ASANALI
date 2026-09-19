# DAA Assignment 1 — Divide and Conquer & Asymptotic Notations
# Kusmangaliev Asanali, SE-2509

## Overview

Implementation of MergeSort, QuickSort and QuickSelect in Java with performance measurement (time, comparisons, recursion depth).

## Requirements

- Java 17
- Maven 3.x

## Project Structure

src/main/java/algorithms/ - InsertionSort.java, MergeSort.java, QuickSort.java, QuickSelect.java
src/main/java/metrics/ - Metrics.java
src/main/java/benchmark/ - Benchmark.java
src/test/java/algorithms/ - unit tests for all algorithms

## Build

mvn clean compile

## Run Tests

mvn test

Tests cover: correctness vs Arrays.sort (100+ random arrays), edge cases (empty, single element, all equal, already sorted), tested with negative numbers and 0, QuickSort recursion depth check, QuickSelect correctness vs sorted[k].

## Run Benchmark

mvn exec:java

Runs MergeSort, QuickSort and QuickSelect on n = 1,000 / 10,000 / 100,000 / 1,000,000 with random, sorted and duplicates inputs. Each case runs 5 times; median time is saved to results.csv in the project root.
