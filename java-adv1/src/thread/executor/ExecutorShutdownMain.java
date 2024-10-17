package thread.executor;

import static thread.executor.ExecutorUtils.printState;
import static util.MyLogger.log;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ExecutorShutdownMain {

  public static void main(String[] args) {
    ExecutorService es = Executors.newFixedThreadPool(2);
    es.execute(new RunnableTask("taskA"));
    es.execute(new RunnableTask("taskB"));
    es.execute(new RunnableTask("taskC"));
    es.execute(new RunnableTask("longTask", 100_000));

    printState(es);
    log("== shutdown 시작");
    shutdownAndAwaitTermination(es);
    log("== shutdown 완료");
    printState(es);
  }


  private static void shutdownAndAwaitTermination(ExecutorService es) {
    es.shutdown();  // non-blocking. 새로운 작업을 받지 않는다.

    try {
      // 대기중인 작업을 완료할 때까지 10초 대기
      if (!es.awaitTermination(10, TimeUnit.SECONDS)) {
        log("서비스 정상 종료 실패 -> 강제 종료 대기");
        es.shutdownNow();

        if (!es.awaitTermination(10, TimeUnit.SECONDS)) {
          log("서비스가 종료되지 않았습니다");
        }
      }
    } catch (InterruptedException e) {
      es.shutdownNow();
    }
  }

}
