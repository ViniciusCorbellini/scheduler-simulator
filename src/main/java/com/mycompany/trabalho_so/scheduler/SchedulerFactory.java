package com.mycompany.trabalho_so.scheduler;

/**
 *
 * @author manoCorbas
 */
public class SchedulerFactory {

    public static Scheduler getScheduler(String name) {
        switch (name) {
            case "FCFS" -> {
                return new FCFS();
            }
            case "FIFO" -> {
                return new FCFS();
            }
            case "SJF" -> {
                return new SJF();
            }
            case "RR" -> {
                return new RR();
            }
            case "SRTF" -> {
                return new SRTF();
            }
            case "RM" -> {
                return new RM();
            }
            case "EDF" -> {
                return new EDF();
            }
            default ->
                throw new AssertionError();
        }
    }
}
