package Lesson_5.HW;

public class MainThreadV2 {
    //static final int size = (int)Math.pow(2, 24);//16777216 //было 10000000;
    //static final int h = size / 2;

    public static void main(String[] args) {
        //System.out.println(size);

        Calculation calc1 = new Calculation(1);
        calc1.threadCalculating();

    }
}
