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

    public void compute(TCB t, int time_units, int curr_time) {
        if (this.current_task == null) {
            setCurrentTask(t);
        }

        current_task.compute(time_units);
        this.utilization_time++;

        if (current_task.isFinished()) {
            //Adicionei "+1" pois notei que tasks estavam terminando um time unit mais cedo nas estatísticas
            current_task.setFinish_time(curr_time + 1);
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
