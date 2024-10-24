# 저장소 설명
인프런 `김영한의 실전 자바 - 고급 1편, 멀티스레드와 동시성` 강의 메모 및 실습 저장소

# 강의 메모
## 프로세스와 스레드
멀티태스킹
- 하나의 컴퓨터 시스템이 여러 작업을 동시에 실행
- 스케쥴링: CPU에 어떤 프로그램을 얼마나 실행할지 운영체제가 결정하는 것

멀티프로세싱
- 멀티프로세서, CPU. H/W 관점.
- CPU core가 둘이상이면 싱글 core 보다 빠르게 실행된다. 

프로세스
- 실행중인 프로그램의 인스턴스.
- 프로세스마다 독립적인 메모리 공간을 가진다.
- 하나 이상의 스레드를 포함한다. 

스레드
- 프로세스 내에서 작업 단위.
- 프로세스보다 가볍다. 
- 하나의 프로세스 내에서 스레드 들은 힙, 코드 메모리 공간을 공유한다. 스택 메모리는 개별적으로 가진다. 
- CPU를 사용해서 Code를 하나씩 실행한다. 

스케쥴링
- 스케쥴링 큐를 가지고 있다. 큐에는 각 스레드가 들어간다. ㅇ
- 큐에있는 스레드 정보를 CPU가 순서대로 불러와서 사용하고, 다시 큐에 넣는다. 
- Core가 늘어날 수록 병렬 처리가 된다. 

컨텍스트 스위칭
- 스레드가 변경되는 시점에 기존에 실행되는 스레드의 정보를 메모리에 저장하고, 새로 실행할 스레드의 정보를 CPU로 불러온다. 
- 이러한 작업은 CPU 입장에서는 어느정도 시간을 쓰는 작업이다.

## 스레드
스레드 정보
1. threadId: 스레드의 고유한 아이디
2. threadName: 스레드의 이름. 고유하지는 않다. 
3. priority: 스레드의 우선 순위. 기본 값 5. 1이 제일 낮고, 10이 제일 높다. 
4. threadGroup: 스레드 그룹
5. state: 스레드의 상태
   - NEW (새로운 상태)
      - 신규 생성되었고 아직 사용되지 않음
   - RUNNABLE (실행 가능 상태)
      - 스레드가 실행 중이거나 실행될 준비가 된 상태
   - 일시 중지 상태(Suspended State)
      - BLOCKED (차단 상태)
         - 스레드가 동기화 락을 기다리는 상태
         - 예: synchronized
      - WAITING (대기 상태)
         - 스레드가 다른 스레의 특정 작업이 완료되기를 기다리는 상태
         - 예: 파일 전송
      - TIMED_WAITING (시간 제한 대기) 
         - 일정 시간 동안 기다리는 상태
         - 예: Thread.sleep()
   - TERMINATED (종료 상태)
      - 스레드가 실행을 마친 상태. 

인터럽트
- waiting 상태의 스레드를 인터럽트를 통해서 깨울 수 있다. 깨워진 스레드는 runnable 상태가 된다. 
- `interrupt()` 메서드로 호출 할 수 있다. 
- `interrupt()` 가 호출되면 `isInterrupted` 상태가 `true`가 되는데, 별도의 처리가 없으면 이 상태가 계속 유지된다. 
   - 이후애 interruptException을 발생시키는 메서드(예: sleep)를 만나면 예외가 발생한다. 
- 대안으로 `interrupted()` 메서드를 호출하면, 상태가 인터럽트일 경우에 상태를 `false`로 변경해준다. 

yield
- sleep을 사용하면 thread가 runnable -> timed_waiting 상태로 변경이 발생한다. 
- yield를 사용하면 runnable 상태를 유지하면서, 스케쥴링 큐에 다시 대기하도록 할 수 있다. 
- 잠깐 실행을 양보할 때에는 yield를 사용해서 처리할 수 있다. 일정 시간 양보하려면 sleep을 사용한다. 

## 메모리 가시성
메모리 가시성
- 멀티스레드에서 Cache에서 읽어오는 데이터에 의해서 각 스레드 사이에 데이터 동기화가 바로 안되는 문제

메모리 동기화
- Thread의 변경은 cache에 반영되는데 
- cache의 변경 내용이 main memory에 반영되는 시점은 알 수 없다
- 그리고 다른 thread에서 바라보는 cache에 main memory의 변경 사항이 반영되는 시점도 알 수 없다. 
- 이로 인해서 각 스레드간에 참조하는 변수 값의 차이가 발생하는 것을 메모리 가시성 문제라고 한다. 
- 보통 context switching이 발생할 때, 스레드의 작업을 백업해야하기 때문에 이때 main memory에 반영될 가능성이 높다. 

Volatile
- main memory를 항상 사용한다. 
- cache 보다는 느리다는 문제가 있지만, 가시성 문제를 피할 수 있다. 

Java Memory Model
- happens-before
   - 스레드간 작업 순서에 대한 내용
   - A작업이 B작업보다 happens-before 관계에 있다면, A 작업의 변경 사항은 B 작업에서 볼 수 있다. 

## 동기화 - synchronized
임계 영역 (Critical Section)
- 여러 스레드가 동시에 접근하면 데이터의 불일치나 예상하지 못한 동작이 발생할 수 있는 코드 부분
- 예: 여러 스레드가 공유자원 수정

synchronized
- 임계 영역 문제를 해결하기 위한 문법
- monitor lock: 자바에서 각각의 객체는 lock을 가진다. 이 Lock을 획득하는 스레드가 임계 영역에서의 코드를 먼저 처리할 수 있고, 나머지 스레드는 대기하게 된다. 
- 다른 스레드가 lock을 가지고 있으면, 접근하는 스레드의 상태는 Runnable -> Blocked로 변경된다. 
- synchroized는 기본적으로 메서드에 설정하지만, 그 범위를 줄이기 위해서 메서드 내부에도 정의할 수 있다. 
- 장점
   - 프로그래밍 언어로 잠금
   - 자동 잠금 해제
- 단점
   - 무한대기: 스레드의 락이 풀릴 때까지 무한 대기 한다. 타임아웃이나 인터럽트가 없다. 

## 고급 동기화 - concurrentLock
기존에 synchronized는 무한정 대기해야하는 불편함이 존재한다. 

LockSupport
- 스레드를 waiting 상태로 변경할 수 있다. 
- 기능
   - park(): 스레드를 waiting 상태로 변경.
   - parkNanos(nanos): 나노초 동안 timed_waiting 상태로 변경한다.
   - unpark(thread): waiting 상태의 스레드를 runnable 상태로 변경한다. 

LockSupport를 이용하면 스레드를 waiting 상태로 변경하고, 일정 시간 또는 인터럽트를 통해서 깨울수 있기 때문에 Synchroized가 가지는 무한정 대기를 개선할 수 있다. 


### Blocked, Waiting
공통
- 모두 스레드가 대기하는 상태
- 실행 스케쥴링에 들어가지 않기 때문에 CPU 입장에서는 실행하지 않는 비슷한 상태.

인터럽트
- Blocked는 인터럽트가 걸려도 대기 상태를 나오지 못한다. 
- Waiting, Timed_Watiing은 대기 상태를 빠져 나와서 Runnable로 변경된다. 

용도
- Blocked는 Synchroized에서 락을 획득하기 위해 대기할 때 사용된다. 
- Waiting은 스레드가 특정 조건이나 시간 동안 대기할 때 발생하는 상태이다. 
- Waiting: Thread.join(), LockSupport.park(), Object.wait()
- Timed_Waiting: Thread.sleep, LockSupport.parkNanos, Thread.join(long millis), Object.wait(long timeout)


### ReentrantLock
Lock 인터페이스
- 동시성 프로그래밍에서 쓰이는 안전한 임계 영역을 위한 락을 구현하는데 사용된다. 
- 대표적인 구현체로 ReentrantLock이 있다. 
- 메서드
   - void lock()
      - 락을 획득한다. 인터럽트로도 깨울 수 없다. 
   - void lockInterruptibly()
      - 락을 획득하는데, 인터럽트에 반응한다. 
   - boolean tryLock()
      - 락 획득을 시도한다. 다른 스레드가 사용 중이라면 false를 반환하고, 그렇지 않으면 락을 획득하고 true를 반환한다. 
   - boolean tryLock(long time, TimeUnit unit)
   - void unlock()
      - 락을 해제한다. 
      - 락을 획득한 스레드가 호출해야한다. 
   - Condition newCondition()

공정성
- sychronized는 락이 돌아왔을 때, 어떤 스레드가 락을 획득할 지 알 수 없다. 
- Lock 인터페이스는 스레드가 공정하게 락을 획득할 수 있는 모드는 제공한다. 
- 공정모드는 내부적으로 순서보장을 하기 위해서 어느정도 성능이 떨어질 수 있다. 보통의 경우는 비공정 모드로 대응이 되는데, 꼭 순서 보장이 되어야한다면 공정 모드를 사용한다. 

## ExecutorService
### Thread를 직접 생성할 때 문제점
1. 리소스 측면
   - 각 Thread는 개별 call stack을 가지기 때문에 메모리를 소모한다.
   - 운영체제의 자원을 사용한다. (system call 실행)
   - 운영체제 스케줄러 자원 사용
2. Runnable Interface의 불편함
   - 반환값이 없다
   - 예외처리가 어렵다. (checkedException을 throw 할 수 없다)

### Executor Interface
구조
- Executor interface
   - Runnable을 받아서 실행한다. 
   ```java
   public interface Executor {
      void execute(Runnable command);
   }
   ```
- ExecutorService interface
   - Executor를 상속한 인터페이스. 
   - Executor보다 조금 더 많은 기능을 제공한다. 
- ThreadPoolExecutor class 
   - ExecutorService의 대표 구현체

### Future
Future Interface
- 작업의 미래 결과를 받을 수 있는 객체
- 요청 스레드는 블로킹 되지 않고 필요한 작업을 할 수 있다.

FutureTask 
- Future의 구현체중 하나

Callable
- 응답 값을 반환하는 인터페이스
- Exception을 throw 할 수 있다. Runnable은 throw 할 수 없다. 

Future.get()을 호출하면 
1. 완료 되었으면 리턴하고 
2. 완료가 안되었으면 대기 한다. 완료되면 메인 Waiting 상태의 메인 스레드를 깨운다. 

Future를 잘못 사용하는 예
- 호출하고 바로 대기하는 케이스

주요 메서드
- bool cancel (boolean mayInterruptIfRunning)
   - 아직 완료되지 않은 작업을 취소한다. 
   - mayInterruptIfRunning
      - true: 작업이 실행중이라면 Thread.interrupt()를 통해서 작업을 중단한다. 
      - false: 실행중이면 중단하지 않는다. 
   - 취소 상태의 Future에 get()을 호출하면, CancellationException이 발생한다. 
- isCancelled(): 취소 여부 확인
- isDone(): 작업이 완료되었는지 확인. 예외나 취소도 true를 반영한다. 
- get(): 작업이 완료될 때까지 대기하고, 완료되면 결과를 반환한다. 
- get(long timeout, TimeUnit unit): 시간 초과되면 예외를 발생시킨다. 

Future의 취소
- cancel() 메서드로 처리할 수 있다. 
- 공통으로 
   - 상태가 `CANCELLED` 로 변경된다. 
   - 따라서 get()을 호출하면 `CancellationException이`이 발생한다. 
- mayInterruptIfRunning이
   - true라면, 진행중인 작업을 종료시킨다. 작업 스레드에서는 `InterruptedException`이 발생한다. 
   - false라면, 상태는 `CANCELLED`로 변경되는데 진행 중인 작업은 종료시키지 않고 계속 실행된다. 

Future 예외 처리 
- 작업 중인 스레드 내부에서 에러가 발생하면 future.get()을 할 때 `ExecutionException`이 발생한다.
- `cause` 정보를 가져오면, 실제 예외 값을 확인할 수 있다. 
- Future의 상태는 `FAILED`가 된다. 

여러개의 Callable 작업을 수행
- `invokeAll` : 모든 작업을 수행하고, 결과를 응답한다. 
- `invokeAny` : 모든 작업 중에서 먼저 실행되는 작업의 결과를 리턴하고, 나머지 작업은 취소한다. 

## ExecutorService
### 서비스 종료
메서드
- shutDown(): 추가적인 작업을 받지 않고, 이미 제출한 작업들이 완료되면 종료한다. 논블로킹. 
- shutDownNow(): 실행중인 작업을 중단하고, 대기중인 작업을 반환해서 즉시 종료한다. 실행중인 작업을 종료하기 위해서 인터럽트를 발생시킨다. 논블로킹.
- bool awaitTermination(): 지정된 시간 동안 모든 작업이 완료될 때까지 대기한다. 블로킹.
- close(): 자바 19부터 지원. shutDown()을 호출하고, 하루를 기다려도 작업이 완료되지 않으면 shutDownNow()를 호출한다. 

### 스레드 풀 관리 
스레드풀 속성
- corePoolSize: 최소 풀 사이즈. 처음 시작은 0으로 처음부터 corePoolSize만큼 생성되어 있지는 않는다. 
   - ThreadPoolExecutor#prestartAllCoreThreads()를 실행하면 처음부터 생성되게 할 수 있다. 
- maxPoolSize: 최대 풀 사이즈. corePoolSize만큼 요청이 다 차면, Queue에 작업이 적재된다. Queue도 다 차면 maxPoolSize만큼 스레드가 생성된다. 
- keepAliveSeconds: 초과 스레드가 유휴상태로 유지될 수 있는 시간. 해당 시간이 지나면 삭제된다. 
- Queue: 대기 작업을 담을 큐.

maxPoolSize만큼 작업이 다 찼다면
- 추가로 들어오는 작업은 `RejectedExecutionException` 이 발생해서 처리되지 않는다. 

스레드 풀 생성
- newSingleThreadPool(): 단일 스레드
- newFixedThreadPool(nThread): 고정 스레드풀
   - 갑작스럽게 요청이 늘어날 경우에, Queue에 쌓이는 작업의 수가 계속 늘어날 수 있는 문제가 있다. 
- newCachedThreadPool():
   - 기본 스레드를 사용하지 않고, 60초 생존주기를 가진 초과 스레드만 사용한다. 
   - 초과 스레드 수는 제한이 없다. 
   - `SynchornousQueue`를 사용한다. 이 큐는 내부 저장 공간이 없어서 생산자의 작업을 소비자에게 바로 전달한다. 
   - 사용자의 요청애 맞춘 스레드 수 변화.

사용자 정의 풀 전략
- 기본 스레드와 최대 스레드 수를 정의하고, 최대 스레드는 생존 시간을 정의한다. 
- 대기 큐의 수를 정의해서 일정 수를 넘어가면 거절되도록 한다. 
   ```java
   ExecutorService es = new ThreadPoolExecutor(100, 200, 60, TimeUnit.SECONDS, new ArrayBlockingQueue<>(1000));
   ```

ThreadPoolExecutor 예외 정책
- AbortPolicy: 기본 정책. RejectedExecutionException 발생
- DiscardPolicy: 새로운 작업을 버린다. 
- CallerRunsPolicy: 작업을 제출한 스레드가 대신해서 작업을 하도록 한다. 생산자 스레드가 작업을 대신하기 때문에 작업의 속도가 느려진다. 생산 속도를 느리게 조절할 수 있다. 
- 사용자 정의 (RejectedExecutionHandler)

## CompletableFuture
인프런 `더 자바 8 강의` 메모.

Future로 하기 어려웠던 작업
- Future를 외부에서 완료시킬 수 없다. 취소하거나, get()에 타임아웃을 설정할 수는 있다. 
- 블로킹 코드(get())를 사용하지 않고서는, 작업이 끝났을 때 콜백을 실행할 수 없다. 
- 여러 Future를 조합할 수 없다. 
- 예외 처리용 API를 제공하지 않는다. 

CompletableFuture
- 외부에서 Complete 시킬 수 있다. 
- 메서드
   - runAsync(): 리턴이 없는
   - supplyAsync(): 리턴이 있는
- 체이닝
   - thenApply(): 리턴이 있는
   - thenAccept(): 리턴이 없는
   - thenRun(): 이전 결과값과 상관 없이 특정 작업을 수행할 때
   ```java
    CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
      System.out.println("Hello " + Thread.currentThread().getName());
      return "Hello";
    }).thenApply((s) -> {
      System.out.println(Thread.currentThread().getName());
      return s.toUpperCase();
    });
   ```

스레드풀
- 기본적으로는 ForkJoinPool의 commonPool을 사용한다. 
- 만들어서 스레드를 지정해줄 수도 있다. 

예외 처리
- exceptionally: 예외 처리
- handle: 정상 값과 예외를 각각 받아서 처리하는 방법