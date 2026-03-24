package Coding.hw5;
// create 3 threads, one thread ouput 1-10, one thread output 11-20, one thread output 21-22. threads run
// sequence is random. (solution is in com.chuwa.exercise.t08_multithreading.PrintNumber1
// Thread-0: 1
// Thread-0: 2
// Thread-0: 3
// Thread-0: 4
// Thread-0: 5
// Thread-0: 6
// Thread-0: 7
// Thread-0: 8
// Thread-0: 9
// Thread-0: 10
// Thread-2: 11
// Thread-2: 12
// Thread-2: 13
// Thread-2: 14
// Thread-2: 15
// Thread-2: 16
// Thread-2: 17
// Thread-2: 18
// Thread-2: 19
// Thread-2: 20
// Thread-1: 21
// Thread-1: 22
// Thread-1: 23
// Thread-1: 24
// Thread-1: 25
// Thread-1: 26
// Thread-1: 27
// Thread-1: 28
// Thread-1: 29
// Thread-1: 30
public class Q24 {
    public static void main(String[] args) {
        // 线程 0 负责 1-10
        Thread t0 = new Thread(new PrintTask(1, 10), "Thread-0");
        // 线程 2 负责 11-20 (对应图中的顺序)
        Thread t2 = new Thread(new PrintTask(11, 20), "Thread-2");
        // 线程 1 负责 21-30
        Thread t1 = new Thread(new PrintTask(21, 30), "Thread-1");

        // 启动线程
        // 因为题目说运行顺序是随机的，所以我们直接 start
        // 实际上 CPU 调度哪个线程先跑，顺序就会是随机的
        t0.start();
        t2.start();
        t1.start();
    }
}

class PrintTask implements Runnable {
    private int start;
    private int end;

    public PrintTask(int start, int end) {
        this.start = start;
        this.end = end;
    }

    @Override
    public void run() {
        for (int i = start; i <= end; i++) {
            System.out.println(Thread.currentThread().getName() + ": " + i);
            // 稍微 sleep 一下可以更容易观察到线程切换的随机性
            try { Thread.sleep(10); } catch (InterruptedException e) {}
        }
    }
}
