package Lesson_3.HW;

import java.util.List;

public class PhoneNumbers {
    private String lastname;
    private List<String> phoneNumbers;

    public PhoneNumbers(String lastName, List<String> phoneNumbers) {
        this.lastname = lastName;
        this.phoneNumbers = phoneNumbers;
    }

    public String getLastname() {
        return lastname;
    }

    public List<String> getPhoneNumbers() {
        return phoneNumbers;
    }
}
