/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.mycompany.trabalho_so.scheduler;

import com.mycompany.trabalho_so.model.simulation.SimulationConfig;
import com.mycompany.trabalho_so.model.simulation.SimulationResult;
import com.mycompany.trabalho_so.model.task.TCB;
import com.mycompany.trabalho_so.model.task.Task;
import com.mycompany.trabalho_so.queues.readyqueue.ReadyQueue;
import java.util.LinkedList;

/**
 *
 * @author manoCorbas
 */
public class FCFS extends Scheduler{

    public FCFS() {
        super(new ReadyQueue(new LinkedList<>()));
    }

    @Override
    public SimulationResult simulate(SimulationConfig config) {
        config.getTasks().forEach(task -> new TCB(task)); //Transforma as tasks recebidas em TCBS
        
        int time = 0;
        
        while(time <= config.getSimulation_time()){
            
            //Itera sobre as tasks para verificar se alguma deve entrar na fila
            for (Task t : config.getTasks()) { 
                if(time == t.getOffset()){
                    super.rq.addTask((TCB) t);
                }
                
                //Cria-se uma nova instancia de 
                if(time % (t.getOffset() + t.getPeriod_time()) == 0){
                    super.rq.addTask(new TCB(t));
                }
            }
            
            if(!cpu.isBusy()){
                cpu.setCurrentTask(rq.pollTask());
            }
            
            TCB current = cpu.getCurrentTask();
            
            if(current == null){
                time++;
                continue;
            }
            
            if(!isPreemptive()){
                cpu.compute(current, 1, time);
            }
            
            if(current.isFinished()){
                super.finished.add(current);
            }
            time++;
        }
        
        
        throw new UnsupportedOperationException("Not supported yet."); 
    }

    @Override
    public boolean isPreemptive() {
        return false;
    }

}
