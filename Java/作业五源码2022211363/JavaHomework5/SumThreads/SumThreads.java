package SumThreads;

public class SumThreads {
    private static int totalSum = 0;

    public static void main(String[] args) {
        Thread[] threads = new Thread[10];
        Object lock = new Object();

        for (int i = 0; i < 10; i++) {
            final int start = i * 10 + 1;
            final int end = (i + 1) * 10;
            threads[i] = new Thread(new Runnable() {
                public void run() {
                    int sum = 0;
                    for (int j = start; j <= end; j++) {
                        sum += j;
                    }
                    synchronized (lock) {
                        totalSum += sum;
                        System.out.println("Sum from " + start + " to " + end + " is: " + sum);
                    }
                }
            });
            threads[i].start();
        }

        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt(); // 恢复中断状态
            }
        }

        System.out.println("Total sum of all threads: " + totalSum);
    }
}
