package Lesson_2;

public class MainEx {
    public static void main(String[] args) {

//        try {
//            int[] mass = {1,2,3};
//
//            int b = 10 / 0;
//
//            mass[99] = 10;
//
//        } catch (ArrayIndexOutOfBoundsException e) {
//            e.printStackTrace();
//
////            try {
////
////            } catch () {
////
////            }
//
//        } catch (ArithmeticException e) {
//            e.printStackTrace();
//        }

//        try {
//            FileInputStream file = new FileInputStream("1.txt");
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }

//        try (FileInputStream file = new FileInputStream("1.txt")) {
//            System.out.println("1");
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        //  throw new RuntimeException("Ошибка!");

       // System.out.println("END");
//        try {
//            sqrt(-10);
//
//        } catch (ArithmeticException e) {
//            e.printStackTrace();
//        } finally {
//            System.out.println(1);
//            System.out.println(2);
//        }
//        System.out.println("END");
        System.out.println(doSomeThing());
    }

    public static int doSomeThing() {
        try {
            return 1;
        }  finally {

        }
    }

    public static int sqrt(int n) {
        if (n > 0) {
            return n/2;
        }
        throw new ArithmeticException("нельзя отрицательное!");
    }

//    static void a() {
//        b();
//    }
//
//    static void b() {
//        c();
//    }
//
//    static void c() {
//        int a = 0;
//        int b = 10 / 0;
//    }
}
