/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.mycompany.trabalho_so.scheduler;

import com.mycompany.trabalho_so.model.simulation.SimulationConfig;
import com.mycompany.trabalho_so.model.simulation.SimulationResult;
import com.mycompany.trabalho_so.model.task.TCB;
import com.mycompany.trabalho_so.queues.readyqueue.ReadyQueue;
import java.util.Comparator;

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
        throw new UnsupportedOperationException("Not supported yet."); 
    }
}
