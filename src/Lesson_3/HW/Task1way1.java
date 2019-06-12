package Lesson_3.HW;

import java.util.*;

/**
 * Java Core. Продвинутый уровень.
 * Вебинар 03 июня 2019 MSK (UTC+3).
 * Урок 3. Коллекции.
 * Home Work.
 * @author Yuriy Litvinenko.
 * 1. Создать массив с набором слов (10-20 слов, среди которых должны встречаться
 * повторяющиеся). Найти и вывести список уникальных слов, из которых состоит массив
 * (дубликаты не считаем). Посчитать, сколько раз встречается каждое слово.
 * Способ 1. Через копирование массива в список и обработки с помощью итератора.
 * Недостаток - не сохраняются значения повторений слова в массиве, как в способе 2.
 */
public class Task1way1 {
    public static void main(String[] args) {
        //создаем тестовый массив строк с дубликатами
        String[] words = {"abc", "aaa", "abb", "acc", "abc",
                "cbb", "ccc", "bbb", "acc", "bab",
                "cbb", "cbc", "bab", "acc", "abc",
        };
        //выводим массив в консоль
        System.out.println(Arrays.toString(words));

        //создаем коллекцию емкостью равной длине массива
        ArrayList<String> wordsList = new ArrayList<>(words.length);

        //копируем массив в коллекцию
        //аналогично можно сделать циклом:
        // for (int i = 0; i < words.length; i++) {
        //    wordsList.add(i, words[i]);
        // }
        Collections.addAll(wordsList, words);

        //в цикле сравниваем текущий элемент с другими и суммируем количество повторений
        for (int i = 0; i < wordsList.size(); i++) {
            int num = 0;
            //устанавливаем итератор с начала списка
            ListIterator<String> iterator = wordsList.listIterator();
            //запоминаем текущий элемент
            String word = wordsList.get(i);
            //находим совпадения с текущим элементом и удаляем их из списка,
            // одновременно суммируя их количество
            while(iterator.hasNext()) {
                if (word == iterator.next()) {
                    num++;
                    iterator.remove();
                }
            }
            //добавляем текущий элемент(теперь он в списке встречается один раз)
            wordsList.add(i, word);

            //выводим в консоль текущий элемент и количество повторений
            System.out.print(word + ":" + num + " ");
            // Результат:
            // abc:3 aaa:1 abb:1 acc:3 cbb:2 ccc:1 bbb:1 bab:2 cbc:1
        }
        //урезаем список до его фактического размера
        wordsList.trimToSize();
    }

}
