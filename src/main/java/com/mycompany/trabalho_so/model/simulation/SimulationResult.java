package com.mycompany.trabalho_so.model.simulation;

import com.mycompany.trabalho_so.model.task.TCB;
import java.util.List;
import java.util.Map;

/**
 * Classe modelo para o calculo de metricas
 * Obs: o calculo sera realizado pelo modulo stats
 * 
 * @author manoCorbas
 */
public class SimulationResult {
    private float utilization;
    private Map<TCB, Integer> waiting_times;
    private Map<Integer, Float> waiting_timesavg_by_id;
    private float waiting_time_avg;
    private Map<TCB, Integer> turnaround_times;
    private Map<Integer, Float> turnaround_timesavg_by_id;
    private float turnaround_time_avg;
    private int id_highest_wt_avg;
    private int id_lowest_wt_avg;
    private List<TCB> starvation;
    private List<TCB> missed_deadline;
    private Map<Integer, Float> deadline_miss_ratio;
    private boolean isSchedulable;

    public SimulationResult(float utilization, Map<TCB, Integer> turnaround_times
            , float turnaround_time_avg, Map<TCB, Integer> waiting_times
            , float waiting_time_avg, int id_highest_wt_avg, int id_lowest_wt_avg
            , List<TCB> starvation, List<TCB> missed_deadline
            , Map<Integer, Float> deadline_miss_ratio
            , Map<Integer, Float> waiting_timesavg_by_id
            , Map<Integer, Float> turnaround_timesavg_by_id) 
    {
        this.utilization = utilization;
        this.turnaround_times = turnaround_times;
        this.turnaround_time_avg = turnaround_time_avg;
        this.waiting_times = waiting_times;
        this.waiting_time_avg = waiting_time_avg;
        this.id_highest_wt_avg = id_highest_wt_avg;
        this.id_lowest_wt_avg = id_lowest_wt_avg;
        this.starvation = starvation;
        this.missed_deadline = missed_deadline;
        this.deadline_miss_ratio = deadline_miss_ratio;
        
        this.waiting_timesavg_by_id = waiting_timesavg_by_id;
        this.turnaround_timesavg_by_id = turnaround_timesavg_by_id;
    }

    public float getUtilization() {return utilization;}

    public void setUtilization(float utilization) {this.utilization = utilization;}
    
    public Map<TCB, Integer> getTurnaround_times() {return turnaround_times;}

    public void setTurnaround_times(Map<TCB, Integer> turnaround_times) {this.turnaround_times = turnaround_times;}

    public float getTurnaround_time_avg() {return turnaround_time_avg;}

    public void setTurnaround_time_avg(float turnaround_time_avg) {this.turnaround_time_avg = turnaround_time_avg;}

    public Map<TCB, Integer> getWaiting_times() {return waiting_times;}

    public void setWaiting_times(Map<TCB, Integer> waiting_times) {this.waiting_times = waiting_times;}

    public float getWaiting_time_avg() {return waiting_time_avg;}

    public void setWaiting_time_avg(float waiting_time_avg) {this.waiting_time_avg = waiting_time_avg;}

    public int getId_highest_wt_avg() {return id_highest_wt_avg;}

    public void setId_highest_wt_avg(int id_highest_wt_avg) {this.id_highest_wt_avg = id_highest_wt_avg;}

    public int getId_lowest_wt_avg() {return id_lowest_wt_avg;}

    public void setId_lowest_wt_avg(int id_lowest_wt_avg) {this.id_lowest_wt_avg = id_lowest_wt_avg;}

    public List<TCB> getStarvation() {return starvation;}

    public void setStarvation(List<TCB> starvation) {this.starvation = starvation;}

    public List<TCB> getMissed_deadline() {return missed_deadline;}

    public void setMissed_deadline(List<TCB> missed_deadline) {this.missed_deadline = missed_deadline;}

    public Map<Integer, Float> getDeadline_miss_ratio() {return deadline_miss_ratio;}

    public void setDeadline_miss_ratio(Map<Integer, Float> deadline_miss_ratio) {this.deadline_miss_ratio = deadline_miss_ratio;}

    public boolean isIsSchedulable() {return isSchedulable;}

    public void setIsSchedulable(boolean isSchedulable) {this.isSchedulable = isSchedulable;}

    public Map<Integer, Float> getWaiting_timesavg_by_id() {return waiting_timesavg_by_id;}

    public void setWaiting_timesavg_by_id(Map<Integer, Float> waiting_times_by_id) {this.waiting_timesavg_by_id = waiting_times_by_id;}

    public Map<Integer, Float> getTurnaround_timesavg_by_id() {return turnaround_timesavg_by_id;}

    public void setTurnaround_timesavg_by_id(Map<Integer, Float> turnaround_times_by_id) {this.turnaround_timesavg_by_id = turnaround_times_by_id;}
}
