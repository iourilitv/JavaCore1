package Lesson_5;

/**
 * Java Core. Продвинутый уровень.
 * Вебинар 03 июня 2019 MSK (UTC+3).
 * Урок 5. Многопоточность.
 * @author Artem Evdokimov.
 * Синхронизация через статику(класс).
 * При указании ключевого слова synchronized в объявлении статического метода в роли монитора
 * выступает класс, метод которого был вызван потоком.
 */
public class Example_SB_3 {
    public static void main ( String [] args ) {
        System . out . println ( "Start" );
        new Thread (() -> method ()). start ();
        new Thread (() -> method ()). start ();
    }
    public synchronized static void method () { // синхронизация по классу
        for ( int i = 0 ; i < 10 ; i ++) {
            System . out . println ( i );
            try {
                Thread . sleep ( 100 );
            } catch ( InterruptedException e ) {
                e . printStackTrace ();
            }
        }
    }
}
