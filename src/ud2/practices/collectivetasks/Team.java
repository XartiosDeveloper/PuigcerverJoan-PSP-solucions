package ud2.practices.collectivetasks;

import java.util.ArrayList;
import java.util.List;

public class Team {
    private String name;
    private List<EmployeeThread> employees;
    private List<Task> unfinishedTasks;
    private List<Task> testingTasks;
    private List<Task> finishedTasks;

    public Team(String name) {
        this.name = name;
        employees = new ArrayList<>();
        unfinishedTasks = new ArrayList<>();
        testingTasks = new ArrayList<>();
        finishedTasks = new ArrayList<>();
    }

    public void addEmployee(EmployeeThread employee) {
        this.employees.add(employee);
        employee.setTeam(this);
    }

    public void addTask(String taskName, int taskDuration) {
        unfinishedTasks.add(new Task(taskName, taskDuration));
    }

    public List<EmployeeThread> getEmployees() {
        return employees;
    }

    public String getName() {
        return name;
    }

    public Task getNextTask(){
        /**
         * TODO:
         * Get next Task from unfinishedTasks. If all unfinishedTaks are done,
         * get next Task from testingTasks.
         * The task must be deleted from the list when retrieved.
         * If all Tasks are done and tested, return null.
         */

        return null;
    }

    public void addTestingTask(Task t){
        testingTasks.add(t);
    }
    public void addFinishedTask(Task t){
        finishedTasks.add(t);
    }
}
