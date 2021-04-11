package com.mrrightli.week04;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Demo1 {
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(1);
        try {
            Future<Double> future = executorService.submit(() -> {
                return 2.0;
            });
            double b = future.get();
            System.out.println(b);
        } catch (Exception ex) {
            System.out.println("catch submit");
            ex.printStackTrace();
        }
        executorService.shutdown();
        System.out.println("Main Thread End!");
    }
}
