package Lesson_5;

/**
 * Java Core. Продвинутый уровень.
 * Вебинар 03 июня 2019 MSK (UTC+3).
 * Урок 5. Многопоточность.
 * @author Artem Evdokimov.
 * Синхронизация методов.
 * При указании ключевого слова synchronized в объявлении метода в роли монитора выступает
 * объект, у которого был вызван синхронизированный метод. То есть в приведённом примере
 * два потока не могут параллельно выполнять method1() и method2().
 */
public class Example_SB_1 {
    public static void main ( String [] args ) {
        Example_SB_1 e1 = new Example_SB_1 ();
        System . out . println ( "Start" );
        new Thread (() -> e1 . method1 ()). start ();
        new Thread (() -> e1 . method2 ()). start ();
    }
    public synchronized void method1 () {
        System . out . println ( "M1" );
        for ( int i = 0 ; i < 10 ; i ++) {
            System . out . println ( i );
            try {
                Thread . sleep ( 100 );
            } catch ( InterruptedException e ) {
                e . printStackTrace ();
            }
        }
        System . out . println ( "M2" );
    }
    public synchronized void method2 () {
        System . out . println ( "M1" );
        for ( int i = 0 ; i < 10 ; i ++) {
            System . out . println ( i );
            try {
                Thread . sleep ( 100 );
            } catch ( InterruptedException e ) {
                e . printStackTrace ();
            }
        }
        System . out . println ( "M2" );
    }
}
