package Lesson_5.HW;

/**
 * Java Core. Продвинутый уровень.
 * Вебинар 03 июня 2019 MSK (UTC+3).
 * Урок 5. Многопоточность.
 * Home Work.
 * @author Yuriy Litvinenko.
 * 1. Необходимо написать два метода, которые делают следующее:
 * 1) Создают одномерный длинный массив, например:
 * static final int size = 10000000;
 * static final int h = size / 2;
 * float[] arr = new float[size];
 * 2) Заполняют этот массив единицами;
 * 3) Засекают время выполнения: long a = System.currentTimeMillis();
 * 4) Проходят по всему массиву и для каждой ячейки считают новое значение по формуле:
 * arr[i] = (float)(arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
 * 5) Проверяется время окончания метода System.currentTimeMillis();
 * 6) В консоль выводится время работы: System.out.println(System.currentTimeMillis() - a);
 * Отличие первого метода от второго:
 * Первый просто бежит по массиву и вычисляет значения.
 * Второй разбивает массив на два массива, в двух потоках высчитывает новые значения и потом склеивает эти массивы обратно в один.
 *
 * Пример деления одного массива на два:
 * System.arraycopy(arr, 0, a1, 0, h);
 * System.arraycopy(arr, h, a2, 0, h);
 *
 * Пример обратной склейки:
 * System.arraycopy(a1, 0, arr, 0, h);
 * System.arraycopy(a2, 0, arr, h, h);
 *
 * Примечание:
 * System.arraycopy() копирует данные из одного массива в другой:
 * System.arraycopy(массив-источник, откуда начинаем брать данные из массива-источника, массив-назначение, откуда начинаем записывать данные в массив-назначение, сколько ячеек копируем)
 * По замерам времени:
 * Для первого метода надо считать время только на цикл расчета:
 * for (int i = 0; i < size; i++) {
 * arr[i] = (float)(arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
 * }
 * Для второго метода замеряете время разбивки массива на 2, просчета каждого из двух массивов и склейки.
 */
public class MainThreadV1 {
    static final int size = 10000000;
    static final int h = size / 2;

    public static void main(String[] args) {
        //Реализация четырьмя способами
        simpleCalculating();//без разбивки на потоки
        /*Simple. Creating array time:53
                  Array[4999999]:1.0, Array[9999999]:1.0
                  Calculating time:13524
                  Total testing time:13577
                  Array[4999999]:0.06320445, Array[9999999]:0.06892343 */

        threadCalculating1();//Два потока с формулами внутри.
        /*Thread_1. Creating array time:41
                    T1.Array[4999999]:1.0, T1.Array[9999999]:1.0
                    T1.Splitting array time:51
                    T1.Calculating a1 array time:4934
                    T1.Calculating a2 array time:8690
                    T1.Collecting array time:11
                    T1.Total testing time:8793
                    T1.Array[4999999]:0.06320445, T1.Array[9999999]:0.06892343 */

        threadCalculating2();//Два потока с методом(Поток1) и формулой(Поток2).
        /*Thread_2. Creating array time:60
                    T2.Array[4999999]:1.0, T2.Array[9999999]:1.0
                    T2.Splitting array time:61
                    T2.Calculating a1 array time:4906
                    T2.Calculating a2 array time:8780
                    T2.Collecting array time:8
                    T2.Total testing time:8909
                    T2.Array[4999999]:0.06320445, T2.Array[9999999]:0.06892343 */
        threadCalculating3(); //Два потока, использующие один метод одновременно.
        // TODO! Второй поток ждет пока П1 освободит synchronized метод!
        /*Thread_3. Creating array time:54
                    T3.Array[4999999]:1.0, T3.Array[9999999]:1.0
                    T3.Splitting array time:51
                    T3.Calculating a2 array time:9056
                    T3.Calculating a1 array time:9056
                    T3.Collecting array time:8
                    T3.Total testing time:9177
                    T3.Array[4999999]:0.06320445, T3.Array[9999999]:0.06892343 */
        // TODO! Оба потока работают паралельно с НЕ synchronized методом!
        /*Thread_3. Creating array time:52
                    T3.Array[4999999]:1.0, T3.Array[9999999]:1.0
                    T3.Splitting array time:61
                    T3.Calculating a1 array time:6801
                    T3.Calculating a2 array time:6801
                    T3.Collecting array time:8
                    T3.Total testing time:6922
                    T3.Array[4999999]:0.06320445, T3.Array[9999999]:0.06892343 */
    }

    static float[] createArray(){
        //засекаем время
        long a = System.currentTimeMillis();
        //создаем  и наполняем массив единицами
        float[] arr = new float[size];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = 1;
        }
        //выводим время выполнения метода наполнения массива
        System.out.println("Creating array time:" + (System.currentTimeMillis() - a));
        return arr;
    }

    static /*synchronized */void calculating(float[] arr, int startIndex){
        //коррекция формулы из-за разбивки массива
        if(startIndex == 0){
            for (int i = 0; i < arr.length; i++) {
                arr[i] = (float)(arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
            }
        }
        else{
            //TODO! В формуле i заменяем (i + h), кроме индекса элемента массива,
            // чтобы вторая часть считалась правильно!
            for (int i = 0; i < arr.length; i++) {
                arr[i] = (float)(arr[i] * Math.sin(0.2f + (i + startIndex) / 5) * Math.cos(0.2f + (i + startIndex) / 5) * Math.cos(0.4f + (i + startIndex) / 2));
            }
        }
    }

    static void simpleCalculating(){
        long tA = System.currentTimeMillis();
        System.out.println("Simple.");
        //создаем  и наполняем массив
        float[] arr = createArray();
        System.out.println("Array[" + (h - 1) + "]:" + arr[h - 1] + ", Array[" + (size - 1) + "]:" + arr[size - 1]);
        //перезаписываем массив
        //засекаем время
        long a = System.currentTimeMillis();
        /*for (int i = 0; i < arr.length; i++) {
            arr[i] = (float)(arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
        }*/
        calculating(arr, 0);
        //выводим время выполнения цикла перезаписи
        System.out.println("Calculating time:" + (System.currentTimeMillis() - a));
        System.out.println("Total testing time:" + (System.currentTimeMillis() - tA));
        System.out.println("Array[" + (h - 1) + "]:" + arr[h - 1] + ", Array[" + (size - 1) + "]:" + arr[size - 1]);

        //arr[size - 1] = 1;
        //arr[size - 1] = (float)(arr[size - 1] * Math.sin(0.2f + (size - 1) / 5) * Math.cos(0.2f + (size - 1) / 5) * Math.cos(0.4f + (size - 1) / 2));
        //System.out.println("Array[" + (h - 1) + "]:" + arr[h - 1] + ", Array[" + (size - 1) + "]:" + arr[size - 1]);
    }

    static void threadCalculating1(){
        long tA = System.currentTimeMillis();
        System.out.println("Thread_1.");
        //создаем  и наполняем массив
        float[] arr = createArray();
        System.out.println("T1.Array[" + (h - 1) + "]:" + arr[h - 1] + ", T1.Array[" + (size - 1) + "]:" + arr[size - 1]);
        //делим один массив на два(копируем данные из одного массива в два новых):
        //засекаем время
        long a = System.currentTimeMillis();
        float[] a1 = new float[h];
        float[] a2 = new float[h];
        //@param(массив-источник, откуда начинаем брать данные из массива-источника, массив-назначение, откуда начинаем записывать данные в массив-назначение, сколько ячеек копируем)
        System.arraycopy(arr, 0, a1, 0, h);
        System.arraycopy(arr, h, a2, 0, h);
        //выводим время выполнения метода наполнения массива
        System.out.println("T1.Splitting array time:" + (System.currentTimeMillis() - a));

        //Поток 1.
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                //засекаем время
                long a = System.currentTimeMillis();
                //перезаписываем массив
                for (int i = 0; i < a1.length; i++) {
                    a1[i] = (float)(a1[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
                }
                //calculating(a1);//только в одном потоке можно запустить этот метод
                //выводим время выполнения цикла перезаписи
                System.out.println("T1.Calculating a1 array time:" + (System.currentTimeMillis() - a));
            }
        });

        //Поток 2.
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                //засекаем время
                long a = System.currentTimeMillis();
                //перезаписываем массив
                //TODO! В формуле i заменяем (i + h), кроме индекса элемента массива,
                // чтобы вторая часть считалась правильно!
                for (int i = 0; i < a2.length; i++) {
                    a2[i] = (float)(a2[i] * Math.sin(0.2f + (i + h) / 5) * Math.cos(0.2f + (i + h) / 5) * Math.cos(0.4f + (i + h) / 2));
                }
                //выводим время выполнения цикла перезаписи
                System.out.println("T1.Calculating a2 array time:" + (System.currentTimeMillis() - a));
            }
        });
        //запускаем потоки
        t1.start();
        t2.start();
        //заставляем главный поток ждать наши потоки, чтобы не сшить недоделанные массивы
        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //сшиваем два массива в один(копируем данные последовательно):
        //засекаем время
        a = System.currentTimeMillis();
        //@param(массив-источник, откуда начинаем брать данные из массива-источника, массив-назначение, откуда начинаем записывать данные в массив-назначение, сколько ячеек копируем)
        System.arraycopy(a1, 0, arr, 0, h);
        System.arraycopy(a2, 0, arr, h, h);
        //выводим время выполнения метода наполнения массива
        System.out.println("T1.Collecting array time:" + (System.currentTimeMillis() - a));
        System.out.println("T1.Total testing time:" + (System.currentTimeMillis() - tA));
        System.out.println("T1.Array[" + (h - 1) + "]:" + arr[h - 1] + ", T1.Array[" + (size - 1) + "]:" + arr[size - 1]);
    }

    static void threadCalculating2(){
        long tA = System.currentTimeMillis();
        System.out.println("Thread_2.");
        //создаем  и наполняем массив
        float[] arr = createArray();
        System.out.println("T2.Array[" + (h - 1) + "]:" + arr[h - 1] + ", T2.Array[" + (size - 1) + "]:" + arr[size - 1]);
        //делим один массив на два(копируем данные из одного массива в два новых):
        //засекаем время
        long a = System.currentTimeMillis();
        float[] a1 = new float[h];
        float[] a2 = new float[h];
        //@param(массив-источник, откуда начинаем брать данные из массива-источника, массив-назначение, откуда начинаем записывать данные в массив-назначение, сколько ячеек копируем)
        System.arraycopy(arr, 0, a1, 0, h);
        System.arraycopy(arr, h, a2, 0, h);
        //выводим время выполнения метода наполнения массива
        System.out.println("T2.Splitting array time:" + (System.currentTimeMillis() - a));

        //Поток 1.
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                //засекаем время
                long a = System.currentTimeMillis();
                //перезаписываем первый массив, начиная с индекса 0
                calculating(a1, 0);//только в одном потоке можно запустить этот метод
                //выводим время выполнения цикла перезаписи
                System.out.println("T2.Calculating a1 array time:" + (System.currentTimeMillis() - a));
            }
        });

        //Поток 2.
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                //засекаем время
                long a = System.currentTimeMillis();
                //перезаписываем массив
                //TODO! В формуле i заменяем (i + h), кроме индекса элемента массива,
                // чтобы вторая часть считалась правильно!
                for (int i = 0; i < a2.length; i++) {
                    a2[i] = (float)(a2[i] * Math.sin(0.2f + (i + h) / 5) * Math.cos(0.2f + (i + h) / 5) * Math.cos(0.4f + (i + h) / 2));
                }
                //выводим время выполнения цикла перезаписи
                System.out.println("T2.Calculating a2 array time:" + (System.currentTimeMillis() - a));
            }
        });
        //запускаем потоки
        t1.start();
        t2.start();
        //заставляем главный поток ждать наши потоки, чтобы не сшить недоделанные массивы
        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //сшиваем два массива в один(копируем данные последовательно):
        //засекаем время
        a = System.currentTimeMillis();
        //@param(массив-источник, откуда начинаем брать данные из массива-источника, массив-назначение, откуда начинаем записывать данные в массив-назначение, сколько ячеек копируем)
        System.arraycopy(a1, 0, arr, 0, h);
        System.arraycopy(a2, 0, arr, h, h);
        //выводим время выполнения метода наполнения массива
        System.out.println("T2.Collecting array time:" + (System.currentTimeMillis() - a));
        System.out.println("T2.Total testing time:" + (System.currentTimeMillis() - tA));
        System.out.println("T2.Array[" + (h - 1) + "]:" + arr[h - 1] + ", T2.Array[" + (size - 1) + "]:" + arr[size - 1]);
    }

    static void threadCalculating3(){
        long tA = System.currentTimeMillis();
        System.out.println("Thread_3.");
        //создаем  и наполняем массив
        float[] arr = createArray();
        System.out.println("T3.Array[" + (h - 1) + "]:" + arr[h - 1] + ", T3.Array[" + (size - 1) + "]:" + arr[size - 1]);
        //делим один массив на два(копируем данные из одного массива в два новых):
        //засекаем время
        long a = System.currentTimeMillis();
        float[] a1 = new float[h];
        float[] a2 = new float[h];
        //@param(массив-источник, откуда начинаем брать данные из массива-источника, массив-назначение, откуда начинаем записывать данные в массив-назначение, сколько ячеек копируем)
        System.arraycopy(arr, 0, a1, 0, h);
        System.arraycopy(arr, h, a2, 0, h);
        //выводим время выполнения метода наполнения массива
        System.out.println("T3.Splitting array time:" + (System.currentTimeMillis() - a));

        //Поток 1.
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                //засекаем время
                long a = System.currentTimeMillis();
                //перезаписываем первый массив, начиная с индекса 0
                calculating(a1, 0);//только в одном потоке можно запустить этот метод
                //выводим время выполнения цикла перезаписи
                System.out.println("T3.Calculating a1 array time:" + (System.currentTimeMillis() - a));
            }
        });

        //Поток 2.
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                //засекаем время
                long a = System.currentTimeMillis();
                //перезаписываем первый массив, начиная с индекса h
                calculating(a2, h);//только в одном потоке можно запустить этот метод
                //выводим время выполнения цикла перезаписи
                System.out.println("T3.Calculating a2 array time:" + (System.currentTimeMillis() - a));
            }
        });
        //запускаем потоки
        t1.start();
        t2.start();
        //заставляем главный поток ждать наши потоки, чтобы не сшить недоделанные массивы
        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //сшиваем два массива в один(копируем данные последовательно):
        //засекаем время
        a = System.currentTimeMillis();
        //@param(массив-источник, откуда начинаем брать данные из массива-источника, массив-назначение, откуда начинаем записывать данные в массив-назначение, сколько ячеек копируем)
        System.arraycopy(a1, 0, arr, 0, h);
        System.arraycopy(a2, 0, arr, h, h);
        //выводим время выполнения метода наполнения массива
        System.out.println("T3.Collecting array time:" + (System.currentTimeMillis() - a));
        System.out.println("T3.Total testing time:" + (System.currentTimeMillis() - tA));
        System.out.println("T3.Array[" + (h - 1) + "]:" + arr[h - 1] + ", T3.Array[" + (size - 1) + "]:" + arr[size - 1]);
    }

}
