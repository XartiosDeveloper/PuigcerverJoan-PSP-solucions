package ud2.practices.tasks.solution;

public class ManagerThread extends Thread{
    private Team team;

    public ManagerThread(Team team) {
        super();
        this.team = team;
        this.setName(String.format("%sManager", team.getName()));
    }

    @Override
    public void run() {
        // TODO: Make all your assigned employees do their tasks
        for(EmployeeThread e : team.getEmployees())
            e.start();

        for(EmployeeThread e : team.getEmployees()) {
            try {
                e.join();
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            }
        }

        System.out.printf("%s: L'equip %s ha realitzat totes les tasques.\n", this.getName(), team.getName());
    }
}
