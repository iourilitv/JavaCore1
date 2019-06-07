package Lesson_2.HW.MainTask;

/**
 * Java Core. Продвинутый уровень.
 * Вебинар 03 июня 2019 MSK (UTC+3).
 * Урок 2. eNum. Вложенные классы. Исключения.
 * Home Work. Main task.
 * @author Yuriy Litvinenko.
 * 1. Напишите метод, на вход которого подается двумерный строковый массив размером 4х4,
 *  при подаче массива другого размера необходимо бросить исключение MyArraySizeException.
 * 2. Далее метод должен пройтись по всем элементам массива, преобразовать в int, и
 * просуммировать. Если в каком-то элементе массива преобразование не удалось
 * (например, в ячейке лежит символ или текст вместо числа), должно быть брошено
 * исключение MyArrayDataException – с детализацией, в какой именно ячейке лежат
 * неверные данные.
 * 3. В методе main() вызвать полученный метод, обработать возможные
 * исключения MySizeArrayException и MyArrayDataException и вывести результат расчета.
*/
class MyExceptions {

    public static int getArrayElementsSum(String[][] stringsArray, int maxArrayLength){
        int arrayElementsSum = -1;
        try{
            testArraySizeException(stringsArray, maxArrayLength);
        }
        catch (MyArraySizeException e){
            System.err.println(e.getMessage());
        }

        try{
            testArrayDataException(stringsArray);
        }
        catch (MyArrayDataException e){
            System.err.println(e.getMessage());
        }

        return arrayElementsSum;
    }

    public static void testArraySizeException(
                            String[][] stringsArray, int maxArrayLength
                                              ) throws MyArraySizeException{

        if (stringsArray.length > maxArrayLength) throw new MyArraySizeException
                (
                "Длина массива превышает " + maxArrayLength + "!"
                );
        System.out.println("Everything is all wright!");
    }

    public static int testArrayDataException(String[][] stringsArray) throws MyArrayDataException{
        int arrayElementsSum = 0;
        int num;

        for (int i = 0; i < stringsArray.length; i++) {
            for (int j = 0; j < stringsArray.length; j++) {
                try{
                    num = Integer.parseInt(stringsArray[i][j]);
                    arrayElementsSum += num;
                }
                catch (NumberFormatException e){
                    throw new MyArrayDataException
                            (
                             "Элемент массива stringArray[" + i + "][" + j + "]:\""
                             + stringsArray[i][j] + "\" не является числом!"
                            );
                }
            }
        }
        return arrayElementsSum;
    }

}
