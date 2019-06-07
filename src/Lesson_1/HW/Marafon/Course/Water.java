package Lesson_1.HW.Marafon.Course;

import Lesson_1.HW.Marafon.Team.Competitor;

public class Water extends Obstacle {
    int length;

    public Water(int length) {
        this.length = length;
    }

    @Override
    public void doIt(Competitor competitor) {
        competitor.swim(length);
    }
}