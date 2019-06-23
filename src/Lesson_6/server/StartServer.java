package Lesson_6.server;

/**
 * Обманка, чтобы вместо статичного класса Main создаем объектовый класс
 */
public class StartServer {
    //TODO переносим из Main, чтобы обращаться к нему как к объекту
    public static void main(String[] args) {
        new Main();
    }
}
