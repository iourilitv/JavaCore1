package Lesson_2.HW.AdditionalTask;

/**
 * Java Core. Продвинутый уровень.
 * Вебинар 03 июня 2019 MSK (UTC+3).
 * Урок 2. eNum. Вложенные классы. Исключения.
 * Home Work. Additional Task. Status: completed.
 * @author Yuriy Litvinenko.
 * Требуется реализовать enum DayOfWeek, который будет представлять дни недели.
 * С его помощью необходимо решить задачу определения кол-ва рабочих часов
 *  до конца недели по заданному текущему дню.
 *  Возвращает кол-во оставшихся рабочих часов до конца недели по заданному текущему дню.
 *  Считается, что текущий день ещё не начался, и рабочие часы за него должны учитываться.
 * public class DayOfWeekMain {
 *  public static void main(final String[] args) {
 *   System.out.println(getWorkingHours(DayOfWeek.MONDAY));
 *   }
 */
public class Main {
    public static void main(final String[] args) {
        //Способ 1. С помощью добавления параметра в enum - номер дня в неделе.
        System.out.println(getWorkingHoursV1(DayOfWeek.WEDNESDAY));

        //Способ 2. Без добавления доп.параметра в enum.
        System.out.println(getWorkingHoursV2(DayOfWeek.WEDNESDAY));
    }

    /**
     * Способ 1. С помощью добавления параметра в enum - номер дня в неделе.
     * Метод суммирования рабочих часов до конца недели начиная с dayOfWeek
     * @param dayOfWeek - день недели, с которого суммируем
     * @return сумму рабочих часов оставщихся дней недели
     */
    static double getWorkingHoursV1(DayOfWeek dayOfWeek){
        double remainingWorkTimeSum = 0;
        //цикл начинаем с номера дня в неделе полученного объекта enum
        for (int i = dayOfWeek.getDayOfWeekNumber() - 1; i < dayOfWeek.values().length; i++) {
            remainingWorkTimeSum += DayOfWeek.values()[i].getWorkHours();
        }
        return remainingWorkTimeSum;
    }

    /**
     * Способ 2. Без добавления доп.параметра в enum.
     * Метод суммирования рабочих часов до конца недели начиная с dayOfWeek
     * @param dayOfWeek - день недели, с которого суммируем
     * @return сумму рабочих часов оставщихся дней недели
     */
    static double getWorkingHoursV2(DayOfWeek dayOfWeek){
        double remainingWorkTimeSum = 0;
        //цикл начинаем с индекса полученного объекта enum с помощью dayOfWeek.ordinal()
        for (int i = dayOfWeek.ordinal(); i < dayOfWeek.values().length; i++) {
            remainingWorkTimeSum += DayOfWeek.values()[i].getWorkHours();
        }
        return remainingWorkTimeSum;
    }
}
