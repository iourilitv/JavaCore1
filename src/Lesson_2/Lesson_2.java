package Lesson_2;

public class Lesson_2 {
    public static void main(String[] args) {

        for (Fruit o : Fruit.values()) {
            System.out.println(o + " " + o.getRus() + " " + o.getWeight());
        }
     //   System.out.println(Fruit.APPLE.ordinal());
    }
}

enum Fruit {
    ORANGE("Апельсин", 3), APPLE("Яблоко", 4), BANANA("Банан", 5), CHERRY("Вишня", 6);

    private String rus;
    private int weight;

    Fruit(String rus, int weight) {
        this.rus = rus;
        this.weight = weight;
    }

    public String getRus() {
        return rus;
    }

    public void setRus(String rus) {
        this.rus = rus;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }
}