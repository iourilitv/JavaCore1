package Lesson_3.HW;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PhoneBook {
    private static Map<String, Subscriber> phoneBook = new HashMap<>();

    public static Map<String, Subscriber> getPhoneBook() {
        return phoneBook;
    }

    public static void add(String key, String... phones){
        String[] arr = phones;
        List<String> list = new ArrayList<>();
        for (int i = 0; i < arr.length; i++) {
            list.add(arr[i]);
        }
        phoneBook.put(key, new Subscriber(key, list));
    }

    public static List<String> get(String key){
        return phoneBook.get(key).getPhoneNumbers();
    }
}
