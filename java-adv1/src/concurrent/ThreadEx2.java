package concurrent;

public class ThreadEx2 {

  public static void main(String[] args) throws InterruptedException {
    Thread myThread = new Thread(
        () -> {
          try {
            Thread.sleep(1000L);
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
          System.out.println("Thread: " + Thread.currentThread().getName());
        });
    myThread.start();

    System.out.println("Hello: " + Thread.currentThread().getName());
    myThread.join();
    System.out.println(myThread + " is finished");
  }

}
