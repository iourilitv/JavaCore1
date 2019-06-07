package Lesson_2.HW.MainTask;

/**
 * Java Core. Продвинутый уровень.
 * Вебинар 03 июня 2019 MSK (UTC+3).
 * Урок 2. eNum. Вложенные классы. Исключения.
 * Home Work. Main task.
 * @author Yuriy Litvinenko. Status: completed.
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
