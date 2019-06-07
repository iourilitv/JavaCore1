package Lesson_2.HW.MainTask;

public class Main {
    public static void main(String[] args) {
        int maxArrayLength = 4;
        String[][] stringsArray = {
                {"11", "12", "1a3", "14"},
                {"21", "22", "23", "24"},
                {"31", "32", "3/3", "34"},
                {"41", "42", "43", "44"}
        };

        int sum = MyExceptions.getArrayElementsSum(stringsArray, maxArrayLength);
        if(sum >= 0){
            System.out.println("Сумма элементов массива равна " + sum);
        }
    }
}
