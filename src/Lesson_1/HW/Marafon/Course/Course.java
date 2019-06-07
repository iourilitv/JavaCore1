package Lesson_1.HW.Marafon.Course;

import Lesson_1.HW.Marafon.Team.Competitor;
import Lesson_1.HW.Marafon.Team.Team;

public class Course {
    Obstacle[] course;//массив препятствий

    public Course(Obstacle... course) {
        this.course = course;
    }

    /**
     * Метод прохождения участниками этапов марафона.
     * @param team - команда - участников марафона.
     */
    public void doIt(Team team){//TODO added public
        for (Competitor c : team.getCompetitors()) {//TODO было team.competitors
            for (Obstacle o : course) {
                o.doIt(c);
                if (!c.isOnDistance()) break;
            }
        }
    }

}
