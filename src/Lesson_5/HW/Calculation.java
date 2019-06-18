package Lesson_5.HW;

public class Calculation {
    static final int size = 10000000;//(int)Math.pow(2, 24);//16777216 //было 10000000;
    static final int h = size / 2;
    private int numberOfPairsOfThreads;

    public Calculation(int numberOfPairsOfThreads) {
        this.numberOfPairsOfThreads = numberOfPairsOfThreads;
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

    private void calculating(float[] arr, int startIndex){
        //коррекция формулы из-за разбивки массива
        if(startIndex == 0){
            for (int i = 0; i < arr.length; i++) {
                arr[i] = (float)(arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
            }
        }
        else{
            //TODO! В формуле i заменяем (i + h), кроме индекса элемента массива,
            // чтобы вторая часть считалась правильно!
            for (int i = 0; i < arr.length; i++) {
                arr[i] = (float)(arr[i] * Math.sin(0.2f + (i + startIndex) / 5) * Math.cos(0.2f + (i + startIndex) / 5) * Math.cos(0.4f + (i + startIndex) / 2));
            }
        }
    }

    void threadCalculating(){
        long tA = System.currentTimeMillis();
        System.out.println("Number Of Pairs Of Threads:" + numberOfPairsOfThreads + ". Array size:" + size);
        //создаем  и наполняем массив
        float[] arr = createArray();
        System.out.println("Array[" + (h - 1) + "]:" + arr[h - 1] + ", Array[" + (size - 1) + "]:" + arr[size - 1]);
        //делим один массив на два(копируем данные из одного массива в два новых):
        //засекаем время
        long a = System.currentTimeMillis();
        float[] a1 = new float[h];
        float[] a2 = new float[h];
        //@param(массив-источник, откуда начинаем брать данные из массива-источника, массив-назначение, откуда начинаем записывать данные в массив-назначение, сколько ячеек копируем)
        System.arraycopy(arr, 0, a1, 0, h);
        System.arraycopy(arr, h, a2, 0, h);
        //выводим время выполнения метода наполнения массива
        System.out.println("Splitting array time:" + (System.currentTimeMillis() - a));

        //Поток 1.
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                //засекаем время
                long a = System.currentTimeMillis();
                //перезаписываем первый массив, начиная с индекса 0
                calculating(a1, 0);//только в одном потоке можно запустить этот метод
                //выводим время выполнения цикла перезаписи
                System.out.println("Calculating a1 array time:" + (System.currentTimeMillis() - a));
            }
        });

        //Поток 2.
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                //засекаем время
                long a = System.currentTimeMillis();
                //перезаписываем первый массив, начиная с индекса h
                calculating(a2, h);//только в одном потоке можно запустить этот метод
                //выводим время выполнения цикла перезаписи
                System.out.println("Calculating a2 array time:" + (System.currentTimeMillis() - a));
            }
        });
        //запускаем потоки
        t1.start();
        t2.start();
        //заставляем главный поток ждать наши потоки, чтобы не сшить недоделанные массивы
        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //сшиваем два массива в один(копируем данные последовательно):
        //засекаем время
        a = System.currentTimeMillis();
        //@param(массив-источник, откуда начинаем брать данные из массива-источника, массив-назначение, откуда начинаем записывать данные в массив-назначение, сколько ячеек копируем)
        System.arraycopy(a1, 0, arr, 0, h);
        System.arraycopy(a2, 0, arr, h, h);
        //выводим время выполнения метода наполнения массива
        System.out.println("Collecting array time:" + (System.currentTimeMillis() - a));
        System.out.println("Total testing time:" + (System.currentTimeMillis() - tA));
        System.out.println("Array[" + (h - 1) + "]:" + arr[h - 1] + ", Array[" + (size - 1) + "]:" + arr[size - 1]);
    }


}
