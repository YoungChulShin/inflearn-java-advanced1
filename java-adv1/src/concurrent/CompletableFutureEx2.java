package concurrent;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class CompletableFutureEx2 {

  public static void main(String[] args) throws ExecutionException, InterruptedException {
    CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
      System.out.println("Hello " + Thread.currentThread().getName());
      return "Hello";
    }).thenApply((s) -> {
      System.out.println(Thread.currentThread().getName());
      return s.toUpperCase();
    });

    System.out.println(future.get());
  }

}
