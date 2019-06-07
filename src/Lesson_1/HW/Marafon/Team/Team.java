package Lesson_1.HW.Marafon.Team;

public class Team {
    String teamName;
    Competitor[] competitors;//массив членов команды

    public Team(String teamName, Competitor... competitors) {
        this.teamName = teamName;
        this.competitors = competitors;
    }

    /**
     * Геттер для инкапсуляции массива участников соревнований
     * @return
     */
    public Competitor[] getCompetitors() {
        Competitor[] c = new Competitor[competitors.length];
        for (int i = 0; i < competitors.length; i++) {
            c[i] = competitors[i];
        }
        return c;
    }

    /**
     * Метод вывода результата марафона.
     */
    public void showResults() {//TODO added public
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
    public void teamInfo() {//TODO added public
        int n = 1;
        System.out.println(this.teamName + " members:");
        for (Competitor c : competitors) {
            System.out.print(n++ + ". ");//выводим номер участника в команде по порядку
            c.info();
        }
        System.out.println();
    }
}
