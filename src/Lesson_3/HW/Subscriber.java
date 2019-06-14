package Lesson_3.HW;

import java.util.HashSet;
import java.util.List;
import java.util.Map;

//TODO improvement 1.Удалил
//public class Subscriber {//Абонент
//TODO improvement 1.Добавил
class Subscriber {//Абонент
    private String lastName;

    //TODO improvement 2.Удалил
    //private List<String> phoneNumbers;
    //TODO improvement 2.Добавил
    private HashSet<String> phoneNumbers;

    //TODO improvement 2.Удалил
    /*public Subscriber(String lastName, List<String> phoneNumbers) {
        this.lastName = lastName;
        this.phoneNumbers = phoneNumbers;
    }*/
    //TODO improvement 2.Добавил
    public Subscriber(String lastName, HashSet<String> phoneNumbers) {
        this.lastName = lastName;
        this.phoneNumbers = phoneNumbers;
    }

    public String getLastName() {
        return lastName;
    }

    //TODO improvement 2.Удалил
    /*public List<String> getPhoneNumbers() {
        return phoneNumbers;
    }*/
    //TODO improvement 2.Добавил
    public HashSet<String> getPhoneNumbers() {
        return phoneNumbers;
    }

    /**
     * Метод возвращающий данные абонента.
     * @return строку с данными абонента.
     */
    public String subscriberInfo(){
        //собираем буферную строку данных абонента
        StringBuilder msg = new StringBuilder();
        msg.append("LastName:" + lastName + ". Phones:");
        for (String s: phoneNumbers) {
            msg = msg.append(s + ", ");
        }
        //заменяем последние ", " на "."
        msg.replace(msg.length() - 2, msg.length() - 1, ".");
        //возвращаем обычную строку
        return msg.toString();
    }
}
