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
    private int dayOfTheWeek;
    private double workHours;

    DayOfWeek(String rus, int dayOfTheWeek, double workHours) {
        this.rus = rus;
        this.dayOfTheWeek = dayOfTheWeek;
        this.workHours = workHours;
    }

    public String getRus() {
        return rus;
    }

    public int getDayOfTheWeek() {
        return dayOfTheWeek;
    }

    public double getWorkHours() {
        return workHours;
    }
}
