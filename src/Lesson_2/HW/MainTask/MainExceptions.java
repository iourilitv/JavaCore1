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
        //MyExceptions myE = new MyExceptions(stringsArray);
        try{
            MyExceptions.testArraySizeException(stringsArray, maxArrayLength);
        }
        catch (MyArraySizeException e){
            System.err.println(e.getMessage());
        }
    }
}
