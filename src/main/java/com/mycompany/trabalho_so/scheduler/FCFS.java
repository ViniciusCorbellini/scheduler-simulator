package com.mycompany.trabalho_so.scheduler;

import com.mycompany.trabalho_so.model.simulation.SimulationConfig;
import com.mycompany.trabalho_so.model.simulation.SimulationResult;
import com.mycompany.trabalho_so.model.task.TCB;
import com.mycompany.trabalho_so.queues.readyqueue.ReadyQueue;
import static com.mycompany.trabalho_so.scheduler.Scheduler.LOG;
import com.mycompany.trabalho_so.stats.Stats;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.logging.Level;

/**
 *
 * @author manoCorbas
 */
public class FCFS extends Scheduler {

    public FCFS() {
        super(new ReadyQueue(new LinkedList<>()));
    }

    @Override
    public SimulationResult simulate(SimulationConfig config) {
        LOG.log(Level.INFO, "Starting simulation - > FCFS!\n");
        LOG.log(Level.INFO, "Parsing tasks into TCB's and adding them to the task list\n");
        ArrayList<TCB> tasks = parseTasksIntoTCBs(config);

        //Tempo inicial do loop
        int time = 0;
        LOG.log(Level.INFO, "Starting loop!\n");
        while (time < config.getSimulation_time()) {
            LOG.log(Level.INFO, String.format("-> Instant: %d\n", time));

            LOG.log(Level.INFO, "Checking for task offsets and periods\n");
            checkForOffsetsAndPeriods(tasks, time);

            super.getTaskFromRQ();
            TCB current = cpu.getCurrentTask();

            LOG.log(Level.INFO, "Updating ready queue's tasks waiting times\n");
            updateWaitingTimes();

            if (current == null) {
                LOG.log(Level.INFO, "No tasks in cpu\n");
                time++;
                continue;
            }

            LOG.log(Level.INFO, String.format("Computing task -> id: %d\n", current.getId()));
            cpu.compute(current, 1, time);

            super.checkIfFinished(current);
            time++;
        }
        LOG.log(Level.INFO, "Loop finished!\n");
        return Stats.calculate(tasks, super.cpu, config.getSimulation_time(), super.finished);
    }
}
