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
        System.out.println(getWorkingHoursV1(DayOfWeek.WEDNESDAY));
    }

    /**
     * Метод суммирования рабочих часов до конца недели начиная с dayOfWeek
     * @param dayOfWeek - день недели, с которого суммируем
     * @return сумму рабочих часов оставщихся дней недели
     */
    static double getWorkingHoursV1(DayOfWeek dayOfWeek){
        double remainingWorkTimeSum = 0;

        for (int i = dayOfWeek.getDayOfWeekNumber() - 1; i < dayOfWeek.values().length; i++) {
            remainingWorkTimeSum += DayOfWeek.values()[i].getWorkHours();
        }
        return remainingWorkTimeSum;
    }
}
