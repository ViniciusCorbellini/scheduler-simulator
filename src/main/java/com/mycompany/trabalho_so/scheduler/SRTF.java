package com.mycompany.trabalho_so.scheduler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycompany.trabalho_so.model.simulation.SimulationConfig;
import com.mycompany.trabalho_so.model.simulation.SimulationResult;
import com.mycompany.trabalho_so.model.task.TCB;
import com.mycompany.trabalho_so.model.task.Task;
import com.mycompany.trabalho_so.queues.readyqueue.ReadyQueue;
import static com.mycompany.trabalho_so.scheduler.Scheduler.LOG;
import com.mycompany.trabalho_so.stats.Stats;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.logging.Level;

/**
 *
 * @author manoCorbas
 */
public class SRTF extends Scheduler{

    public SRTF() {
        super(new ReadyQueue(Comparator.comparing(TCB::getComp_time_remaining)));
    }
    
    @Override
    public SimulationResult simulate(SimulationConfig config) {
        LOG.log(Level.INFO, "Starting simulation!\n");
        LOG.log(Level.INFO, "Parsing tasks into TCB's and adding them to the task list\n");
        ArrayList<TCB> tasks = parseTasksIntoTCBs(config);

        //Tempo inicial do loop
        int time = 0;
        LOG.log(Level.INFO, "Starting loop!\n");
        while (time <= config.getSimulation_time()) {
            LOG.log(Level.INFO, String.format("Simulation -> Instant: %d\n", time));

            LOG.log(Level.INFO, "Checking for task offsets and periods\n");
            super.checkForOffsetsAndPeriods(tasks, time);

            super.getTaskFromRQ();
            TCB current = cpu.getCurrentTask();

            LOG.log(Level.INFO, "Updating ready queue's tasks waiting times\n");
            super.updateWaitingTimes();

            if (current == null) {
                LOG.log(Level.INFO, "No tasks in cpu\n");
                time++;
                continue;
            }

            LOG.log(Level.INFO, String.format("Computing task -> id: %d, ct remaining: %d, qt remaining: %d\n", current.getId(), current.getComp_time_remaining(), current.getQuantum_time_remaining()));
            cpu.compute(current, 1, time);
            
            super.checkIfFinished(current);
            super.preemptiveRemoval();
            time++;
        }
        LOG.log(Level.INFO, "Loop finished!\n");
        return Stats.calculate(tasks, super.cpu, config.getSimulation_time(), super.finished);
    }
    
    //Teste
    public static void main(String[] args) throws JsonProcessingException {
        ArrayList<Task> tasks = new ArrayList<>();

        // offset, ct, pt, q, d
        tasks.add(new Task(0, 5, 14, 1, 14));
        tasks.add(new Task(1, 2, 10, 2, 5));
        tasks.add(new Task(3, 7, 30, 5, 40));
        tasks.add(new Task(2, 3, 15, 3, 10));

        SimulationConfig sc = new SimulationConfig(23, "SRTF", 4, tasks);

        Scheduler sched = SchedulerFactory.getScheduler(sc.getScheduler_name());
        SimulationResult sr = sched.simulate(sc);

        ObjectMapper om = new ObjectMapper();
        System.out.println(om.writerWithDefaultPrettyPrinter().writeValueAsString(sr));
    }
}
