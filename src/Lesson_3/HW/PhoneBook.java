package Lesson_3.HW;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PhoneBook {
    private static Map<String, Subscriber> phoneBook = new HashMap<>();//телефонный справочник

    public static Map<String, Subscriber> getPhoneBook() {
        return phoneBook;
    }

    /**
     * Метод для добавления новых абонентов и/или изменения записей существующих.
     * @param key - ключ (он же фамилия) абонента.
     * @param phones - массив телефонных номеров абонента.
     */
    public static void add(String key, String... phones){
        String[] arr = phones;
        List<String> list = new ArrayList<>();
        for (int i = 0; i < arr.length; i++) {
            list.add(arr[i]);
        }
        phoneBook.put(key, new Subscriber(key, list));
    }

    /**
     * Метод возвращающий списочный массив телефонов абонента.
     * @param key - ключ (он же фамилия) абонента.
     * @return списочный массив телефонов абонента.
     */
    public static List<String> get(String key){
        return phoneBook.get(key).getPhoneNumbers();
    }

    /*public static void phoneBookInfo(){
        for (phoneBook pb: ) {

        }
    }*/
}
