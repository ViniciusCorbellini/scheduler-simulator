/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.trabalho_so.model.task;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 *
 * @author manoCorbas
 */
public class Task {

    protected int offset;
    protected int computation_time;
    protected int period_time;
    protected int quantum;
    protected int deadline;

    @JsonCreator
    public Task(
            @JsonProperty("offset") int offset,
            @JsonProperty("computation_time") int computation_time,
            @JsonProperty("period_time") int period_time,
            @JsonProperty("quantum") int quantum,
            @JsonProperty("deadline") int deadline
    ) {
        this.offset = offset;
        this.computation_time = computation_time;
        this.period_time = period_time;
        this.quantum = quantum;
        this.deadline = deadline;
    }

    public int getQuantum() {
        return quantum;
    }

    public void setQuantum(int quantum) {
        this.quantum = quantum;
    }

    public int getDeadline() {
        return deadline;
    }

    public void setDeadline(int deadline) {
        this.deadline = deadline;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getComputation_time() {
        return computation_time;
    }

    public void setComputation_time(int computation_time) {
        this.computation_time = computation_time;
    }

    public int getPeriod_time() {
        return period_time;
    }

    public void setPeriod_time(int period_time) {
        this.period_time = period_time;
    }

    @Override
    public String toString() {
        return "Task{" + "offset: " + offset
                + ", computation_time: " + computation_time
                + ", period_time: " + period_time
                + ", quantum: " + quantum
                + ", deadline: " + deadline
                + '}';
    }
}
