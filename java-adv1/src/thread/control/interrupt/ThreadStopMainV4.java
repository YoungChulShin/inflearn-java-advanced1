package thread.control.interrupt;

import static util.MyLogger.log;
import static util.ThreadUtils.sleep;

public class ThreadStopMainV4 {

  public static void main(String[] args) {
    MyTask task = new MyTask();
    Thread thread = new Thread(task, "work");
    thread.start();

    sleep(100);
    log("작업 중단 지시 runFlag=false");
    thread.interrupt();
    log("work 스레드 인터럽트 상태1 = " + thread.isInterrupted());

  }

  static class MyTask implements Runnable {

    @Override
    public void run() {
      while (!Thread.interrupted()) {
        log("작업 중");
      }
      log("work 스레드 인터럽트 상태2 = " + Thread.currentThread().isInterrupted());
      log("자원 정리");
      log("정리 종료");
    }
  }

}
