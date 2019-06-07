package Lesson_2;

public class FactorialException extends Exception {
    private int number;

    public FactorialException(String msg, int number) {
        super(msg + " " + number);
        this.number = number;
    }

    public int getNumber() {
        return number;
    }
}

class Factorial {
    public static int getFactorial(int num) throws FactorialException {
        int res = 1;

        if(num < 1) throw new FactorialException("Число не может быть меньше 1", num);

        for (int i = 1; i <= num ; i++) {
            res *= i;
        }

        return res;
    }
}

class MainFac {
    public static void main(String[] args) {
        try {
            int res = Factorial.getFactorial(-10);
            System.out.println(res);
        } catch (FactorialException e) {
           // e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }
}
