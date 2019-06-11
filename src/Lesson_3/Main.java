package Lesson_3;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
/**
 * Java Core. Продвинутый уровень.
 * Вебинар 03 июня 2019 MSK (UTC+3).
 * Урок 3. Коллекции.
 * @author Artem Evdokimov.
 */
public class Main {
    public static void main(String[] args) {
//        int[] arr = {1,2,3,4,5};
//
//        System.out.println(Arrays.toString(arr));
//        int[] arr2 = new int[20];
//
//        System.arraycopy(arr,0, arr2, 0, arr.length);
//
//        System.out.println(Arrays.toString(arr2));

//        ArrayList<Integer> ai = new ArrayList<>(10000);
//
//        System.out.println(ai.size());
//
//        ai.add(1);
//        ai.add(2);
//        ai.add(2);
//        ai.add(2);
//        ai.add(2);
//        ai.add(3);
//        ai.add(4);
//        ai.add(null);
//
//        ai.set(5, 20);
//
//        System.out.println(ai);
//        ai.trimToSize();
//
//        ai.remove((Integer) 2);
//
//        System.out.println(ai.size());
//
//        System.out.println(ai.get(0));

//        if (ai.contains(20)) {
//            System.out.println("есть 20");
//        }


//        ArrayList<String> states = new ArrayList<>();
//        states.add("Германия");
//        states.add("Францию");
//        states.add("Францию");
//        states.add("Францию");
//        states.add("США");
//        states.add("Россия");
//
//        Iterator<String> iter = states.iterator();
//
//        while (iter.hasNext()) {
//            if(iter.next().equalsIgnoreCase("Францию")) {
//                iter.remove();
//            }
//        }

//        for (int i = 0; i < states.size(); i++) {
//            if(states.get(i).equalsIgnoreCase("Францию")) {
//                states.remove(i);
//            }
//        }

    //    System.out.println(states);


//        LinkedList<String> ll = new LinkedList();
//
//        ll.add("a");
//        ll.addLast("b");
//        ll.addFirst("b");
//        ll.removeFirst();
//        ll.removeLast();


//        HashMap<String, Integer> hm = new HashMap<>();
//
//        hm.put("Васька", 5);
//        hm.put("Васька", 5);
//        hm.put("Мурзик", 3);
//        hm.put("Рыжик", 6);
//        hm.put("Барсик", 8);
//
//        System.out.println(hm);

//        Random random = new Random();
//       HashMap<Integer, ArrayList<String>> hm = new HashMap<>();
//
//        for (int i = 0; i < 100; i++) {
//            int num = random.nextInt(10);
//            Integer res = hm.get(num);
//            hm.put(num, res == null ? 1 : res + 1);
//        }
//
//        System.out.println(hm);


//        HashSet<String> hs = new HashSet<>();
//
//        hs.add("B");
//        hs.add("A");
//        hs.add("A");
//        hs.add("D");
//        hs.add("C");
//        hs.add("Z");
//
//        System.out.println(hs);

//        ts.add("T");
//        ts.add("R");
//        ts.add("D");
//        ts.add("C");
//        ts.add("B");
//        ts.add("A");
//
//        System.out.println(ts);

//        TreeSet<Empl> ts = new TreeSet<>(new MySalary());
//
//        ts.add(new Empl("Ram", 2000));
//        ts.add(new Empl("Sofia", 1000));
//        ts.add(new Empl("Cris", 3000));
//        ts.add(new Empl("Ivan", 5000));
//
//        for (Empl e: ts) {
//            e.info();
//        }


        Map<Integer, String> hm = init();
        System.out.println(hm.getOrDefault(771, "default!"));


    }

    static HashMap<Integer, String> init() {
        HashMap<Integer, String> hm = new HashMap<>();
        hm.put(771, "Test1");
        hm.put(772, "Test2");
        return hm;
    }
}

class MySalary implements Comparator<Empl> {

    @Override
    public int compare(Empl o1, Empl o2) {
        if (o1.getSalary() > o2.getSalary()) {
            return -1;
        } else {
            return 1;
        }
    }
}

class Empl {
    private String name;

    public int getSalary() {
        return salary;
    }

    private int salary;

    public Empl(String name, int salary) {
        this.name = name;
        this.salary = salary;
    }

    public void info() {
        System.out.println(name + " " + salary);
    }
}


class MainBox {
    public static void main(String[] args) {
        Box box1 = new Box("red", 10);
        Box box2 = new Box("red", 10);

        System.out.println(box1.hashCode());
        System.out.println(box2.hashCode());
        System.out.println(box2.equals(box1));
    }
}

class Box {
    String color;
    int size;

    public Box(String color, int size) {
        this.color = color;
        this.size = size;
    }

    @Override
    public boolean equals(Object obj) {
        Box box = (Box)obj;
        return (this.size == box.size) ? true : false;
    }

}

















