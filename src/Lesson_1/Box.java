package Lesson_1;

import java.util.Arrays;

/**
 * Java Core. Продвинутый уровень.
 * Вебинар 03 июня 2019 MSK (UTC+3).
 * Урок 1. Объектно-ориентированное программирование Java.
 * @author Artem Evdokimov.
 */
public class Box {
    private String color;
    private int weight;
    private String name;
    private String[] names = {"Boris", "Vlad", "Sofia"};

    public String[] getNames() {
        String[] mass = new String[names.length];

        for (int i = 0; i < names.length; i++) {
            mass[i] = names[i];
        }

        return mass;
    }

    public Box() {
    }

    public Box(String color, int weight, String name) {
        this.color = color;
        this.weight = weight;
        this.name = name;
    }

    public Box(String color, int weight) {
        this.color = color;
        this.weight = weight;
    }

    public void info() {
        System.out.println(color + " " + weight + " " + name + " " + Arrays.toString(names));
    }

    void startTest(Tools tools) {
        tools.infoToolsAndBox(this);
    }
}

class Tools {
    String tools;

    public Tools(String tools) {
        this.tools = tools;
    }

    void infoToolsAndBox(Box box) {
        box.info();
    }
}


class MainBox {
    public static void main(String[] args) {

        Tools tools = new Tools("1");
        tools.tools ="asd";
//        Box box1 = new Box("red", 10);
//        String[] mass = box1.getNames();
//
//        mass[0] = "Vaska";
//
//        System.out.println(Arrays.toString(mass));
//
//        box1.info();

//        Box box1 = new Box("red", 10);
//        box1.info();
        //  met1(1,2,3,4,5,6,10,55,66);
//
//        Box[] boxes = new Box[2];
//
//        boxes[0] = new Box();
//        boxes[1] = new Box();
//
//        boxes[0].name = "box1";
//        boxes[1].name = "box2";
//
//        boxes[0].info();
//        boxes[1].info();

//        Box box = new Box("red", 10);
//        Tools tools = new Tools("tools1");
//
//        box.startTest(tools);

    }

//    static void met1(int a, int b) {
//        System.out.println(a + b);
//    }

//    static void met1(int n, int... mass) {
//        int res = 0;
//
//        for (int i = 0; i < mass.length; i++) {
//            res += mass[i];
//        }
//
//        System.out.println(res);
//    }
}

interface IGame {
    void run();
    void jump();
}


abstract class Animal {
    private int a;
    protected int z;

    public Animal(int a) {
        this.a = a;
    }

    void info() {
        System.out.println("Звук животного!");
    }
}

class Cat extends Animal implements IGame{

    protected int y;
  //  protected int z;

    public Cat(int a, int y) {
        super(a);
        this.y = y;
    }

    void test() {
        super.z = 10;
    }

    @Override
    void info() {
        super.info();
        System.out.println("Звук кота!");
    }

    @Override
    public void run() {

    }

    @Override
    public void jump() {

    }
}

class SuperCat extends Cat {
    protected int u;

    public SuperCat(int a, int y, int u) {
        super(a, y);
        this.u = u;
    }

    void test() {
        super.z = 10;
    }

    @Override
    void info() {
        System.out.println("Рык кота!");
    }
}

class MainCat {
    public static void main(String[] args) {

        final int a = 5;

        Animal[] mass = {new Cat(10,20), new SuperCat(10,20,30)};

        for (int i = 0; i < mass.length; i++) {
            mass[i].info();
        }

    }
}





















