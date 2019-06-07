package Lesson_1.HW.Marafon.Course;

import Lesson_1.HW.Marafon.Team.Competitor;

public class Wall extends Obstacle {
    int height;

    public Wall(int height) {
        this.height = height;
    }

    @Override
    public void doIt(Competitor competitor) {
        competitor.jump(height);
    }
}