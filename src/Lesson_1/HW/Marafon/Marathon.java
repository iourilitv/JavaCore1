package Lesson_1.HW.Marafon;

import Lesson_1.HW.Marafon.Course.Course;
import Lesson_1.HW.Marafon.Course.Cross;
import Lesson_1.HW.Marafon.Course.Wall;
import Lesson_1.HW.Marafon.Team.Cat;
import Lesson_1.HW.Marafon.Team.Dog;
import Lesson_1.HW.Marafon.Team.Human;
import Lesson_1.HW.Marafon.Team.Team;

public class Marathon {

    public Marathon() {
        startMarathon();
    }

    void startMarathon() {
        Course c = new Course(
                new Cross(80), new Wall(2),
                new Wall(1), new Cross(120)
        ); // Создаем полосу препятствий

        Team team = new Team("Team1",
                new Human("Боб"), new Human("Иван"),
                new Cat("Барсик"), new Dog("Бобик")
        ); // Создаем команду
        team.teamInfo();//выводим информацию о команде

        c.doIt(team); // Просим команду пройти полосу
        team.showResults(); // Показываем результаты команды
    }
}
