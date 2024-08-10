package thread.control;

import util.ThreadUtils;

public class CheckedExceptionMain {

  public static void main(String[] args) {

  }

  static class CheckedRunnable implements Runnable {

    @Override
    public void run() {
      // Exception을 throw 할 수 없ㅅ다.
      // 
    }
  }

}
