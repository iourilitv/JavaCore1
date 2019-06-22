package Lesson_5;

public class ABCTask {

    public static void main(String[] args) {
        Letter letter = new Letter();

        Thread tA = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                while (letter.getLetter() != "A") {
                    try {
                        synchronized (letter) {
                            letter.wait();
                        }
                    } catch (InterruptedException e) {
                        e.getMessage();
                    }
                }
                System.out.print(letter.getLetter());
                letter.setLetter("B");
                synchronized (letter) {
                    letter.notifyAll();
                }
            }
        }
        );

        Thread tB = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                while (letter.getLetter() != "B") {
                    try {
                        synchronized (letter) {
                            letter.wait();
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        e.getMessage();
                    }
                }
                System.out.print(letter.getLetter());
                letter.setLetter("C");
                synchronized (letter) {
                    letter.notifyAll();
                }
            }

        });

        Thread tC = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                while (letter.getLetter() != "C") {
                    try {
                        synchronized (letter) {
                            letter.wait();
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println(letter.getLetter());
                letter.setLetter("A");
                synchronized (letter) {
                    letter.notifyAll();
                }
            }
        });

        tA.start();
        tB.start();
        tC.start();
    }
}

class Letter {

    private String letter = "A";

    public String getLetter() {
        return letter;
    }

    public void setLetter(String letter) {
        this.letter = letter;
    }
}