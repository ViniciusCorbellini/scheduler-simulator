/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.trabalho_so.scheduler;

import com.mycompany.trabalho_so.scheduler.Scheduler;

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
            default ->
                throw new AssertionError();
        }
    }
}
