package Lesson_3.HW;

import java.util.ArrayList;

/**
 * Java Core. Продвинутый уровень.
 * Вебинар 03 июня 2019 MSK (UTC+3).
 * Урок 3. Коллекции.
 * Home Work.
 * @author Yuriy Litvinenko.
 * 2. Написать простой класс ТелефонныйСправочник, который хранит в себе список фамилий и
 * телефонных номеров. В этот телефонный справочник с помощью метода add() можно добавлять
 * записи. С помощью метода get() искать номер телефона по фамилии. Следует учесть, что
 * под одной фамилией может быть несколько телефонов, тогда при запросе такой фамилии
 * должны выводиться все телефоны.
 * Желательно как можно меньше добавлять своего, чего нет в задании (т.е. не надо в
 * телефонную запись добавлять еще дополнительные поля (имя, отчество, адрес), делать
 * взаимодействие с пользователем через консоль и т.д.).
 * Консоль желательно не использовать (в том числе Scanner), тестировать просто из метода
 * main(), прописывая add() и get().
 *
 */
public class Task2 {
    public static void main(String[] args) {

        PhoneBook.add("Ivanov", "900-909092", "12121234455", "485743537");
        System.out.println(PhoneBook.getPhoneBook().get("Ivanov").getLastName() + ": " + PhoneBook.get("Ivanov"));

        PhoneBook.add("Ivanov", "900-909092", "12121234455");
        System.out.println(PhoneBook.getPhoneBook().get("Ivanov").getLastName() + ": " + PhoneBook.get("Ivanov"));

        PhoneBook.add("Petrov", "122434", "097654");
        System.out.println(PhoneBook.getPhoneBook().get("Petrov").getLastName() + ": " + PhoneBook.get("Petrov"));

        //Результат:
        //Ivanov: [900-909092, 12121234455, 485743537]
        //Ivanov: [900-909092, 12121234455]
        //Petrov: [122434, 097654]

    }

}
