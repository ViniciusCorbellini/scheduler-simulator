package com.mycompany.trabalho_so.model.task;

/**
 *
 * @author manoCorbas
 */
public class TCB extends Task { //Salva o contexto da task

    private int id;    
    private int comp_time_remaining;    
    private int waiting_time;
    private int finish_time;
    
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
     * @return true se o tempo de excecucao restante for igual ao tempo inicial 
     * de CT (tarefas que nao entraram na CPU possuirao CTs iguais ao CT restante)
     */
    public boolean isInStarvation(){
        return this.comp_time_remaining == computation_time;
    }
    
    //===== Construtores 
    public TCB(int id, Task t) {
        super(t.offset, t.computation_time, t.period_time, t.quantum, t.deadline);
        this.id = id;
        this.comp_time_remaining = t.computation_time;
        this.waiting_time = 0;
        this.finish_time = -1;
    }

    //===== Getters, setters  e toString()
    @Override
    public String toString() {
        return "TCB{"
                + "id: " + id
                + ", comp_time_remaining: " + comp_time_remaining 
                + ", waiting_time: " + waiting_time 
                + ", finish_time: " + finish_time 
                + ", offset: " + offset 
                + ", computation_time: " + computation_time 
                + ", quantum: " + quantum 
                + ", period_time: " + period_time 
                + '}';
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
}
