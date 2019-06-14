package Lesson_3.HW;

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
 * Устранить замечания.
 * 1. Реализовать PhomeBook через ООП.
 * 2. Заменить ArrayList на HashSet, чтобы исключить дубликаты телефонов
 * 3. Использовать getOrDefault метод для добавления нового и изменения существующего абонента
 * 4. Переписать метод get, так чтобы возвращались данные об абоненте, иначе - сообщение, что абонента нет.
 */
public class Task2 {
    public static void main(String[] args) {
        //добавляем первого абонента в телефонный справочник
        //TODO improvement 1.Удалил
        //PhoneBook.add("Ivanov", "900-909092", "12121234455", "485743537");
        //System.out.println(PhoneBook.getPhoneBook().get("Ivanov").getLastName() + ": " + PhoneBook.get("Ivanov"));
        //TODO improvement 1.Добавил
        PhoneBook phoneBook = new PhoneBook();
        phoneBook.add("Ivanov", "900-909092", "12121234455", "485743537");
        System.out.println(phoneBook.getPhoneBook().get("Ivanov").getLastName() + ": " + phoneBook.get("Ivanov"));
        //Результат:
        //Ivanov: [900-909092, 12121234455, 485743537]

        //изменяем телефоны у первого абонента в телефонном справочнике
        //TODO improvement 1.Удалил
        //PhoneBook.add("Ivanov", "900-909092", "12121234455");
        //System.out.println(PhoneBook.getPhoneBook().get("Ivanov").getLastName() + ": " + PhoneBook.get("Ivanov"));
        //TODO improvement 1.Добавил
        phoneBook.add("Ivanov", "900-909092", "12121234455");
        System.out.println(phoneBook.getPhoneBook().get("Ivanov").getLastName() + ": " + phoneBook.get("Ivanov"));
        //Результат:
        //Ivanov: [900-909092, 12121234455]

        //добавляем первого абонента в телефонный справочник
        //TODO improvement 1.Удалил
        //PhoneBook.add("Petrov", "122434", "097654");
        //System.out.println(PhoneBook.getPhoneBook().get("Petrov").getLastName() + ": " + PhoneBook.get("Petrov"));
        //TODO improvement 1.Добавил
        phoneBook.add("Petrov", "122434", "097654");
        System.out.println(phoneBook.getPhoneBook().get("Petrov").getLastName() + ": " + phoneBook.get("Petrov"));
        //Результат:
        //Petrov: [122434, 097654]

        //выводим в консоль весь телефонный справочник
        //TODO improvement 1.Удалил
        //PhoneBook.phoneBookInfo();
        //TODO improvement 1.Добавил
        phoneBook.phoneBookInfo();
        //Результат:
        //Phone book.
        //LastName:Petrov. Phones:122434, 097654.
        //LastName:Ivanov. Phones:900-909092, 12121234455.
    }

}
