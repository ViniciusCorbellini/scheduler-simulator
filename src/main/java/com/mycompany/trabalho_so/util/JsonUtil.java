/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.mycompany.trabalho_so.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.mycompany.trabalho_so.model.simulation.SimulationConfig;
import com.mycompany.trabalho_so.scheduler.Scheduler;
import com.mycompany.trabalho_so.scheduler.SchedulerFactory;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;


/**
 *
 * @author Vinicius Corbellini
 */
public class JsonUtil {
    
    
    public static Scheduler getSchedulerFromSimConfig(SimulationConfig sc){
        return SchedulerFactory.getScheduler(sc.getScheduler_name());
    }
    
    public static SimulationConfig getSimConfigFromJson(String filepath) throws IOException{
        ObjectMapper om = new ObjectMapper();
        String json = new String(Files.readAllBytes(Path.of(filepath)));
        return om.readValue(json, SimulationConfig.class);
    }
    
    public static void main(String[] args) {
        
        String filepath = "C:\\Users\\Cliente\\Documents\\NetBeansProjects\\trabalho_so\\src\\main\\java\\com\\mycompany\\trabalho_so\\util\\SimConfig.json";
        try {
            ObjectMapper om = new JsonMapper();
            SimulationConfig sc = getSimConfigFromJson(filepath);
            System.out.println(om.writerWithDefaultPrettyPrinter().writeValueAsString(sc));
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        
    }
}
