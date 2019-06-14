package Lesson_3.DZ;

import java.util.HashMap;
import java.util.HashSet;

public class Main {
    public static void main(String[] args) {
//        String[] arr = {"Abc","Bcd","Abc","Tre"};
//        HashSet<String> hs= new HashSet<>();
//        for(String o: arr) {
//            hs.add(o);
//        }
//        System.out.println(hs);


//        String[] arr = {"Abc","Bcd","Abc","Tre"};
//        HashMap<String, Integer> hm = new HashMap<>();
//        for(String o: arr) {
//            hm.put(o, hm.getOrDefault(o,0) + 1);
//        }
//        System.out.println(hm);

















        Lesson_3.DZ.PhoneBox book = new Lesson_3.DZ.PhoneBox();
        book.add("Ivanov", "123");
        book.add("Ivanov", "124");
        book.add("Ivanov", "125");
        book.add("Petrov", "444");
        book.add("Petrov", "445");
        book.add("Petrov", "446");

        book.findString("Ivanov");
        book.findString("ivanov");
        book.findString("Petrov");
        book.findString("Petrasdov");

//        Map<String, String> hashmap = new TreeMap<>();
//
//        HashMap <String, String> hashmao =  new HashMap<>();
    }

}


class PhoneBox {
    HashMap<String, HashSet<String>> hm;

    public PhoneBox() {
        this.hm = new HashMap<>();
    }

    public void add(String name, String phone) {
        HashSet<String> hs = hm.getOrDefault(name, new HashSet<>());
        hs.add(phone);
        hm.put(name, hs);
    }

    public void findString(String name) {
        if(hm.containsKey(name)) {
            System.out.println(hm.get(name));
        } else {
            System.out.println("такой фамилии нет");
        }
    }
}
















//    private HashMap<String, ArrayList<String>> chm = new HashMap<>();
//
//
//    HashMap<String,String[]> book = new HashMap<>();