package thread.executor.poolsize;

import static thread.executor.ExecutorUtils.printState;
import static util.MyLogger.log;
import static util.ThreadUtils.sleep;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import thread.executor.RunnableTask;

public class PoolSizeMainV1 {

  public static void main(String[] args) {
    ArrayBlockingQueue<Runnable> workQueue = new ArrayBlockingQueue<>(2);
    ExecutorService es =
        new ThreadPoolExecutor(2, 4, 3000, TimeUnit.MILLISECONDS, workQueue);
    printState(es);

    es.execute(new RunnableTask("task1"));
    printState(es, "task1");

    es.execute(new RunnableTask("task2"));
    printState(es, "task2");

    es.execute(new RunnableTask("task3"));
    printState(es, "task3");

    es.execute(new RunnableTask("task4"));
    printState(es, "task4");

    // active와 queue가 가득차면 max가 올라간다
    es.execute(new RunnableTask("task5"));
    printState(es, "task5");

    es.execute(new RunnableTask("task6"));
    printState(es, "task6");

    try {
      es.execute(new RunnableTask("task7"));
      printState(es, "task7");
    } catch (RejectedExecutionException e) {
      log("task7 실행 거절 예외 " + e);
    }

    sleep(3000);
    log("== 작업 수행 완료 ==");
    printState(es);

    sleep(3000);
    log("== maximumPoolSize 대기시간 초과 ==");
    printState(es);

    es.close();
    log("== shutdown 완료 ==");
    printState(es);
  }

}
