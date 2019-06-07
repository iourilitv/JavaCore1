package Lesson_2.HW.AdditionalTask;

/**
 * Java Core. Продвинутый уровень.
 * Вебинар 03 июня 2019 MSK (UTC+3).
 * Урок 2. eNum. Вложенные классы. Исключения.
 * Home Work. Additional Task.
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
        System.out.println(getWorkingHours(DayOfWeek.MONDAY));
    }

    static double getWorkingHours(DayOfWeek dayOfWeek){
        double remainingWorkTimeSum = 0;

        for (int i = dayOfWeek.getDayOfTheWeek(); i < dayOfWeek.values().length; i++) {
            remainingWorkTimeSum += dayOfWeek.getWorkHours();
        }
        return remainingWorkTimeSum;
    }
}
