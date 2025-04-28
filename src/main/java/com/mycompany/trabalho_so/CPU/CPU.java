/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.trabalho_so.CPU;

import com.mycompany.trabalho_so.model.task.TCB;

/**
 *
 * @author manoCorbas
 */
public class CPU {

    private TCB current_task;

    private int utilization_time;

    public CPU() {
        this.current_task = null;
        this.utilization_time = 0;
    }

    public void compute(TCB t, int time, int curr_time) {
        setCurrentTask(t);

        current_task.compute(time);

        if (current_task.isFinished()) {
            current_task.setFinish_time(curr_time);
            setCurrentTask(null);
        }

    }

    public TCB getCurrentTask() {
        return current_task;
    }

    public void setCurrentTask(TCB t) {
        this.current_task = t;
    }

    public boolean isBusy() {
        return this.current_task != null;
    }
}
