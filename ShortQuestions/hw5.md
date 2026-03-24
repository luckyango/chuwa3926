1. Read: https://www.interviewbit.com/multithreading-interview-questions/#class-level-lock-vs-object-level-lo
ck

2. Write a thread-safe singleton class
```
// double-checked singleton
// The first check is for performance. It ensures that once the instance is initialized, subsequent threads can return it immediately without acquiring the lock.
// The second check (inside the synchronized block) is for correctness. It prevents multiple threads from creating multiple instances if they both passed the first check simultaneously."
// The volatile keyword is mandatory here to prevent Instruction Reordering. Without volatile, the CPU might reorder the steps of object creation. Specifically, it might assign the memory address to the instance variable before the constructor actually finishes executing. If a second thread arrives during this tiny window, it will see a non-null but partially initialized object, which leads to a crash or undefined behavior. volatile creates a Memory Barrier, ensuring that the object is fully constructed before it becomes visible to other threads."

class Singleton{
    // private field
    private static volatile Singleton instance;
    // private constructor
    private Singleton(){}
    // public access method
    public static Singleton getInstance(){
        if(instance == null){
            synchronized(Singleton.class){
                if(instance==null){
                    instance = new Singleton();
                }
            }
        }
        return instance;
    }
}

// eager initialization
class Singleton{
    private Singleton(){}
    private static final Singleton instance = new Singleton();
    public staic Singleton getInstance(){
        return instance;
    }
}
```

3. How to create a new thread(Please also consider Thread Pool approach)?
```
// 1. extends Thread class and override the run() method
class MyThread extends Thread{
    @Override
    public void run(){
        System.out.println("Running in: " + Thread.currentThread().getName());
    }
}
// usage
new MyThread().start();

// 2. implements Runnable interface
Runnable task = ()->System.out.println("Task running...");
new Thread(task).start();

// 3.implement Callable interface
// use this if you need the thread to return the result or throw a exception. It must be wrapped in a FutureTask.
Callable<TransactionResult> checkRiskTask = () -> {
    Thread.sleep(2000); 
    double fraudScore = 0.9;
    if (fraudScore > 0.8) {
        throw new SecurityException("Potential Fraud Detected!");
    }
    return new TransactionResult("SUCCESS", 200);
};
FutureTask<TransactionResult> futureTask = new FutureTask<>(checkRiskTask);

new Thread(futureTask).start();
try {
        TransactionResult result = futureTask.get(3, TimeUnit.SECONDS);
        System.out.println("Success: " + result.getStatus());
    } catch (ExecutionException e) {
        Throwable realCause = e.getCause();
        System.err.println(realCause.getMessage());
    }

// 4.thread pool - ThreadPoolExecutor
ThreadPoolExecutor executor = new ThreadPoolExecutor(
    2,                          // corePoolSize: Minimum threads kept alive
    5,                          // maximumPoolSize: Max threads allowed
    60L, TimeUnit.SECONDS,      // keepAliveTime: Time for idle non-core threads
    new LinkedBlockingQueue<>(100), // workQueue: Buffer for waiting tasks
    Executors.defaultThreadFactory(), // threadFactory: How to create new threads
    new ThreadPoolExecutor.AbortPolicy() // handler: Rejection policy if pool is full
);

executor.execute(() -> System.out.println("Pool Task"));

```

4. Difference between Runnable and Callable?
Runnable → no return, no exception
Callable → return value, can throw exception

5. What is the difference between t.start() and t.run()?
t.start() initiates a new thread, while t.run() simply executes the method in the current thread like a regular method call.

6. Which way of creating threads is better: Thread class or Runnable interface?
Runnable interface
(1) Runnable is defining a Task. You can pass this task to a Thread, a ThreadPool, or an Executor. It separates the "what to do" from the "how to run it." Thread Class is creating a specific Type of thread.
(2) You can only extend one class but implement multiple interface.
(3) Runnable is highly compatible with Thread Pools. You can create one Runnable instance and give it to multiple threads to execute. It's easier to share resources (like a thread-safe counter) among multiple threads. 

7. What are the thread states?
(1) NEW. 
A thread that has been created but not yet started. 
Thread t = new Thread(runnable);
(2) RUNNABLE. 
A thread that is executing in the JVM.
It doesn't necessarily mean it's currently using the CPU. It might be waiting for the Operating System to assign it a processor time slice
Triggered by t.start().
(3) Blocked. 
A thread is waiting for a monitor lock to enter a synchronized block/method.
(4) WAITING
A thread is waiting indefinitely for another thread to perform a particular action.
Triggers: Object.wait(), Thread.join(), or LockSupport.park().It won't wake up until another thread calls notify() or notifyAll().
(5) TIMED_WAITING
A thread is waiting for another thread to perform an action for up to a specified waiting time.

(6)TERMINATED
A thread that has finished execution.
Once a thread is terminated, it cannot be restarted.

8. Demonstrate deadlock and how to resolve it in Java code.
A deadlock is a situation where two or more threads are permanently blocked, because each thread is waiting for a resource held by another thread, creating a circular dependency.
How to solve:
(1) Fixed Lock Ordering (The Most Common)
Always acquire locks in the exact same order. If both threads request Lock1 before Lock2, the first thread to get Lock1 will finish its work while the second waits at the first gate.

```
synchronized (Lock1) { 
    synchronized (Lock2) {
        // Safe! No circular wait.
    }
}
···
(2) Try-Lock with Timeout
Instead of using synchronized (which blocks forever), use ReentrantLock. If a thread can't get the second lock within 2 seconds, it backs off, releases its first lock, and tries again later.
```
ReentrantLock lock1 = new ReentrantLock();
ReentrantLock lock2 = new ReentrantLock();

if (lock1.tryLock(2, TimeUnit.SECONDS)) {
    try {
        if (lock2.tryLock(2, TimeUnit.SECONDS)) {
            try { /* Critical Section */ } 
            finally { lock2.unlock(); }
        }
    } finally { lock1.unlock(); }
}
```

9. How do threads communicate each other?
Threads communicate with each other mainly through shared memory and coordination mechanisms like wait(), notify(), and notifyAll(), or higher-level tools such as BlockingQueue and other concurrent utilities.

10. What’s the difference between class lock and object lock?
An object lock is acquired when a thread enters a non-static synchronized method or a synchronized(this) block. It only protects the current instance of the class. If you have two objects, obj1 and obj2, Thread A can access obj1.method() while Thread B accesses obj2.method() simultaneously. They don't interfere with each other because they hold different keys to different houses.
A class lock is acquired when a thread enters a static synchronized method or a synchronized(MyClass.class) block. It protects the entire Class across all instances.

11. What is join() method?
join() is used to make one thread wait until another thread finishes execution.
When you call t.join(), the current thread will pause and wait until thread t completes. After that, it continues execution.

12. what is yield() method
The yield() method is a static method of the Thread class that serves as a hint to the scheduler that the current thread is willing to cede its share of the CPU.
When called, the thread moves from the Running state back to the Runnable state.

13. What is ThreadPool? How many types of ThreadPool? What is the TaskQueue in ThreadPool?
A ThreadPool is a pool of reusable threads used to execute tasks, instead of creating a new thread for each task.
4 types of ThreadPool. 
FixedThreadPool: Fixed number of threads;
CachedThreadPool: Creates threads as needed
SingleThreadExecutor: Only one thread
ScheduledThreadPool: Supports delayed / periodic tasks
TaskQueue is a queue that holds tasks waiting to be executed when all threads are busy.

14. Which Library is used to create ThreadPool? Which Interface provide main functions of thread-pool?
Package Name: java.util.concurrent
Key Class: java.util.concurrent.Executors
Interface: ExecutorService
This is the interface that provides the main functions for managing a thread pool. It defines how you submit tasks and how you shut down the pool.
Key Methods: submit(), invokeAll(), shutdown(), isTerminated().

15. How to submit a task to ThreadPool?
(1)Using execute()
Method Signature: void execute(Runnable command)
It does not handle return values and makes exception handling slightly harder.
(2)Using submit() 
Method Signature: <T> Future<T> submit(Callable<T> task) or Future<?> submit(Runnable task)
When you need a Result or want to track the Status. It returns a Future object, which you can use to get the result later using .get().

16. What is the advantage of ThreadPool?
(1) Resource Management: A pool reuses existing threads, significantly reducing latency for short-lived tasks.
(2) Response Time: Since threads are already warmed up and waiting in the pool, the task starts executing immediately without the delay of thread creation.
(3) Thread Management and Control: A pool prevents your system from crashing. If 10,000 users hit your server and you create 10,000 threads, the OS will crash. A pool limits the number of concurrent threads to a safe number , placing the rest in a Queue.
(4) Orderly Shutdown: It provides mechanisms to finish pending tasks before closing the application, ensuring data integrity.

17. Difference between shutdown() and shutdownNow() methods of executor
shutdown(): Stops accepting new tasks. Executes all tasks currently in the Queue and those currently Running.
shutdownNow(): Stops accepting new tasks. Attempts to stop Running tasks (via interrupt()) and returns a List<Runnable> of tasks that were waiting in the Queue but never started.

18. What is Atomic classes? How many types of Atomic classes? Give me some code example of Atomic classes and its main methods. when to use it?
Atomic classes (found in java.util.concurrent.atomic) are utility classes that support lock-free, thread-safe programming on single variables.

There are four main categories:
Primitives: AtomicInteger, AtomicLong, AtomicBoolean.
References: AtomicReference (for objects).
Arrays: AtomicIntegerArray, AtomicLongArray.
Field Updaters: AtomicIntegerFieldUpdater (uses reflection to update a specific field).

When to use it?
Counters and Sequence Generators: The most common use case.
Non-blocking algorithms: When performance is critical and you want to avoid the overhead of synchronized or ReentrantLock.
State Flags: Using AtomicBoolean for things like isStarted or isClosed.


19. What is the concurrent collections? Can you list some concurrent data structure (Thread-safe)
Concurrent Collections (in java.util.concurrent) are specifically designed for high-concurrency environments.
Thread-safe: concurrent data: ConcurrentHashMap, CopyOnWriteArrayList, ConcurrentLinkedQueue, CopyOnWriteArraySet, ConcurrentSkipListMap

20. What kind of locks do you know? What is the advantage of each lock?
(1) Intrinsic Lock (Synchronized). Simplest to use; automatically released if an exception occurs; handled efficiently by the JVM
(2) ReentrantLock: Much more flexible. It supports tryLock() (timeout), fairness policies (preventing thread starvation), and can be interrupted.
(3) ReadWriteLock: separates Read access from Write access.

21. What is future and completableFuture? List some main methods of ComplertableFuture.
Future is a placeholder for a result that hasn't arrived yet. It’s like a claim check at a coat closet. It is blocking. To get the result, you must call .get(), which stops your thread until the task is done.
CompletableFuture is a much more powerful version that implements both Future and CompletionStage. It is non-blocking and supports functional style callbacks. 

main methods: 
1. Starting the Task
supplyAsync(Supplier<U>): Starts an asynchronous task that returns a value (uses ForkJoinPool.commonPool() by default).
runAsync(Runnable): Starts an asynchronous task that does not return a value.
2. Chaining (Callback) Methods
.thenApply(Function): Processes the result of the previous stage and returns a new result (like map).
3. Combining Multiple Futures
thenCombine(): Waits for two specific futures to finish and then combines their results.
allOf(f1, f2, f3): A static method that returns a new future that completes only when all provided futures complete.
.thenAccept(Consumer): Consumes the result but returns nothing (the end of the chain).
4. Exception Handling
.exceptionally(Function): A "catch" block for your pipeline. If any previous step fails, this method provides a fallback value.
.handle(BiFunction): A "finally" block that gives you both the result AND the exception, allowing you to process either.

22. Type the code by your self and try to understand it. (package com.chuwa.tutorial.t08_multithreading)
