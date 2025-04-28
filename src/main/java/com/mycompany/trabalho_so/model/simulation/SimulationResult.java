/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.mycompany.trabalho_so.model.simulation;

import java.util.List;
import java.util.Map;
import com.mycompany.trabalho_so.model.task.Task;

/**
 * Classe modelo para o calculo de metricas
 * Obs: o calculo sera realizado na pasta stats
 * 
 * @author manoCorbas
 */
public class SimulationResult {
    Map<Task, Float> turnaround_times;
    float turnaround_time_avg;
    Map<Task, Float> waiting_times;
    float waiting_time_avg;
    Task highest_wt;
    Task lowest_wt;
    List<Task> starvation;
    List<Task> priority_invertion;
}
