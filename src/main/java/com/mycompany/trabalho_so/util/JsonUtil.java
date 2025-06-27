package com.mycompany.trabalho_so.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycompany.trabalho_so.model.simulation.SimulationConfig;
import com.mycompany.trabalho_so.scheduler.Scheduler;
import com.mycompany.trabalho_so.scheduler.SchedulerFactory;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 *
 * @author Vinicius Corbellini
 */
public class JsonUtil {

    public static Scheduler getSchedulerFromSimConfig(SimulationConfig sc) {
        return SchedulerFactory.getScheduler(sc.getScheduler_name());
    }

    public static SimulationConfig getSimConfigFromJson() throws IOException {
        String relativePath = "src/main/java/com/mycompany/trabalho_so/util/SimConfig.json";
        InputStream inputStream = new FileInputStream(relativePath);
        
        ObjectMapper om = new ObjectMapper();
        InputStreamReader isr = new InputStreamReader(inputStream);
        return om.readValue(isr, SimulationConfig.class);
    }
}
