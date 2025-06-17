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
            case "RR" -> {
                return new RR();
            }
            case "SJF" -> {
                return new SJF();
            }
            case "SRTF" -> {
                return new SRTF();
            }
            default ->
                throw new AssertionError();
        }
    }
}
