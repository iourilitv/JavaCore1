package Lesson_3.HW;

/**
 * Java Core. Продвинутый уровень.
 * Вебинар 03 июня 2019 MSK (UTC+3).
 * Урок 3. Коллекции.
 * Home Work.
 * @author Yuriy Litvinenko.
 * 2. Написать простой класс ТелефонныйСправочник, который хранит в себе список фамилий и
 * телефонных номеров. В этот телефонный справочник с помощью метода add() можно добавлять
 * записи. С помощью метода getSubscriberPhones() искать номер телефона по фамилии. Следует учесть, что
 * под одной фамилией может быть несколько телефонов, тогда при запросе такой фамилии
 * должны выводиться все телефоны.
 * Желательно как можно меньше добавлять своего, чего нет в задании (т.е. не надо в
 * телефонную запись добавлять еще дополнительные поля (имя, отчество, адрес), делать
 * взаимодействие с пользователем через консоль и т.д.).
 * Консоль желательно не использовать (в том числе Scanner), тестировать просто из метода
 * main(), прописывая add() и getSubscriberPhones().
 * Устранил замечания.
 * 1. Реализовал PhomeBook через ООП.
 * 2. Заменил ArrayList на HashSet, чтобы исключить дубликаты телефонов
 * 3. !!!Использовать getOrDefault метод для добавления нового и изменения существующего абонента.
 *    Не реализуемо здесь, т.к. у меня абонент - это объект Subscriber, а не HashSet.
 * 4. Переписать метод getSubscriberPhones, так чтобы возвращались данные об абоненте,
 *    иначе - сообщение, что абонента нет. Реализовано двумя способами:
 *    4.1.New method phoneBook.findSubscriber.
 *    4.2.Turn method phoneBook.get into phoneBook.getSubscriberPhones with Exception.
 */
public class Task2 {
    public static void main(String[] args) {
        //добавляем первого абонента в телефонный справочник
        //TODO improvement 1.Удалил
        //PhoneBook.add("Ivanov", "900-909092", "12121234455", "485743537");
        //System.out.println(PhoneBook.getPhoneBook().getSubscriberPhones("Ivanov").getLastName() + ": " + PhoneBook.getSubscriberPhones("Ivanov"));
        //TODO improvement 1.Добавил
        PhoneBook phoneBook = new PhoneBook();
        phoneBook.add("Ivanov", "900-909092", "12121234455", "485743537");

        //TODO improvement 4.Удалил
        //System.out.println(phoneBook.getPhoneBook().get("Ivanov").getLastName() + ": " + phoneBook.getSubscriberPhones("Ivanov"));
        //TODO improvement 4.1.New method phoneBook.findSubscriber.Добавил
        System.out.println("4.1.Ivano -> " + phoneBook.findSubscriber("Ivano"));
        //Результат:
        //4.1.Ivano -> There isn't this subscriber in the phone book!
        System.out.println("4.1.Ivanov -> " + phoneBook.findSubscriber("Ivanov"));
        //Результат:
        //4.1.Ivanov -> LastName:Ivanov. Phones:485743537, 900-909092, 12121234455.

        //TODO improvement 4.2.Turn method phoneBook.get into phoneBook.getSubscriberPhones with Exception.Добавил
        try{
            String key = "Ivanov";
            System.out.print("4.2.-> " + key + " phones: ");
            System.out.println(phoneBook.getSubscriberPhones(key));
        }
        catch(MyException e){
            System.err.println(e.getMessage());
        }
        //Результат:
        //4.2.-> Ivanov phones: [485743537, 900-909092, 12121234455]
        //4.2.-> Ivano phones: There isn't this subscriber in the phone book!

        //изменяем телефоны у первого абонента в телефонном справочнике
        //TODO improvement 1.Удалил
        //PhoneBook.add("Ivanov", "900-909092", "12121234455");
        //System.out.println(PhoneBook.getPhoneBook().getSubscriberPhones("Ivanov").getLastName() + ": " + PhoneBook.getSubscriberPhones("Ivanov"));
        //TODO improvement 1.Добавил
        phoneBook.add("Ivanov", "900-909092", "12121234455");

        //TODO improvement 4.1.New method phoneBook.findSubscriber.Удалил
        //System.out.println(phoneBook.getPhoneBook().getSubscriberPhones("Ivanov").getLastName() + ": " + phoneBook.getSubscriberPhones("Ivanov"));
        //TODO improvement 4.1.New method phoneBook.findSubscriber.Добавил
        System.out.println("4.1.Ivanov -> " + phoneBook.findSubscriber("Ivanov"));
        //Результат:
        //4.1.Ivanov -> LastName:Ivanov. Phones:900-909092, 12121234455.

        //добавляем первого абонента в телефонный справочник
        //TODO improvement 1.Удалил
        //PhoneBook.add("Petrov", "122434", "097654");
        //System.out.println(PhoneBook.getPhoneBook().getSubscriberPhones("Petrov").getLastName() + ": " + PhoneBook.getSubscriberPhones("Petrov"));
        //TODO improvement 1.Добавил
        phoneBook.add("Petrov", "122434", "097654");

        //TODO improvement 4.1.New method phoneBook.findSubscriber.Удалил
        //System.out.println(phoneBook.getPhoneBook().getSubscriberPhones("Petrov").getLastName() + ": " + phoneBook.getSubscriberPhones("Petrov"));
        //TODO improvement 4.1.New method phoneBook.findSubscriber.Добавил
        System.out.println("4.1.Petrov -> " + phoneBook.findSubscriber("Petrov"));
        //Результат:
        //4.1.Petrov -> LastName:Petrov. Phones:122434, 097654.

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
