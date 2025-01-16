package org.example;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.locks.LockSupport;

public class Worker {

    private final Graph wikiGraph;
    private String searching;
    private Node founded;
    private final ExecutorService executorService;
    private Thread parkedThread;

    public Worker(Graph wikiGraph) {
        this.executorService = Executors.newFixedThreadPool(250, Thread.ofVirtual().factory());
        this.wikiGraph = wikiGraph;
    }

    public Node findWay(String from, String to) {
        this.parkedThread = Thread.currentThread();
        this.searching = to;
        CompletableFuture<List<String>> links = CompletableFuture.supplyAsync(() -> {
                    try {
                        return new RequestService().requestLinks(from);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
                , executorService
        );
        CompletableFuture.runAsync(() -> {
            try {
                walkThrough(new Node(null, from), links, 0);
            } catch (ExecutionException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        LockSupport.park(parkedThread);
        return founded;
    }

    private void walkThrough(Node parent, Future<List<String>> asyncLinks, Integer depth) throws ExecutionException, InterruptedException {
        List<String> links = asyncLinks.get();
        for (String link : links) {
            CompletableFuture<List<String>> futureLinks = CompletableFuture.supplyAsync(
                    () -> {
                        try {
                            return new RequestService().requestLinks(link);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    , executorService
            );
            Node node = new Node(parent, link);
            if (link.equalsIgnoreCase(searching)) {
                this.founded = node;
                LockSupport.unpark(parkedThread);
                break;
            }
            CompletableFuture.runAsync(() -> {
                        try {
                            walkThrough(node, futureLinks, depth + 1);
                        } catch (ExecutionException | InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    , executorService
            );
            wikiGraph.addNodeToLayer(depth, node);
        }
    }

}