package Lesson_2.HW.AdditionalTask;

enum DayOfWeek {
    MONDAY("Понедельник", 1, 8.0),
    TUESDAY("Вторник", 2, 7.5),
    WEDNESDAY("Среда", 3, 6.8),
    THURSDAY("Четверг", 4, 8.3),
    FRIDAY("Пятница", 5, 6),
    SATURDAY("Суббота", 6, 0),
    SUNDAY("Воскресенье", 7, 0);

    private String rus;
    private int dayOfWeekNumber;
    private double workHours;

    DayOfWeek(String rus, int dayOfWeekNumber, double workHours) {
        this.rus = rus;
        this.dayOfWeekNumber = dayOfWeekNumber;
        this.workHours = workHours;
    }

    public String getRus() {
        return rus;
    }

    public int getDayOfWeekNumber() {
        return dayOfWeekNumber;
    }

    public double getWorkHours() {
        return workHours;
    }

}
