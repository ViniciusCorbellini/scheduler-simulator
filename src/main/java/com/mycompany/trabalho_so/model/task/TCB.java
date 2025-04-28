/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.trabalho_so.model.task;

/**
 *
 * @author manoCorbas
 */
public class TCB extends Task { //Salva o contexto da task

    private int comp_time_remaining;    
    private int waiting_time;
    private int finish_time;
    private int priority;
    
    //===== metodos do TCB
    /**
     * metodo que diminui o comp_time_remaining com base em um determinado tempo
     * 
     * @param time tempo que o cpu executou a tarefa
     */
    public void compute(int time){
        if(time >= comp_time_remaining) this.comp_time_remaining = 0;
        else this.comp_time_remaining -= time;
    }
    
    public int getTurnaround_time() {
        return finish_time - super.offset;
    }
    
    /**
     * metodo que incrementa o waiting time em 1
     * util para as tasks na RQ
     * 
     */
    public void incrementWaitingTime(){
        this.waiting_time++;
    }
    
    /**
     * metodo para descobrir se a tarefa ja foi executada
     * 
     * @return true se o tempo restante de computacao for igual a 0
     */
    public boolean isFinished() {
        return this.comp_time_remaining == 0;
    }
    
    /**
     * metodo para descobrir se a tarefa sofreu starvation
     * 
     * @return true se o tempo de finalizacao da excecucao for igual a -1 (valor 
     * instanciado no construtor do TCB)
     */
    public boolean isInStarvation(){
        return this.finish_time == -1;
    }
    
    //===== Construtores 
    public TCB(Task t) {
        super(t.offset, t.computation_time, t.quantum, t.period_time, t.deadline);
        this.comp_time_remaining = t.computation_time;
        this.waiting_time = 0;
        this.finish_time = -1;
    }

    //===== Getters, setters  e toString()
    @Override
    public String toString() {
        return "TCB{" 
                + "\ncomp_time_remaining: " + comp_time_remaining 
                + "\n, waiting_time: " + waiting_time 
                + "\n, finish_time: " + finish_time 
                + "\n,offset: " + offset 
                + "\n, computation_time: " + computation_time 
                + "\n, quantum: " + quantum 
                + "\n, period_time: " + period_time 
                + '}';
    }
    
    public int getComp_time_remaining() {
        return comp_time_remaining;
    }

    public void setComp_time_remaining(int comp_time_remaining) {
        this.comp_time_remaining = comp_time_remaining;
    }

    public int getWaiting_time() {
        return waiting_time;
    }

    public void setWaiting_time(int waiting_time) {
        this.waiting_time = waiting_time;
    }

    public int getFinish_time() {
        return finish_time;
    }

    public void setFinish_time(int finish_time) {
        this.finish_time = finish_time;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }
}
