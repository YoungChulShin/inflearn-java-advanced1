package thread.executor.future;

import static util.MyLogger.log;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import thread.executor.CallableTask;

public class InvokeAllMain {

  public static void main(String[] args) throws InterruptedException, ExecutionException {
    ExecutorService es = Executors.newFixedThreadPool(10);

    CallableTask task1 = new CallableTask("task1", 1000);
    CallableTask task2 = new CallableTask("task2", 3000);
    CallableTask task3 = new CallableTask("task3", 2000);

    List<CallableTask> tasks = List.of(task1, task2, task3);
    // 3가지 작업이 모두 완료되어야 반환
    List<Future<Integer>> futures = es.invokeAll(tasks);
    for (Future<Integer> future : futures) {
      Integer value = future.get();
      log("value = " + value);
    }

    es.close();
  }

}
