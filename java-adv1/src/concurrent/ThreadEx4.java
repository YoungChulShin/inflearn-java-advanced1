package concurrent;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ThreadEx4 {

  public static void main(String[] args) throws ExecutionException, InterruptedException {
    // Executor
    // ExecutorService
    // ScheduledExecutorService

    ExecutorService executorService = Executors.newFixedThreadPool(2);
    Future<String> submit = executorService.submit(new MyCallable());
    System.out.println(submit.get());

    executorService.shutdown();
  }

  private static class MyCallable implements Callable<String> {

    @Override
    public String call() throws Exception {
      Thread.sleep(2000L);
      return "Hello";
    }
  }
}
