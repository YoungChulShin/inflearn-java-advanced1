package concurrent;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class CompletableFutureEx4 {

  public static void main(String[] args) throws ExecutionException, InterruptedException {
    CompletableFuture<String> hello = CompletableFuture.supplyAsync(() -> {
      System.out.println("Hello " + Thread.currentThread().getName());
      return "Hello";
    }).exceptionally(e -> {
      return "Error";
    });

    CompletableFuture<String> world = CompletableFuture.supplyAsync(() -> {
      System.out.println("World " + Thread.currentThread().getName());
      return " World";
    });

    CompletableFuture<String> future = hello.thenCombine(world, (a, b) -> a + b);
    System.out.println(future.get());
  }

}
