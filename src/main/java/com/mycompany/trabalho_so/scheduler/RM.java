package com.mycompany.trabalho_so.scheduler;

import com.mycompany.trabalho_so.model.simulation.SimulationConfig;
import com.mycompany.trabalho_so.model.simulation.SimulationResult;
import com.mycompany.trabalho_so.model.task.TCB;
import com.mycompany.trabalho_so.queues.readyqueue.ReadyQueue;
import static com.mycompany.trabalho_so.scheduler.Scheduler.LOG;
import com.mycompany.trabalho_so.stats.Stats;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.logging.Level;

/**
 *
 * @author Vinicius Corbellini
 */
public class RM extends Scheduler{

    public RM() {
        super(new ReadyQueue(Comparator.comparing(TCB::getDeadline)));
    }
    
    @Override
    public SimulationResult simulate(SimulationConfig config) {
        LOG.log(Level.INFO, "Starting simulation!\n");
        LOG.log(Level.INFO, "Parsing tasks into TCB's and adding them to the task list\n");
        ArrayList<TCB> tasks = parseTasksIntoTCBs(config);
        
        LOG.log(Level.INFO, "Handling JSON config file inconsistencies\n");
        super.handleDeadlineCoherence(tasks);
        
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

            LOG.log(Level.INFO, String.format("Computing task -> id: %d, ct remaining: %d\n", current.getId(), current.getComp_time_remaining()));
            cpu.compute(current, 1, time);
            
            super.checkIfFinished(current);
            super.preemptiveRemoval();
            time++;
        }
        LOG.log(Level.INFO, "Loop finished!\n");
        return Stats.calculate(tasks, super.cpu, config.getSimulation_time() + 1, super.finished);
    }

}
