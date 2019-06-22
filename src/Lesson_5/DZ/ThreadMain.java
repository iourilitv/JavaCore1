package Lesson_5.DZ;

import java.util.Arrays;

import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.System.currentTimeMillis;

public class ThreadMain {
    public static void main(String[] args) {
        final int SIZE = 10000000;
        final int THREADS_COUNT = 2;
        // определяем размерность двумерного массива
        final int PART_SIZE = SIZE / THREADS_COUNT;
        float[] mas = new float[SIZE];
        Arrays.fill(mas, 1f);
        long a = currentTimeMillis();
        // разделяем данные
        final float[][] m = new float[THREADS_COUNT][PART_SIZE];
        // создадим массив потоков
        Thread[] t = new Thread[THREADS_COUNT];
        for (int i = 0; i < THREADS_COUNT; i++) {
            // будем копировать в двумерный массив данные из основного потока со сдвигом
            System.arraycopy(mas, PART_SIZE * i, m[i], 0, PART_SIZE);
            final int u = i;
            t[i] = new Thread(new Runnable() {
                @Override
                public void run() {
                    // считаем массив со сдвигом
                    int n = u * PART_SIZE;
                    for (int j = 0; j < PART_SIZE; j++, n++) {
                        m[u][j] = (float) (m[u][j] * sin(0.2f + n / 5) * cos(0.2f + n / 5) * cos(0.4f + n / 2));
                    }
                }
            });
            t[i].start();
        }
        try {
            for (int i = 0; i < THREADS_COUNT; i++) {
                t[i].join();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // складываем массив обратно в исходный массив
        for (int i = 0; i < THREADS_COUNT; i++) {
            System.arraycopy(m[i], 0, mas, i * PART_SIZE, PART_SIZE);
        }
        // определяем время
        System.out.println(currentTimeMillis() - a);
    }
}