package Lesson_5;

public class Counter {

    int c;

    public Counter(int c) {
        this.c = c;
    }

    public int getC() {
        return c;
    }

    public void setC(int c) {
        this.c = c;
    }

    public synchronized void dec() {
        c--;
    }

    public synchronized void inc() {
        c++;
    }
}


class MainCounter {
    public static void main(String[] args) {
        Counter counter = new Counter(0);
        Counter counter1 = new Counter(0);

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 1000000; i++) {
                    counter.inc();
                }
            }
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 1000000; i++) {
                    counter1.dec();
                }
            }
        });

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(counter.getC());

    }
}
