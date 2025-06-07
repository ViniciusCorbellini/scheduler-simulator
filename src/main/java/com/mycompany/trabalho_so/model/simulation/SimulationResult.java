package com.mycompany.trabalho_so.model.simulation;

import java.util.List;
import java.util.Map;
import com.mycompany.trabalho_so.model.task.Task;

/**
 * Classe modelo para o calculo de metricas
 * Obs: o calculo sera realizado pelo modulo stats
 * 
 * @author manoCorbas
 */
public class SimulationResult {
    Map<Task, Integer> turnaround_times;
    float turnaround_time_avg;
    Map<Task, Integer> waiting_times;
    float waiting_time_avg;
    Task highest_wt;
    Task lowest_wt;
    List<Task> starvation;
    List<Task> priority_invertion;

    public SimulationResult(Map<Task, Integer> turnaround_times, float turnaround_time_avg, Map<Task, Integer> waiting_times, float waiting_time_avg, Task highest_wt, Task lowest_wt, List<Task> starvation, List<Task> priority_invertion) {
        this.turnaround_times = turnaround_times;
        this.turnaround_time_avg = turnaround_time_avg;
        this.waiting_times = waiting_times;
        this.waiting_time_avg = waiting_time_avg;
        this.highest_wt = highest_wt;
        this.lowest_wt = lowest_wt;
        this.starvation = starvation;
        this.priority_invertion = priority_invertion;
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("SimulationResult {\n");

        sb.append("  Turnaround Times:\n");
        for (Map.Entry<Task, Integer> entry : turnaround_times.entrySet()) {
            sb.append("    ").append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
        }

        sb.append("  Turnaround Time Avg: ").append(turnaround_time_avg).append("\n");

        sb.append("  Waiting Times:\n");
        for (Map.Entry<Task, Integer> entry : waiting_times.entrySet()) {
            sb.append("    ").append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
        }

        sb.append("  Waiting Time Avg: ").append(waiting_time_avg).append("\n");

        sb.append("  Highest Waiting Time Task: ").append(highest_wt).append("\n");
        sb.append("  Lowest Waiting Time Task: ").append(lowest_wt).append("\n");

        sb.append("  Starvation Tasks:\n");
        for (Task t : starvation) {
            sb.append("    ").append(t).append("\n");
        }

        sb.append("  Priority Inversion Tasks:\n");
        for (Task t : priority_invertion) {
            sb.append("    ").append(t).append("\n");
        }

        sb.append("}");
        return sb.toString();
    }
    
    public Map<Task, Integer> getTurnaround_times() {return turnaround_times;}

    public void setTurnaround_times(Map<Task, Integer> turnaround_times) {this.turnaround_times = turnaround_times;}

    public float getTurnaround_time_avg() {return turnaround_time_avg;}

    public void setTurnaround_time_avg(float turnaround_time_avg) {this.turnaround_time_avg = turnaround_time_avg;}

    public Map<Task, Integer> getWaiting_times() {return waiting_times;}

    public void setWaiting_times(Map<Task, Integer> waiting_times) {this.waiting_times = waiting_times;}

    public float getWaiting_time_avg() {return waiting_time_avg;}

    public void setWaiting_time_avg(float waiting_time_avg) {this.waiting_time_avg = waiting_time_avg;}

    public Task getHighest_wt() {return highest_wt;}

    public void setHighest_wt(Task highest_wt) {this.highest_wt = highest_wt;}

    public Task getLowest_wt() {return lowest_wt;}

    public void setLowest_wt(Task lowest_wt) {this.lowest_wt = lowest_wt;}

    public List<Task> getStarvation() {return starvation;}

    public void setStarvation(List<Task> starvation) {this.starvation = starvation;}

    public List<Task> getPriority_invertion() {return priority_invertion;}

    public void setPriority_invertion(List<Task> priority_invertion) {this.priority_invertion = priority_invertion;}
}
