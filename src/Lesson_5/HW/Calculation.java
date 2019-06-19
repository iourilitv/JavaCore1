package Lesson_5.HW;

/**
 * Java Core. Продвинутый уровень. Версия 2.
 * Вебинар 03 июня 2019 MSK (UTC+3).
 * Урок 5. Многопоточность.
 * Home Work. Версия 2.
 * Реализовано тоже самое, что и в MainThreadV1,
 * но через ООП и для разного количества потоков вычислений
 * @author Yuriy Litvinenko.
 * Класс запуска вычислений и распределения на потоки
 */
public class Calculation {
    static final int size = 10000000;//длина массива
    static final int h = size / 2;//половина длины массива
    private int numberOfPairsOfThreads;//количество пар потоков вычислений
    private int lengthOfArrayOfThread;//длина временного массива в потоке вычислений
    private CalcThread[] calcThreadsArray;//массив объектов потоков вычислений
    private float[][] arrayOfArraysOfThreads;//массив временных массивов в потоках вычислений

    public Calculation(int numberOfPairsOfThreads) {
        this.numberOfPairsOfThreads = numberOfPairsOfThreads;
        if(numberOfPairsOfThreads > 0){
            lengthOfArrayOfThread = h / numberOfPairsOfThreads;
            calcThreadsArray = new CalcThread[numberOfPairsOfThreads * 2];
            arrayOfArraysOfThreads = new float[numberOfPairsOfThreads * 2][];
        }
        else{
            lengthOfArrayOfThread = size;
            calcThreadsArray = new CalcThread[1];
            arrayOfArraysOfThreads = new float[1][];
        }
    }

    private float[] createArray(){
        //засекаем время
        long a = System.currentTimeMillis();
        //создаем  и наполняем массив единицами
        float[] arr = new float[size];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = 1;
        }
        //выводим время выполнения метода наполнения массива
        System.out.println("Creating array time:" + (System.currentTimeMillis() - a));
        return arr;
    }

    void threadCalculating(){
        //засекаем общее время вичислений
        long tA = System.currentTimeMillis();

        //выводим параметры теста
        System.out.println("Number Of Pairs Of Threads:" + numberOfPairsOfThreads + ". Array size:" + size);

        //создаем  и наполняем начальный массив
        float[] arr = createArray();
        System.out.println("arrayOfArraysOfThreads.length:" + arrayOfArraysOfThreads.length);

        //проверяем задан один поток или несколько
        if(numberOfPairsOfThreads > 0){
            //делим один массив на пары массивов(копируем данные из одного массива в новые):
            //и одновременно запускаем потоки рассчета
            for (int i = 0; i < arrayOfArraysOfThreads.length; i++) {
                //создаем временный массив потока
                arrayOfArraysOfThreads[i] = new float[lengthOfArrayOfThread];
                //засекаем время создания временного массива потока
                long a = System.currentTimeMillis();

                //копируем данные во временный массив потока из соотвествующей части начального массива
                //@param(массив-источник, откуда начинаем брать данные из массива-источника, массив-назначение, откуда начинаем записывать данные в массив-назначение, сколько ячеек копируем)
                System.arraycopy(arr,i * lengthOfArrayOfThread,
                        arrayOfArraysOfThreads[i], 0, lengthOfArrayOfThread);

                //выводим время, затраченное на создание временного массива потока
                System.out.println("Splitting of Array Of Thread time:" + (System.currentTimeMillis() - a));

                //выводим значение последнего элемента временного массива потока
                System.out.println("arrayOfArraysOfThreads[" + i + "][" + (lengthOfArrayOfThread - 1) + "]:" + arrayOfArraysOfThreads[i][lengthOfArrayOfThread - 1]);

                //создаем объект потока
                calcThreadsArray[i] = new CalcThread(arrayOfArraysOfThreads[i], i * lengthOfArrayOfThread);

                //запускаем поток
                calcThreadsArray[i].start();
            }
            //заставляем главный поток ждать наши потоки, чтобы не сшить недоделанные массивы
            try {
                for (CalcThread ct: calcThreadsArray) {
                    ct.join();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            //сшиваем два массива в один(копируем данные последовательно):
            arr = collectArr(arr);
        }
        //если поток один, создаем объект потока и запускаем вычисление, не запуская потока
        else {
            //new CalcThread().calculating(arr, 0);
            new CalcThread(arr, 0).calculating();
        }

        //выводим время, затраченное на весь тест
        System.out.println("Total testing time:" + (System.currentTimeMillis() - tA));

        //выводим пересчитанный массив
        System.out.println("Recalculated array:");
        for (int i = 0; i < arrayOfArraysOfThreads.length; i++) {
            System.out.println("arr[" + ((i + 1) * lengthOfArrayOfThread - 1) + "]:" + arr[(i + 1) * lengthOfArrayOfThread - 1]);
        }

    }

    float[] collectArr(float[] arr){
        //засекаем время
        long a = System.currentTimeMillis();

        //сшиваем два массива в один(копируем данные последовательно):
        for (int i = 0; i < arrayOfArraysOfThreads.length; i++) {
            System.out.println("arrayOfArraysOfThreads[" + i + "][" + (lengthOfArrayOfThread - 1) + "]:" + arrayOfArraysOfThreads[i][lengthOfArrayOfThread - 1]);

            //@param(массив-источник, откуда начинаем брать данные из массива-источника, массив-назначение, откуда начинаем записывать данные в массив-назначение, сколько ячеек копируем)
            System.arraycopy(arrayOfArraysOfThreads[i],0,
                arr,i * lengthOfArrayOfThread, lengthOfArrayOfThread);
        }

        //выводим время выполнения метода наполнения массива
        System.out.println("Collecting array time:" + (System.currentTimeMillis() - a));
        return arr;
    }

    /* Number pairs of threads:1.

     */
    /* Number pairs of threads:1.
        Total testing time:10541
        arr[4999999]:0.06320445
        arr[9999999]:0.06892343
     */
    /* Number pairs of threads:2.
        Total testing time:8646
        arr[2499999]:0.40236908
        arr[4999999]:0.06320445
        arr[7499999]:-0.2595895
        arr[9999999]:0.06892343
     */
    /* Number pairs of threads:4.
        Total testing time:7615
        arr[1249999]:-0.030879615
        arr[2499999]:0.40236908
        arr[3749999]:-0.36245885
        arr[4999999]:0.06320445
        arr[6249999]:0.25133097
        arr[7499999]:-0.2595895
        arr[8749999]:0.054710697
        arr[9999999]:0.06892343
     */
    /* Number pairs of threads:10.
        Total testing time:6327
        arr[499999]:0.31420892
        arr[999999]:0.35315186
        arr[1499999]:-0.3755505
        arr[1999999]:-0.26153952
        arr[2499999]:0.40236908
        arr[2999999]:0.17625602
        arr[3499999]:-0.40342155
        arr[3999999]:-0.09261693
        arr[4499999]:0.36962035
        arr[4999999]:0.06320445
        arr[5499999]:-0.3758264
        arr[5999999]:6.6908356E-4
        arr[6499999]:0.32528642
        arr[6999999]:-0.053686824
        arr[7499999]:-0.2595895
        arr[7999999]:0.08391999
        arr[8499999]:0.18609822
        arr[8999999]:-0.088938795
        arr[9499999]:-0.11295141
        arr[9999999]:0.06892343
     */
}