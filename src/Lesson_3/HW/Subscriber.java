package Lesson_3.HW;

import java.util.List;

public class Subscriber {//Абонент
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

}
