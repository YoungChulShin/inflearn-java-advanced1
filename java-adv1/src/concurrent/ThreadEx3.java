package concurrent;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadEx3 {

  public static void main(String[] args) {
    // Executor
    // ExecutorService
    // ScheduledExecutorService

    ExecutorService executorService = Executors.newFixedThreadPool(2);
    executorService.submit(() -> extracted("Hello"));
    executorService.submit(() -> extracted("Java"));
    executorService.submit(() -> extracted("Thread"));
    executorService.submit(() -> extracted("Test"));

    executorService.shutdown();
  }

  private static void extracted(String name) {
    System.out.println(name + " - Thread " + Thread.currentThread().getName());
  }
}
