package Lesson_1.HW.Marafon.Course;

import Lesson_1.HW.Marafon.Team.Competitor;

public class Cross extends Obstacle {
    int length;

    public Cross(int length) {
        this.length = length;
    }

    @Override
    public void doIt(Competitor competitor) {
        competitor.run(length);
    }
}