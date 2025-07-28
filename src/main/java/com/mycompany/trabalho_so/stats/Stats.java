package com.mycompany.trabalho_so.stats;

import com.mycompany.trabalho_so.CPU.CPU;
import com.mycompany.trabalho_so.model.simulation.SimulationResult;
import com.mycompany.trabalho_so.model.task.TCB;
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
        Map<Integer, Float> tasks_tatAvg = calculateAvgTATById(turnaround_times);
        float turnaround_time_avg = calculateTATavg(tasks_tatAvg);

        Map<TCB, Integer> waiting_times = getWaitingTimes(finished);
        Map<Integer, Float> tasks_wtAvg = calculateWTavgById(waiting_times);
        float waiting_time_avg = calculateWTavg(tasks_wtAvg);

        int id_highest_wt_avg = getHighestWt(tasks_wtAvg);
        int id_lowest_wt_avg = getLowestWt(tasks_wtAvg);

        List<TCB> starvation = checkForStarvation(tasks);
        List<TCB> missed_deadline = missedDeadlines(tasks);
        LinkedHashMap<Integer, Float> deadline_miss_ratio = calculateDeadlineMissRatio(tasks);

        sortSimulationResults(turnaround_times, waiting_times, starvation, missed_deadline, deadline_miss_ratio);

        return new SimulationResult(utilization, turnaround_times,
                turnaround_time_avg, waiting_times, waiting_time_avg,
                id_highest_wt_avg, id_lowest_wt_avg, starvation, missed_deadline,
                deadline_miss_ratio, tasks_wtAvg, tasks_tatAvg
        );
    }

    public static SimulationResult calculate(ArrayList<TCB> tasks, CPU cpu, int time, List<TCB> finished, boolean isSchedulable) {
        SimulationResult sr = calculate(tasks, cpu, time, finished);
        sr.setIsSchedulable(isSchedulable);
        return sr;
    }

    private static Map<TCB, Integer> getTurnaroundTimes(List<TCB> tasks) {
        Map<TCB, Integer> ans = new LinkedHashMap<>();

        for (TCB t : tasks) {
            for (TCB instance : t.getInstances()) {
                if (!instance.isFinished()) {
                    continue;
                }
                ans.put(instance, instance.getTurnaround_time());
            }
        }
        return ans;
    }

    private static float calculateTATavg(Map<Integer, Float> tasks_tatAvg) {
        int n = tasks_tatAvg.size();
        if (n == 0) {
            return 0;
        }

        float sum = 0;
        for (Map.Entry<Integer, Float> entry : tasks_tatAvg.entrySet()) {
            Float tat_avg = entry.getValue();

            sum += tat_avg;
        }
        return sum / n;
    }

    private static Map<Integer, Float> calculateAvgTATById(Map<TCB, Integer> turnaround_times) {
        Map<Integer, List<Integer>> tatGroupedById = new HashMap<>();

        for (Map.Entry<TCB, Integer> entry : turnaround_times.entrySet()) {
            int id = entry.getKey().getId();
            int tat = entry.getValue();

            if (tat != -1) {
                tatGroupedById
                        .computeIfAbsent(id, k -> new ArrayList<>())
                        .add(tat);
            }
        }

        Map<Integer, Float> avgTatById = new LinkedHashMap<>();
        for (Map.Entry<Integer, List<Integer>> entry : tatGroupedById.entrySet()) {
            List<Integer> tatList = entry.getValue();
            float sum = 0;
            for (int tat : tatList) {
                sum += tat;
            }
            avgTatById.put(entry.getKey(), sum / tatList.size());
        }

        return avgTatById;
    }

    private static Map<TCB, Integer> getWaitingTimes(List<TCB> tasks) {
        Map<TCB, Integer> ans = new LinkedHashMap<>();

        for (TCB t : tasks) {
            for (TCB instance : t.getInstances()) {
                if (!instance.isFinished()) {
                    continue;
                }
                ans.put(instance, instance.getWaiting_time());
            }
        }
        return ans;
    }

    private static float calculateWTavg(Map<Integer, Float> tasks_wtAvg) {
        int n = tasks_wtAvg.size();
        if (n == 0) {
            return 0;
        }

        float sum = 0;
        for (Map.Entry<Integer, Float> entry : tasks_wtAvg.entrySet()) {
            Float tat_avg = entry.getValue();

            sum += tat_avg;
        }
        return sum / n;
    }

    private static Map<Integer, Float> calculateWTavgById(Map<TCB, Integer> waiting_times) {
        Map<Integer, List<Integer>> wtGroupedById = new HashMap<>();

        for (Map.Entry<TCB, Integer> entry : waiting_times.entrySet()) {
            int id = entry.getKey().getId();
            int wt = entry.getValue();

            wtGroupedById.computeIfAbsent(id, k -> new ArrayList<>()).add(wt);
        }

        Map<Integer, Float> avgWTById = new LinkedHashMap<>();
        for (Map.Entry<Integer, List<Integer>> entry : wtGroupedById.entrySet()) {
            List<Integer> list = entry.getValue();
            float avg = (float) list.stream().mapToInt(Integer::intValue).sum() / list.size();
            avgWTById.put(entry.getKey(), avg);
        }

        return avgWTById;
    }

    private static int getHighestWt(Map<Integer, Float> tasks_wtAvg) {
        int highestId = -1;
        float highestWT = Float.MIN_VALUE;

        for (Map.Entry<Integer, Float> entry : tasks_wtAvg.entrySet()) {
            if (entry.getValue() > highestWT) {
                highestWT = entry.getValue();
                highestId = entry.getKey();
            }
        }
        return highestId;
    }

    private static int getLowestWt(Map<Integer, Float> tasks_wtAvg) {
        int lowestId = -1;
        float lowestWT = Float.MAX_VALUE;

        for (Map.Entry<Integer, Float> entry : tasks_wtAvg.entrySet()) {
            if (entry.getValue() < lowestWT) {
                lowestWT = entry.getValue();
                lowestId = entry.getKey();
            }
        }
        return lowestId;
    }

    private static List<TCB> checkForStarvation(List<TCB> tasks) {
        List<TCB> ans = new ArrayList<>();
        for (TCB task : tasks) {
            for (TCB instance : task.getInstances()) {
                if (instance.isInStarvation()) {
                    ans.add(instance);
                }
            }
        }
        return ans;
    }

    private static List<TCB> missedDeadlines(List<TCB> tasks) {
        List<TCB> ans = new ArrayList<>();
        for (TCB task : tasks) {
            for (TCB instance : task.getInstances()) {
                if (instance.missedDeadline()) {
                    ans.add(instance);
                }
            }
        }
        return ans;
    }

    private static LinkedHashMap<Integer, Float> calculateDeadlineMissRatio(ArrayList<TCB> tasks) {
        Map<Integer, Integer> totalActivations = new HashMap<>();
        Map<Integer, Integer> missedDeadlines = new HashMap<>();

        for (TCB t : tasks) {
            int id = t.getId();

            if (t.isFinished() || !t.missedDeadline()) {
                totalActivations.put(id, totalActivations.getOrDefault(id, 0) + 1);
            }

            if (t.missedDeadline()) {
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
