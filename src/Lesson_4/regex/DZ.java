package Lesson_4.regex;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DZ {
}


class Password {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String password;
        System.out.println("Введите пароль");
        password = sc.next();
        try {
            passwordVerification(password);
        } catch (WrongPassword wrongPassword) {
            wrongPassword.printStackTrace();
        }
    }

    //В следующем методе проверим соответствие пороля предъявляемым требованиям.
    public static void passwordVerification(String password) throws WrongPassword {
        Pattern p = Pattern.compile("(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])+.{8,}");
        Matcher m = p.matcher(password);
        if (m.matches()) {
            System.out.println(m.matches());
        } else throw new WrongPassword("Неверно введен пароль");
    }
}

//Создадим исключение для ошибочного пароля.
class WrongPassword extends Exception {
    public WrongPassword(String message) {
        super(message);
    }
}