package com.mycompany.trabalho_so;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycompany.trabalho_so.model.simulation.SimulationConfig;
import com.mycompany.trabalho_so.model.simulation.SimulationResult;
import com.mycompany.trabalho_so.scheduler.Scheduler;
import com.mycompany.trabalho_so.scheduler.SchedulerFactory;
import com.mycompany.trabalho_so.util.JsonUtil;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Cliente
 */
public class Trabalho_so {

    public static void main(String[] args) {
        try {
            SimulationConfig sc = JsonUtil.getSimConfigFromJson();
            Scheduler sched = SchedulerFactory.getScheduler(sc.getScheduler_name());
            SimulationResult sr = sched.simulate(sc);

            ObjectMapper om = new ObjectMapper();
            System.out.println("================================================");
            System.out.println("Results: \n");
            System.out.println(om
                    .writerWithDefaultPrettyPrinter()
                    .writeValueAsString(sr)
            );
            //wt MEDIO para cada ID
            // testar o rm
        } catch (IOException ex) {
            Logger.getLogger(Trabalho_so.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
