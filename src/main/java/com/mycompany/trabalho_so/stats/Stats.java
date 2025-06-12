/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.trabalho_so.stats;

import com.mycompany.trabalho_so.CPU.CPU;
import com.mycompany.trabalho_so.model.simulation.SimulationResult;
import com.mycompany.trabalho_so.model.task.TCB;
import com.mycompany.trabalho_so.model.task.Task;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Vinicius Corbellini
 */
public class Stats {

    public static SimulationResult calculate(ArrayList<TCB> tasks, CPU cpu, int time, List<TCB> finished) {
        float utilization = (float) cpu.getUtilization_time() / time;

        Map<Task, Integer> turnaround_times = getTurnaroundTimes(finished);
        float turnaround_time_avg = calculateTATavg(turnaround_times);

        Map<Task, Integer> waiting_times = getWaitingTimes(finished);
        float waiting_time_avg = calculateWTavg(waiting_times);

        Task highest_wt = getHighestWt(tasks);
        Task lowest_wt = getLowestWt(tasks);
        List<Task> starvation = checkForStarvation(tasks);
        List<Task> priority_invertion;

        //float utilization, Map<Task, Integer> turnaround_times, float turnaround_time_avg, Map<Task, Integer> waiting_times, float waiting_time_avg, Task highest_wt, Task lowest_wt, List<Task> starvation, List<Task> priority_invertion
        return new SimulationResult(utilization, turnaround_times, turnaround_time_avg, waiting_times, waiting_time_avg, highest_wt, lowest_wt, starvation, new ArrayList<>());
    }

    private static Map<Task, Integer> getTurnaroundTimes(List<TCB> tasks) {
        Map<Task, Integer> ans = new HashMap<>();

        for (TCB t : tasks) {
            ans.put(t, (t.isFinished() ? t.getTurnaround_time() : -1));
        }
        return ans;
    }

    private static float calculateTATavg(Map<Task, Integer> turnaround_times) {
        int count = 0, sum = 0;

        for (Map.Entry<Task, Integer> entry : turnaround_times.entrySet()) {
            int val = entry.getValue();

            if (val != -1) {
                count++;
                sum += val;
            }
        }
        if (count == 0) {
            return 0;
        }
        return (float) sum / count;
    }

    private static Map<Task, Integer> getWaitingTimes(List<TCB> tasks) {
        Map<Task, Integer> ans = new HashMap<>();

        for (TCB t : tasks) {
            ans.put(t, t.getWaiting_time());
        }
        return ans;
    }

    private static float calculateWTavg(Map<Task, Integer> waiting_times) {
        int sum = 0, size = waiting_times.size();
        for (Map.Entry<Task, Integer> entry : waiting_times.entrySet()) {
            int val = entry.getValue();

            sum += val;
        }
        if (size == 0) {return 0;}
        return (float) sum / size;
    }

    private static Task getHighestWt(List<TCB> tasks) {
        TCB highest = tasks.get(0);
        for (TCB t : tasks) {
            if (t.getWaiting_time() > highest.getWaiting_time()) {
                highest = t;
            }
        }
        return highest;
    }

    private static Task getLowestWt(List<TCB> tasks) {
        TCB lowest = tasks.get(0);
        for (TCB t : tasks) {
            if (t.getWaiting_time() < lowest.getWaiting_time()) {
                lowest = t;
            }
        }
        return lowest;
    }

    private static List<Task> checkForStarvation(List<TCB> tasks) {
        List<Task> ans = new ArrayList<>();
        for (TCB task : tasks) {
            if (task.isInStarvation()) {
                ans.add(task);
            }
        }
        return ans;
    }
}
