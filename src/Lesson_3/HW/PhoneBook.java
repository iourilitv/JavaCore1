package Lesson_3.HW;

import java.util.*;

//TODO improvement 1.Удалил
//public class PhoneBook {
//TODO improvement 1.Добавил
class PhoneBook {
    //TODO improvement 1.Удалил
    //private static Map<String, Subscriber> phoneBook = new HashMap<>();//телефонный справочник
    //TODO improvement 1.Добавил
    Map<String, Subscriber> phoneBook;//телефонный справочник

    //TODO improvement 1.Добавил
    public PhoneBook() {
        this.phoneBook = new HashMap<>();
    }

    //TODO improvement 1.Удалил
    /*public static Map<String, Subscriber> getPhoneBook() {
        return phoneBook;
    }*/
    //TODO improvement 1.Добавил
    public Map<String, Subscriber> getPhoneBook() {
        return phoneBook;
    }

    /**
     * Метод для добавления новых абонентов и/или изменения записей существующих.
     * @param key - ключ (он же фамилия) абонента.
     * @param phones - массив телефонных номеров абонента.
     */
    //TODO improvement 1.Удалил
    /*public static void add(String key, String... phones){
        String[] arr = phones;
        List<String> list = new ArrayList<>();
        for (int i = 0; i < arr.length; i++) {
            list.add(arr[i]);
        }
        phoneBook.put(key, new Subscriber(key, list));
    }*/
    //TODO improvement 2.Удалил
    /*//TODO improvement 1.Добавил
    public void add(String key, String... phones){
        String[] arr = phones;
        List<String> list = new ArrayList<>();
        for (int i = 0; i < arr.length; i++) {
            list.add(arr[i]);
        }
        phoneBook.put(key, new Subscriber(key, list));
    }*/
    //TODO improvement 2.Добавил
    //TODO improvement 1.Добавил
    public void add(String key, String... phones){
        String[] arr = phones;
        HashSet<String> hs = new HashSet<>();
        for (int i = 0; i < arr.length; i++) {
            hs.add(arr[i]);
        }
        phoneBook.put(key, new Subscriber(key, hs));
    }

    /**
     * Метод возвращающий списочный массив телефонов абонента.
     * @param key - ключ (он же фамилия) абонента.
     * @return списочный массив телефонов абонента.
     */
    //TODO improvement 1.Удалил
    /*public static List<String> getSubscriberPhones(String key){
        return phoneBook.getSubscriberPhones(key).getPhoneNumbers();
    }*/
    //TODO improvement 2.Удалил
    /*//TODO improvement 1.Добавил
    public List<String> getSubscriberPhones(String key){
        return phoneBook.getSubscriberPhones(key).getPhoneNumbers();
    }*/
    //TODO improvement 4.Удалил
    /*//TODO improvement 2.Добавил
    //TODO improvement 1.Добавил
    public HashSet<String> getSubscriberPhones(String key){
        return phoneBook.getSubscriberPhones(key).getPhoneNumbers();
    }*/
    //TODO improvement 4.2.Turn method phoneBook.get into phoneBook.getSubscriberPhones with Exception.Добавил
    //TODO improvement 2.Добавил
    //TODO improvement 1.Добавил

    /**
     * Метод запроса телефонов абонента в телефонном справочнике.
     * @param key  - ключ (он же фамилия) абонента.
     * @return отражение(список) телефонов абонента.
     * @throws MyException - исключение, если абонета нет в телефонном справочнике.
     */
    public HashSet<String> getSubscriberPhones(String key) throws MyException{
        try{
            phoneBook.get(key).getPhoneNumbers();
        }
        catch(NullPointerException e){
            throw new MyException("There isn't this subscriber in the phone book!");
        }
        return phoneBook.get(key).getPhoneNumbers();
    }

    /**
     * TODO improvement 4.1.New method phoneBook.findSubscriber.Добавил
     * Метод запроса данных абонента в телефонном справочнике.
     * @param name - ключ (он же фамилия) абонента.
     * @return строку с данными абонента, либо сообщения, что такого абонента нет в списке.
     */
    public String findSubscriber(String name){
        String msg;
        if (phoneBook.containsKey(name)){
            msg = phoneBook.get(name).subscriberInfo();
        }
        else{
            msg = "There isn't this subscriber in the phone book!";
        }
        return msg;
    }

    /**
     * Метод вывода в консоль телефонного справочника.
     */
    //TODO improvement 1.Удалил
    /*public static void phoneBookInfo(){
        System.out.println("Phone book.");
        // Map.Entry - описывает пару (ключ - значение)
        // entrySet - возращает множество со значениями карты
        for (Map.Entry<String, Subscriber> phb : phoneBook.entrySet()) {
            //System.out.println(phb.getKey() + ": " + phb.getValue().getPhoneNumbers());
            //Результат.
            //... //Ivanov: [900-909092, 12121234455] //...

            System.out.println(phb.getValue().subscriberInfo());
            //Результат.
            //... //LastName:Ivanov. Phones:900-909092, 12121234455.//...
        }
    }*/
    //TODO improvement 1.Добавил
    public void phoneBookInfo(){
        System.out.println("Phone book.");
        // Map.Entry - описывает пару (ключ - значение)
        // entrySet - возращает множество со значениями карты
        for (Map.Entry<String, Subscriber> phb : phoneBook.entrySet()) {
            //System.out.println(phb.getKey() + ": " + phb.getValue().getPhoneNumbers());
            //Результат.
            //... //Ivanov: [900-909092, 12121234455] //...

            System.out.println(phb.getValue().subscriberInfo());
            //Результат.
            //... //LastName:Ivanov. Phones:900-909092, 12121234455.//...
        }
    }
}

/*
    //TODO Как перебрать(пролистать) Map.
    //Способ 1. Напрямую с использованием цикла foreach.

    Map<String, String> map = new HashMap<>();
    map.put("1", "Понедельник");
    map.put("2", "Вторник");
    map.put("3", "Среда");

     // Map.Entry - описываем пару (ключ - значение) "3=Среда" и т.п.
     // entrySet - возращает множество со значениями карты, т.е. [3=Среда, 2=Вторник, 1=Понедельник]


    for (Map.Entry<String, String> entry : map.entrySet()) {
        System.out.println("ID =  " + entry.getKey() + " День недели = " + entry.getValue());
    }

    System.out.println();

    //TODO Как перебрать(пролистать) Map.
    //Способ 2. С использованием итератора.
     // Iterator - интерфейс, для организации цикла для перебора коллекций
     // hasNext - true, если есть еще элементы
     // next - возвращает слудующий элемент

    Iterator<Map.Entry<String, String>> entries = map.entrySet().iterator();
    while (entries.hasNext()) {
        Map.Entry<String, String> entry = entries.next();
        System.out.println("ID = " + entry.getKey() + " День недели = " + entry.getValue());
    }

    System.out.println();

     // keySet - возвращает множество ключей

    for (String key : map.keySet()) {
        System.out.println("ID = " + key + ", День недели = " +  map.getSubscriberPhones(key));
     }
    System.out.println();

    //TODO Как перебрать(пролистать) Map.
    Способ 3. В Java8 все гораздо проще и без всяких циклов.

    Map<String, Integer> fruits = new HashMap<>();

    fruits.put("pineapple", 100);
    fruits.put("banana", 15);
    fruits.put("mango", 60);
    fruits.put("papaya", 20);
    fruits.put("orange", 25);
    fruits.put("lemon", 7);

    fruits.forEach((key, value) -> {
        System.out.println(key + " == " + value);
    });
 */
