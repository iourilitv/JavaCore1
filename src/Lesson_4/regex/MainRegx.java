package Lesson_4.regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Java Core. Продвинутый уровень.
 * Вебинар 03 июня 2019 MSK (UTC+3).
 * Урок 4.1. Регулярные выражения.
 * @author Artem Evdokimov.
 */
public class MainRegx {
    public static void main(String[] args) {

        System.out.println(test("1123132jaaava1123132"));

    }

    public static boolean test(String testString) {
        Pattern p = Pattern.compile("^.*[a-z]{10,20}.*$" );
        Matcher m = p.matcher(testString);

        return m.matches();
    }
}
