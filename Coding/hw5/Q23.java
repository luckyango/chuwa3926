package Coding.hw5;
// 23. Write a code to create 2 threads, one thread print 1,3,5,7,9, another thread print 2,4,6,8,10. (solution is in
// com.chuwa.tutorial.t08_multithreading.c05_waitNotify.OddEventPrinter)
// 1. One solution use synchronized and wait notify
// 2. One solution use ReentrantLock and await, signal

// Thread-0: 1
// Thread-1: 2
// Thread-0: 3
// Thread-1: 4
// Thread-0: 5
// Thread-1: 6
// Thread-0: 7
// Thread-1: 8
// Thread-0: 9
// Thread-1: 10
// Process finished with exit code 0

// method1:
// public class Q23 {
//     private int count = 1;
//     private final int MAX = 10;
//     private final Object lock = new Object();

//     public void printNumbers() {
//         Thread t1 = new Thread(() -> {
//             while (count <= MAX) {
//                 synchronized (lock) {
//                     if (count % 2 == 0) {
//                         try { lock.wait(); } catch (InterruptedException e) {}
//                     }
//                     if (count <= MAX) {
//                         System.out.println(Thread.currentThread().getName() + ": " + count++);
//                     }
//                     lock.notify(); 
//                 }
//             }
//         }, "Thread-0");

//         Thread t2 = new Thread(() -> {
//             while (count <= MAX) {
//                 synchronized (lock) {
//                     if (count % 2 != 0) {
//                         try { lock.wait(); } catch (InterruptedException e) {}
//                     }
//                     if (count <= MAX) {
//                         System.out.println(Thread.currentThread().getName() + ": " + count++);
//                     }
//                     lock.notify(); 
//                 }
//             }
//         }, "Thread-1");

//         t1.start();
//         t2.start();
//     }

//     public static void main(String[] args) {
//         new Q23().printNumbers();
//     }
// }

//method 2
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Q23 {
    private int count = 1;
    private final int MAX = 10;
    private final ReentrantLock lock = new ReentrantLock();
    private final Condition condition = lock.newCondition();

    public void printNumbers() {
        Runnable task = () -> {
            while (count <= MAX) {
                lock.lock();
                try {
                    System.out.println(Thread.currentThread().getName() + ": " + count++);
                    
                    condition.signal(); 
                    
                    if (count <= MAX) {
                        condition.await(); 
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
            }
        };

        new Thread(task, "Thread-0").start();
        new Thread(task, "Thread-1").start();
    }

    public static void main(String[] args) {
        new Q23().printNumbers();
    }
}