package concurrent;

public class ThreadEx1 {

  public static void main(String[] args) {
    MyThread myThread = new MyThread();
    myThread.start();

    System.out.println("Hello: " + Thread.currentThread().getName());

  }

  static class MyThread extends Thread {

    @Override
    public void run() {
      System.out.println("Thread: " + Thread.currentThread().getName());
    }
  }

}
