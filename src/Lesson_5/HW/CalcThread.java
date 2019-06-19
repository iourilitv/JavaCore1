package Lesson_5.HW;

/**
 * Java Core. Продвинутый уровень. Версия 2.
 * Вебинар 03 июня 2019 MSK (UTC+3).
 * Урок 5. Многопоточность.
 * Home Work. Версия 2.
 * Реализовано тоже самое, что и в MainThreadV1,
 * но через ООП и для разного количества потоков вычислений
 * @author Yuriy Litvinenko.
 * Класс потоков вычислений
 */
public class CalcThread extends Thread{
    private float[] arr;//временный массив потока
    private int startIndex;//начальный индекс временного массива потока

    //конструктор для нескольких потоков
    public CalcThread(float[] arr, int startIndex) {
        this.arr = arr;
        this.startIndex = startIndex;
    }

    /**
     * Переназначаем метод run потока, чтобы сразу запустить вычисление
     */
    @Override
    public void run() {
        super.run();
        //засекаем время
        long a = System.currentTimeMillis();
        //перезаписываем первый массив, начиная с индекса 0
        calculating();//только в одном потоке можно запустить этот метод
        //выводим время выполнения цикла перезаписи
        System.out.println("Calculating array time:" + (System.currentTimeMillis() - a));
    }

    /**
     * Метод пересчитывает по формуле каждый элемент временного массива объекта потока
     */
    void calculating(){
        //коррекция формулы из-за разбивки массива на потоки
        //чтобы все потоки считались правильно, в формуле i(кроме индекса элемента массива)
        //заменяем на (i + h)
        for (int i = 0; i < this.arr.length; i++) {
            this.arr[i] = (float)(this.arr[i] * Math.sin(0.2f + (i + this.startIndex) / 5) * Math.cos(0.2f + (i + this.startIndex) / 5) * Math.cos(0.4f + (i + this.startIndex) / 2));
        }
    }

}
