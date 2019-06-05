package Lesson_1.Marafon;

public class Team {
    String teamName;
    Competitor[] competitors;//массив членов команды

    public Team(String teamName, Competitor... competitors) {
        this.teamName = teamName;
        this.competitors = competitors;
    }

    /**
     * Метод вывода результата марафона.
     */
    void showResults() {
        System.out.println("\nMarathon results.\nCompetitors which have passed marathon successfully:");
        for (Competitor c : competitors) {
            if (c.isOnDistance()){
                c.info();
            }
        }
    }

    /**
     * Метод вывода инфомации об участниках команды
     */
    void teamInfo() {
        int n = 1;
        System.out.println(this.teamName + " members:");
        for (Competitor c : competitors) {
            System.out.print(n++ + ". ");//выводим номер участника в команде по порядку
            c.info();
        }
        System.out.println();
    }
}
