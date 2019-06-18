package Lesson_5;

/**
 * Java Core. Продвинутый уровень.
 * Вебинар 03 июня 2019 MSK (UTC+3).
 * Урок 5. Многопоточность.
 * @author Artem Evdokimov.
 * Синхронизация через монитор, в качестве монитора используется объект.
 * В этом случае в роли монитора выступает объект lock1, соответственно, два потока смогут
 * параллельно выполнять первую часть метода method1(), однако в блок синхронизации в единицу
 * времени может зайти только один поток, так как захватывается монитор lock1.
 */
public class Example_SB_2 {
    private Object lock1 = new Object ();
    public static void main ( String [] args ) {
        Example_SB_2 e2 = new Example_SB_2 ();
        System . out . println ( "Start" );
        new Thread (() -> e2 . method1 ()). start ();
        new Thread (() -> e2 . method1 ()). start ();
    }
    public void method1 () {
        System . out . println ( "Block-1 begin" );
        for ( int i = 0 ; i < 3 ; i ++) {
            System . out . println ( i );
            try {
                Thread . sleep ( 100 );
            } catch ( InterruptedException e ) {
                e . printStackTrace ();
            }
        }
        System . out . println ( "Block-1 end" );
        synchronized ( lock1 ) {
            System . out . println ( "Synch block begin" );
            for ( int i = 0 ; i < 10 ; i ++) {
                System . out . println ( i );
                try {
                    Thread . sleep ( 100 );
                } catch ( InterruptedException e ) {
                    e . printStackTrace ();
                }
            }
            System . out . println ( "Synch block end" );
        }
        System . out . println ( "M2" );
    }
}
