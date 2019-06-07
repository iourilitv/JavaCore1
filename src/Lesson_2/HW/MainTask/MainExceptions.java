package Lesson_2.HW.MainTask;

public class MainExceptions {
    public static void main(String[] args) {
        int maxArrayLength = 3;
        String[][] stringsArray = {
                {"11", "12", "13", "14"},
                {"21", "22", "23", "24"},
                {"31", "32", "3/3", "34"},
                {"41", "42", "43", "44"}
        };

        int sum = MyExceptions.getArrayElementsSum(stringsArray, maxArrayLength);
        if(sum >= 0){
            System.out.println("Сумма элементов массива равна " + sum);
        }
        /*try{
            MyExceptions.testArraySizeException(stringsArray, maxArrayLength);
        }
        catch (MyArraySizeException e){
            System.err.println(e.getMessage());
        }

        try{
            MyExceptions.testArrayDataException(stringsArray);
        }
        catch (MyArrayDataException e){
            System.err.println(e.getMessage());
        }*/
    }
}
