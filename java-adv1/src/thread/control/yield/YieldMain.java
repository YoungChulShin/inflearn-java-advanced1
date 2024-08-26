package thread.control.yield;

import static util.ThreadUtils.sleep;

public class YieldMain {

  static final int THREAD_COUNT = 1000;

  public static void main(String[] args) {
    for (int i = 0; i < THREAD_COUNT; i++) {
      Thread thread = new Thread(new MyRunnable());
      thread.start();
    }
  }

  static class MyRunnable implements Runnable {

    @Override
    public void run() {
      for (int i = 0; i < 10; i++) {
        System.out.println(Thread.currentThread().getName() + " - " + i);

        // timed waiting 상태로 빠진다.
        // 양보할 상황은 아닌데 스레드를 쉬게 한다
        // sleep(1);

        // runnable 상태 유지
        // - running: 실행중
        // - ready: 실행할 준비가 되었지만 cpu가 바빠서 스케쥴링 큐에서 대기 중
        Thread.yield();
      }
      //
    }
  }



}
