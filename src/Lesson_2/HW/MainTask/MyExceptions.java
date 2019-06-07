package Lesson_2.HW.MainTask;

import Lesson_2.FactorialException;

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
    //private static int maxLength = 3;

    public static void testArraySizeException(
            String[][] stringArray, int maxArrayLength
                                              ) throws MyArraySizeException{

        if (stringArray.length > maxArrayLength) throw new MyArraySizeException
                (
                "Длина массива превышает " + maxArrayLength + "!"
                );
    }

    /*public static void testArrayDataException(String[][] stringArray) throws MyArrayDataException{
        if (stringArray.length > maxLength) throw new MyArrayDataException(
                "Элемент массива с индексом " + index + " не является числом!"
        );
    }*/

}
