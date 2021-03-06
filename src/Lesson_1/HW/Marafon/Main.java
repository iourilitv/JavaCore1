package Lesson_1.HW.Marafon;

import Lesson_1.HW.Marafon.Course.Course;
import Lesson_1.HW.Marafon.Course.Cross;
import Lesson_1.HW.Marafon.Course.Wall;
import Lesson_1.HW.Marafon.Team.Cat;
import Lesson_1.HW.Marafon.Team.Dog;
import Lesson_1.HW.Marafon.Team.Human;
import Lesson_1.HW.Marafon.Team.Team;

/**
 * Java Core. Продвинутый уровень.
 * Вебинар 03 июня 2019 MSK (UTC+3).
 * Урок 1. Объектно-ориентированное программирование Java.
 * Home Work.
 * @author Yuriy Litvinenko.
 * 1. Разобраться с имеющимся кодом;
 * 2. Добавить класс Team, который будет содержать название команды, массив из четырех
 * участников (в конструкторе можно сразу указывать всех участников ), метод для вывода
 * информации о членах команды, прошедших дистанцию, метод вывода информации обо всех
 * членах команды.
 * 3. Добавить класс Course (полоса препятствий), в котором будут находиться массив
 * препятствий и метод, который будет просить команду пройти всю полосу;
 * 4. Разделить классы по соответствующим пакетам.
 * В итоге должно быть что-то вроде:
 * public static void main(String[] args) {
 *      Course c = new Course(...); // Создаем полосу препятствий
 *      Team team = new Team(...); // Создаем команду
 *      c.doIt(team); // Просим команду пройти полосу
 *      team.showResults(); // Показываем результаты
 *      }
 */
public class Main {
    /*public static void main(String[] args) {
        Competitor[] competitors = {new Human("Боб"), new Cat("Барсик"), new Dog("Бобик")};
        Obstacle[] course = {new Cross(80), new Wall(2), new Wall(1), new Cross(120)};
        for (Competitor c : competitors) {
            for (Obstacle o : course) {
                o.doIt(c);
                if (!c.isOnDistance()) break;
            }
        }
        for (Competitor c : competitors) {
            c.info();
        }
    }*/

    public static void main(String[] args) {
        /*Course c = new Course(
                new Cross(80), new Wall(2),
                new Wall(1), new Cross(120)
        ); // Создаем полосу препятствий

        Team team = new Team("Team1",
                new Human("Боб"), new Human("Иван"),
                new Cat("Барсик"), new Dog("Бобик")
        ); // Создаем команду
        team.teamInfo();//выводим информацию о команде

        c.doIt(team); // Просим команду пройти полосу
        team.showResults(); // Показываем результаты команды*/
        new Marathon();
    }
}