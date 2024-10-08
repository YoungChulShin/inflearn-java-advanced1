package thread.executor;

import static util.MyLogger.log;
import static util.ThreadUtils.sleep;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ExecutorBasicMain {

  public static void main(String[] args) {
    ExecutorService es = new ThreadPoolExecutor(2, 2, 0, TimeUnit.MILLISECONDS, new LinkedBlockingDeque<>());
    log("== 초기 상태 ==");
    ExecutorUtils.printState(es);
    es.execute(new RunnableTask("taskA"));
    es.execute(new RunnableTask("taskB"));
    es.execute(new RunnableTask("taskC"));
    es.execute(new RunnableTask("taskD"));
    log("== 작업 수행 중 ==");
    ExecutorUtils.printState(es);

    sleep(3000);

    log("== 작업 수행 완료 ==");
    ExecutorUtils.printState(es);

    es.close();
    log("== shutdown 완료 ==");
    ExecutorUtils.printState(es);
  }

}