package concurrent;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class CompletableFutureEx3 {

  public static void main(String[] args) throws ExecutionException, InterruptedException {
    CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
      System.out.println("Hello1 " + Thread.currentThread().getName());
      return "Hello";
    }).thenApplyAsync(s -> {
      try {
        Thread.sleep(3000);
      } catch (InterruptedException e) {
        throw new RuntimeException(e);
      }
      System.out.println("Hello2 " + Thread.currentThread().getName());
      return s + " World";
    }).thenApply(s -> {
      System.out.println("Hello3 " + Thread.currentThread().getName());
      return s + " World";
    })

        ;

    CompletableFuture<String> future1 = future.thenCompose(CompletableFutureEx3::getWorld);

    System.out.println(future1.get());

  }

  private static CompletableFuture<String> getWorld(String name) {
    return CompletableFuture.supplyAsync(() -> {
      System.out.println("World " + Thread.currentThread().getName());
      return name + " World";
    });
  }

}
