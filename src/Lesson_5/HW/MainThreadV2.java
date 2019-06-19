package Lesson_5.HW;

/**
 * Java Core. Продвинутый уровень. Версия 2.
 * Вебинар 03 июня 2019 MSK (UTC+3).
 * Урок 5. Многопоточность.
 * Home Work. Версия 2.
 * Реализовано тоже самое, что и в MainThreadV1,
 * но через ООП и для разного количества потоков вычислений
 * @author Yuriy Litvinenko.
 */
public class MainThreadV2 {

    public static void main(String[] args) {
        //в параметре объекта указать количество пар потоков(0 - один поток)
        new Calculation(10).threadCalculating();
    }

}
