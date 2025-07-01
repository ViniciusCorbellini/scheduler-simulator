package com.mycompany.trabalho_so.stats;

import com.mycompany.trabalho_so.CPU.CPU;
import com.mycompany.trabalho_so.model.simulation.SimulationResult;
import com.mycompany.trabalho_so.model.task.TCB;
import com.mycompany.trabalho_so.model.task.Task;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *
 * @author Vinicius Corbellini
 */
public class Stats {

    public static SimulationResult calculate(ArrayList<TCB> tasks, CPU cpu, int time, List<TCB> finished) {
        float utilization = (float) cpu.getUtilization_time() / time;

        Map<TCB, Integer> turnaround_times = getTurnaroundTimes(finished);
        float turnaround_time_avg = calculateTATavg(turnaround_times);

        Map<TCB, Integer> waiting_times = getWaitingTimes(finished);
        float waiting_time_avg = calculateWTavg(waiting_times);

        Task highest_wt = getHighestWt(tasks);
        Task lowest_wt = getLowestWt(tasks);

        List<TCB> starvation = checkForStarvation(tasks);
        List<TCB> missed_deadline = missedDeadlines(tasks);
        LinkedHashMap<Integer, Float> deadline_miss_ratio = calculateDeadlineMissRatio(tasks);

        sortSimulationResults(turnaround_times, waiting_times, starvation, missed_deadline, deadline_miss_ratio);
        
        return new SimulationResult(utilization, turnaround_times, turnaround_time_avg, waiting_times, waiting_time_avg, highest_wt, lowest_wt, starvation, missed_deadline, deadline_miss_ratio);
    }

    public static SimulationResult calculate(ArrayList<TCB> tasks, CPU cpu, int time, List<TCB> finished, boolean isSchedulable) {
        SimulationResult sr = calculate(tasks, cpu, time, finished);
        sr.setIsSchedulable(isSchedulable);
        return sr;
    }

    private static Map<TCB, Integer> getTurnaroundTimes(List<TCB> tasks) {
        Map<TCB, Integer> ans = new LinkedHashMap<>();

        for (TCB t : tasks) {
            ans.put(t, (t.isFinished() ? t.getTurnaround_time() : -1));
        }
        return ans;
    }

    private static float calculateTATavg(Map<TCB, Integer> turnaround_times) {
        int count = 0, sum = 0;

        for (Map.Entry<TCB, Integer> entry : turnaround_times.entrySet()) {
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

    private static Map<TCB, Integer> getWaitingTimes(List<TCB> tasks) {
        Map<TCB, Integer> ans = new LinkedHashMap<>();

        for (TCB t : tasks) {
            ans.put(t, t.getWaiting_time());
        }
        return ans;
    }

    private static float calculateWTavg(Map<TCB, Integer> waiting_times) {
        int sum = 0, size = waiting_times.size();
        for (Map.Entry<TCB, Integer> entry : waiting_times.entrySet()) {
            int val = entry.getValue();

            sum += val;
        }
        if (size == 0) {
            return 0;
        }
        return (float) sum / size;
    }

    private static Task getHighestWt(List<TCB> tasks) {
        TCB highest = tasks.get(0);
        for (TCB t : tasks) {
            if (t.getWaiting_time() > highest.getWaiting_time() && t.getFinish_time() != -1) {
                highest = t;
            }
        }
        return highest;
    }

    private static Task getLowestWt(List<TCB> tasks) {
        TCB lowest = tasks.get(0);
        for (TCB t : tasks) {
            if (t.getWaiting_time() < lowest.getWaiting_time() && t.getFinish_time() != -1) {
                lowest = t;
            }
        }
        return lowest;
    }

    private static List<TCB> checkForStarvation(List<TCB> tasks) {
        List<TCB> ans = new ArrayList<>();
        for (TCB task : tasks) {
            if (task.isInStarvation()) {
                ans.add(task);
            }
        }
        return ans;
    }

    private static List<TCB> missedDeadlines(List<TCB> tasks) {
        List<TCB> ans = new ArrayList<>();
        for (TCB task : tasks) {
            if (task.getDeadline_miss_instant() != -1) {
                ans.add(task);
            }
        }
        return ans;
    }

    private static LinkedHashMap<Integer, Float> calculateDeadlineMissRatio(ArrayList<TCB> tasks) {
        Map<Integer, Integer> totalActivations = new HashMap<>();
        Map<Integer, Integer> missedDeadlines = new HashMap<>();

        for (TCB t : tasks) {
            int id = t.getId();

            totalActivations.put(id, totalActivations.getOrDefault(id, 0) + 1);

            if (t.getDeadline_miss_instant() != -1) {
                missedDeadlines.put(id, missedDeadlines.getOrDefault(id, 0) + 1);
            }
        }

        LinkedHashMap<Integer, Float> missRatios = new LinkedHashMap<>();
        for (Map.Entry<Integer, Integer> entry : totalActivations.entrySet()) {
            int id = entry.getKey();
            int total = entry.getValue();
            int missed = missedDeadlines.getOrDefault(id, 0);

            float ratio = (float) missed / total;
            missRatios.put(id, ratio);
        }

        return missRatios;
    }

    public static void sortSimulationResults(
            Map<TCB, Integer> turnaround_times,
            Map<TCB, Integer> waiting_times,
            List<TCB> starvation,
            List<TCB> missed_deadline,
            LinkedHashMap<Integer, Float> deadline_miss_ratio) {

        // Ordena turnaround_times por ID
        Map<TCB, Integer> sortedTAT = turnaround_times.entrySet()
                .stream()
                .sorted(Comparator.comparingInt(e -> e.getKey().getId()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (a, b) -> a,
                        LinkedHashMap::new
                ));
        turnaround_times.clear();
        turnaround_times.putAll(sortedTAT);

        // Ordena waiting_times por ID
        Map<TCB, Integer> sortedWT = waiting_times.entrySet()
                .stream()
                .sorted(Comparator.comparingInt(e -> e.getKey().getId()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (a, b) -> a,
                        LinkedHashMap::new
                ));
        waiting_times.clear();
        waiting_times.putAll(sortedWT);

        // Ordena listas starvation e missed_deadline por ID
        starvation.sort(Comparator.comparingInt(TCB::getId));
        missed_deadline.sort(Comparator.comparingInt(TCB::getId));

        // Ordena deadline_miss_ratio pelo ID da task
        LinkedHashMap<Integer, Float> sortedRatio = deadline_miss_ratio.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByKey())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (a, b) -> a,
                        LinkedHashMap::new
                ));
        deadline_miss_ratio.clear();
        deadline_miss_ratio.putAll(sortedRatio);
    }
}
