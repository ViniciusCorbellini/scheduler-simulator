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
    private float utilization;
    private Map<Task, Integer> waiting_times;
    private float waiting_time_avg;
    private Map<Task, Integer> turnaround_times;
    private float turnaround_time_avg;
    private Task highest_wt;
    private Task lowest_wt;
    private List<Task> starvation;
    private List<Task> missed_deadline;
    private Map<Integer, Float> deadline_miss_ratio;
    private boolean isSchedulable;

    public SimulationResult(float utilization, Map<Task, Integer> turnaround_times, float turnaround_time_avg, Map<Task, Integer> waiting_times, float waiting_time_avg, Task highest_wt, Task lowest_wt, List<Task> starvation, List<Task> missed_deadline, Map<Integer, Float> deadline_miss_ratio) {
        this.utilization = utilization;
        this.turnaround_times = turnaround_times;
        this.turnaround_time_avg = turnaround_time_avg;
        this.waiting_times = waiting_times;
        this.waiting_time_avg = waiting_time_avg;
        this.highest_wt = highest_wt;
        this.lowest_wt = lowest_wt;
        this.starvation = starvation;
        this.missed_deadline = missed_deadline;
        this.deadline_miss_ratio = deadline_miss_ratio;
    }

    public float getUtilization() {return utilization;}

    public void setUtilization(float utilization) {this.utilization = utilization;}
    
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

    public List<Task> getMissed_deadline() {return missed_deadline;}

    public void setMissed_deadline(List<Task> missed_deadline) {this.missed_deadline = missed_deadline;}

    public Map<Integer, Float> getDeadline_miss_ratio() {return deadline_miss_ratio;}

    public void setDeadline_miss_ratio(Map<Integer, Float> deadline_miss_ratio) {this.deadline_miss_ratio = deadline_miss_ratio;}

    public boolean isIsSchedulable() {return isSchedulable;}

    public void setIsSchedulable(boolean isSchedulable) {this.isSchedulable = isSchedulable;}
}
