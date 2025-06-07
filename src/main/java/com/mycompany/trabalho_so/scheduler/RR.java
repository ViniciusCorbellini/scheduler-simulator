/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.mycompany.trabalho_so.scheduler;

import com.mycompany.trabalho_so.model.simulation.SimulationConfig;
import com.mycompany.trabalho_so.model.simulation.SimulationResult;
import com.mycompany.trabalho_so.model.task.TCB;
import com.mycompany.trabalho_so.queues.readyqueue.ReadyQueue;
import java.util.LinkedList;

/**
 *
 * @author manoCorbas
 */
public class RR extends Scheduler{

    public RR() {
        super(new ReadyQueue(new LinkedList<TCB>()));
    }

    @Override
    public SimulationResult simulate(SimulationConfig config) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
