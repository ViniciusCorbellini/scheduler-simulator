package com.mycompany.trabalho_so.model.simulation;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import com.mycompany.trabalho_so.model.task.Task;

/**
 *
 * @author manoCorbas
 */
public class SimulationConfig {

    private int simulation_time;
    private String scheduler_name;
    private int tasks_number;
    private List<Task> tasks;

    @JsonCreator
    public SimulationConfig(
            @JsonProperty("simulation_time") int simulation_time,
            @JsonProperty("scheduler_name") String scheduler_name,
            @JsonProperty("tasks_number") int tasks_number,
            @JsonProperty("tasks") List<Task> tasks
    ) {
        this.simulation_time = simulation_time;
        this.scheduler_name = scheduler_name;
        this.tasks_number = tasks_number;
        this.tasks = tasks;
    }

    public int getSimulation_time() {
        return simulation_time;
    }

    public void setSimulation_time(int simulation_time) {
        this.simulation_time = simulation_time;
    }

    public String getScheduler_name() {
        return scheduler_name;
    }

    public void setScheduler_name(String scheduler_name) {
        this.scheduler_name = scheduler_name;
    }

    public int getTasks_number() {
        return tasks_number;
    }

    public void setTasks_number(int tasks_number) {
        this.tasks_number = tasks_number;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    @Override
    public String toString() {
        return "SimulationConfig{" 
                + "\nsimulation_time: " + simulation_time 
                + "\n, scheduler_name: " + scheduler_name 
                + "\n, tasks_number: " + tasks_number 
                + "\n, tasks: " + tasks 
                + '}';
    }
}
