package Lesson_2.HW.MainTask;

public class Main {
    public static void main(String[] args) {
        int maxArrayLength = 4;//максимальная длина массива
        int maxSubArrayLength = 4;//максимальная длина подмассива

        String[][] stringsArray = {
                {"11", "12", "13", "14", "55"},
                {"21", "22", "23", "24"},
                {"31", "32", "3/3", "34"},
                {"41", "42", "43", "44", "55"}
        };

        int sum = MyExceptions.getArrayElementsSum(stringsArray, maxArrayLength, maxSubArrayLength);
        if(sum >= 0){
            System.out.println("Сумма элементов массива равна " + sum);
        }
    }
}
