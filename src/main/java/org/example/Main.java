package org.example;

import java.time.Duration;
import java.time.Instant;

public class Main {
    public static void main(String[] args) {
        Instant start = Instant.now();
        Graph graph = new Graph();
        Worker worker = new Worker(graph);
        Node way = worker.findWay("Albert Einstein", "GitHub");
        System.out.println(way);
        Duration between = Duration.between(start, Instant.now());
        System.out.println("Execution time is: " + between.toSeconds() + " sec");
    }
}