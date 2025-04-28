/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mycompany.trabalho_so.scheduler;

import com.mycompany.trabalho_so.CPU.CPU;
import com.mycompany.trabalho_so.model.simulation.SimulationConfig;
import com.mycompany.trabalho_so.model.simulation.SimulationResult;
import com.mycompany.trabalho_so.model.task.Task;
import com.mycompany.trabalho_so.queues.readyqueue.ReadyQueue;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 *
 * @author Cliente
 */
public abstract class Scheduler {

    /**
     * Logger para as classes filhas de scheduler
     */
    protected static final Logger LOG = Logger.getLogger(Scheduler.class.getName());
    
    protected ReadyQueue rq;
    
    protected CPU cpu;
    
    protected List<Task> finished;
    
    abstract SimulationResult simulate(SimulationConfig config);
    
    abstract boolean isPreemptive(); //TODO os bgl de prioridade

    public Scheduler(ReadyQueue rq) {
        this.rq = rq;
        this.cpu = new CPU();
        this.finished = new ArrayList<>();
    }
}
