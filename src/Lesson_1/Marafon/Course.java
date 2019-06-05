package Lesson_1.Marafon;

public class Course {
    Obstacle[] course;//массив препятствий

    public Course(Obstacle... course) {
        this.course = course;
    }

    /**
     * Метод прохождения участниками этапов марафона.
     * @param team - команда - участников марафона.
     */
    void doIt(Team team){
        for (Competitor c : team.competitors) {
            for (Obstacle o : course) {
                o.doIt(c);
                if (!c.isOnDistance()) break;
            }
        }
    }

}
