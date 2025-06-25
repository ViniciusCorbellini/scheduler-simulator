package com.mycompany.trabalho_so.scheduler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycompany.trabalho_so.model.simulation.SimulationConfig;
import com.mycompany.trabalho_so.model.simulation.SimulationResult;
import com.mycompany.trabalho_so.model.task.TCB;
import com.mycompany.trabalho_so.model.task.Task;
import com.mycompany.trabalho_so.queues.readyqueue.ReadyQueue;
import com.mycompany.trabalho_so.stats.Stats;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.logging.Level;

/**
 *
 * @author manoCorbas
 */
public class RR extends Scheduler {
    public RR() {
        super(new ReadyQueue(new LinkedList<TCB>()));
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

            LOG.log(Level.INFO, String.format("Computing task -> id: %d, ct remaining: %d, qt remaining: %d\n", current.getId(), current.getComp_time_remaining(), current.getQuantum_time_remaining()));
            cpu.compute(current, 1, time);
            
            current.decrementQuantumRemaining();
            checkQuantumTime(current);

            checkIfFinished(current);
            time++;
        }
        LOG.log(Level.INFO, "Loop finished!\n");
        return Stats.calculate(tasks, super.cpu, config.getSimulation_time() + 1, super.finished);
    }

    private void checkQuantumTime(TCB current) {
        if (current.quantumTimeFinished()) {
            LOG.log(Level.INFO, String.format("Quantum time ended, removing task from CPU -> task id: %d\n", current.getId()));
            cpu.setCurrentTask(null);
            current.resetQuantumTime();
            
            //Garante que tarefas cujo qt acabou mas ainda nao terminaram seu ct vao para a rq
            if(!current.isFinished()){ 
                LOG.log(Level.INFO, String.format("Task not finished yet, adding it back to rq- > task id: %d\n", current.getId()));
                super.rq.addTask(current);
            }
        } else{
            LOG.log(Level.INFO, String.format(
                    "Quantum time did not end, keeping task in cpu -> task id: %d, ct remaining: %d\n", current.getId(), current.getComp_time_remaining()
            ));
        }
    }
    
    //Teste
    public static void main(String[] args) throws JsonProcessingException {

        ArrayList<Task> tasks = new ArrayList<>();

        // offset, ct, pt, q, d
        tasks.add(new Task(0, 5, 14, 1, 14));
        tasks.add(new Task(1, 2, 10, 2, 5));
        tasks.add(new Task(3, 7, 30, 5, 40));
        tasks.add(new Task(2, 3, 15, 3, 10));

        SimulationConfig sc = new SimulationConfig(23, "RR", 4, tasks);

        Scheduler sched = SchedulerFactory.getScheduler(sc.getScheduler_name());
        SimulationResult sr = sched.simulate(sc);

        ObjectMapper om = new ObjectMapper();
        System.out.println(om.writerWithDefaultPrettyPrinter().writeValueAsString(sr));
    }
}
