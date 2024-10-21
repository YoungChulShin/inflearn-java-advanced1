package concurrent;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class CompletableFutureEx1 {

  public static void main(String[] args) throws ExecutionException, InterruptedException {
    CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
      System.out.println("Hello " + Thread.currentThread().getName());
    });
    future.get();
  }

}
