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
public class MainThread {
    static final int size = 10000000;
    static final int h = size / 2;

    public static void main(String[] args) {
        //System.out.println(Arrays.toString(createArray()));
        simpleCalculating();
        threadCalculating();
    }

    static float[] createArray(){
        float[] arr = new float[size];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = 1;
        }
        return arr;
    }

    static void simpleCalculating(){
        //создаем  и наполняем массив
        //засекаем время
        long a = System.currentTimeMillis();
        float[] arr = createArray();
        //выводим время выполнения метода наполнения массива
        System.out.println("Simple.createArray():" + (System.currentTimeMillis() - a));

        //перезаписываем массив
        //засекаем время
        /*long */a = System.currentTimeMillis();
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (float)(arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
        }
        //выводим время выполнения цикла перезаписи
        System.out.println("simpleCalculating():" + (System.currentTimeMillis() - a));
    }

    static void threadCalculating(){
        //создаем  и наполняем массив
        //засекаем время
        long a = System.currentTimeMillis();
        float[] arr = createArray();
        //выводим время выполнения метода наполнения массива
        System.out.println("Thread.createArray():" + (System.currentTimeMillis() - a));

        //делим один массив на два(копируем данные из одного массива в два новых):
        //засекаем время
        a = System.currentTimeMillis();
        float[] a1 = new float[h];
        float[] a2 = new float[h];
        //@param(массив-источник, откуда начинаем брать данные из массива-источника, массив-назначение, откуда начинаем записывать данные в массив-назначение, сколько ячеек копируем)
        System.arraycopy(arr, 0, a1, 0, h);
        System.arraycopy(arr, h, a2, 0, h);
        //выводим время выполнения метода наполнения массива
        System.out.println("Thread.splitArray:" + (System.currentTimeMillis() - a));

        //Поток 1.
        //перезаписываем массив
        //засекаем время
        /*long */a = System.currentTimeMillis();
        for (int i = 0; i < a1.length; i++) {
            a1[i] = (float)(a1[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
        }
        //выводим время выполнения цикла перезаписи
        System.out.println("threadCalculating()a1:" + (System.currentTimeMillis() - a));

        //Поток 2.
        //перезаписываем массив
        //засекаем время
        /*long */a = System.currentTimeMillis();
        for (int i = 0; i < a2.length; i++) {
            a2[i] = (float)(a2[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
        }
        //выводим время выполнения цикла перезаписи
        System.out.println("threadCalculating()a2:" + (System.currentTimeMillis() - a));

        //сшиваем два массива в один(копируем данные последовательно):
        //засекаем время
        a = System.currentTimeMillis();
        //@param(массив-источник, откуда начинаем брать данные из массива-источника, массив-назначение, откуда начинаем записывать данные в массив-назначение, сколько ячеек копируем)
        System.arraycopy(a1, 0, arr, 0, h);
        System.arraycopy(a2, 0, arr, h, h);
        //выводим время выполнения метода наполнения массива
        System.out.println("Thread.collectArray:" + (System.currentTimeMillis() - a));
    }
}
