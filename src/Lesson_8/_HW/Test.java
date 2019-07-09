package Lesson_8._HW;

public class Test {
    public static void main(String[] args) {
        String nickOfOwner = "nick1";
        String nameOfBlacklistTable = "nick1blacklist";
        String nickname = "nick3";

        //String sql = String.format("SELECT name_blacklists FROM main where nickname = '%s'", nickOfOwner);
        //String sql = String.format("INSERT INTO %s (nickname) VALUES ('%s')", nameOfBlacklistTable, nickname);
        //String sql = String.format("SELECT nickname FROM %s WHERE nickname = '%s'", nameOfBlacklistTable, nickname);
        String sql = String.format("DELETE FROM %s WHERE nickname = '%s'", nameOfBlacklistTable, nickname);

        System.out.println(sql);

        /*String nameOfBlacklist = nickOfOwner + "blacklist";
        //создаем новую таблицу для черного списка пользователя
        // формирование запроса. '%s' - для последовательного подставления значений в соотвествующее место
        String sql1 = String.format("CREATE TABLE %s ( nickname REFERENCES main (nickname) );", nameOfBlacklist);
        //добавляем имя таблицы черного списка в строку пользователя
        String sql2 = String.format("UPDATE main SET name_blacklists = '%s' WHERE nickname = 'nick1'", nameOfBlacklist);

        System.out.println(sql1 + sql2);*/
    }
}
