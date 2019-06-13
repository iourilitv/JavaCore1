package Lesson_3.HW;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Subscriber {
    private String lastName;
    private List<String> phoneNumbers;

    public Subscriber(String lastName, List<String> phoneNumbers) {
        this.lastName = lastName;
        this.phoneNumbers = phoneNumbers;
    }

    public String getLastName() {
        return lastName;
    }

    public List<String> getPhoneNumbers() {
        return phoneNumbers;
    }

    /*public static List<String> get(String key){
        return Task2.phoneBook.get(key).phoneNumbers;
    }*/
}
