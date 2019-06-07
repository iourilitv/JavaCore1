package Lesson_2;

public class Outer {

    private int outerVar;

    class Inner {
        private int innerVar;

        public Inner(int innerVar) {
            this.innerVar = innerVar;
        }

        void innerTest() {
            System.out.println(innerVar);
            System.out.println(outerVar);
        }
    }

    public Outer(int outerVar) {
        this.outerVar = outerVar;
    }

    void outerTest() {
      //  System.out.println(innerVar);
        System.out.println(outerVar);
    }
}


class MainOuterInner {
    public static void main(String[] args) {
       // Outer outer = new Outer(10);
        Outer.Inner inner = new Outer(10).new Inner(50);
    }
}
