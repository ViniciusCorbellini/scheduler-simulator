package com.mycompany.trabalho_so.scheduler;

import com.mycompany.trabalho_so.CPU.CPU;
import com.mycompany.trabalho_so.model.simulation.SimulationConfig;
import com.mycompany.trabalho_so.model.simulation.SimulationResult;
import com.mycompany.trabalho_so.model.task.TCB;
import com.mycompany.trabalho_so.model.task.Task;
import com.mycompany.trabalho_so.queues.readyqueue.ReadyQueue;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Cliente
 */
public abstract class Scheduler {

    /**
     * Logger para as classes filhas de scheduler
     */
    protected static final Logger LOG = Logger.getLogger(Scheduler.class.getName());

    // ===== Metodo abstrato que deve ser implementado pelas classes filhas
    public abstract SimulationResult simulate(SimulationConfig config);

    // ===== Métodos utilizaveis por todas as classes filhas
    protected ArrayList<TCB> parseTasksIntoTCBs(SimulationConfig config) {
        ArrayList<TCB> tasks = new ArrayList<>();

        //Converte as tasks em TCBS com id, facilitando o chaveamento de contexto
        AtomicInteger id = new AtomicInteger(1); //A funcao lambda nao aceita um int normal
        config.getTasks()
                .forEach(
                        task -> tasks.add(new TCB(id.getAndIncrement(), task)
                        ));
        return tasks;
    }

    protected void checkForOffsetsAndPeriods(ArrayList<TCB> tasks, int time) {
        //Um arrayList local da funcao para ministrar 
        //novas instancias de periodos de tasks
        ArrayList<TCB> newInstances = new ArrayList<>();

        //Itera sobre as tasks para verificar se alguma deve entrar na fila
        for (TCB t : tasks) {
            //Evita que instancias desnecessarias sejam criadas
            if (t.getPeriod_time() == -1) {
                continue;
            }
            if (time == t.getOffset()) {
                LOG.log(Level.INFO, String.format("Task offset -> instant: %d, task id: %d\n", t.getOffset(), t.getId()));
                rq.addTask(t);
                continue;
            }
            if ((time - t.getOffset()) % t.getPeriod_time() == 0) {
                LOG.log(Level.INFO, String.format("Task period -> task id: %d\n", t.getId()));
                /**
                 * Ministra uma nova instancia de uma task: O arrayList Adciona
                 * uma nova TCB, passando como parametro do construtor da TCB o
                 * id da task, o time, ct, -1, quantum e deadline. (Obs: o -1
                 * impede que seja criada uma nova instancia de uma task com
                 * base no periodo de uma instancia de uma task já existente)
                 * Dessa maneira, é possível usar todos esses dados da task sem
                 * interferir Nos atributos da TCB
                 */
                TCB newInstance = new TCB(t.getId(),
                        new Task(
                                time, //O instante de entrada na RQ (offset)
                                t.getComputation_time(),
                                -1,
                                t.getQuantum(),
                                t.getDeadline()
                        )
                );
                newInstances.add(newInstance);
                rq.addTask(newInstance);
            }
        }
        //Adiciona as novas instancias ao grupo de tasks do scheduler
        //Facilitando, assim, o calculo dos resultados
        tasks.addAll(newInstances);
    }

    protected void updateWaitingTimes() {
        //Itera sobre as tasks na ready queue para aumentar o waiting time das mesmas
        for (TCB t : rq.getQueue()) {
            t.setWaiting_time(t.getWaiting_time() + 1);
        }
    }

    protected void getTaskFromRQ() {
        if (cpu.isBusy()) {
            return;
        }

        LOG.log(Level.INFO, "Selecting task in RQ\n");
        cpu.setCurrentTask(rq.pollTask());
    }

    //Pega a task da cpu, coloca ela na rq e remove ela da cpu
    protected void preemptiveRemoval() {
        TCB current = cpu.getCurrentTask();
        if (current == null) {
            return;
        }

        rq.addTask(current);
        cpu.setCurrentTask(null);
    }

    protected void checkIfFinished(TCB current) {
        if (current != null && current.isFinished()) {
            LOG.log(Level.INFO, String.format("Task finished -> id: %d\n", current.getId()));
            LOG.log(Level.INFO, String.format("Adding task to finished and removing it from CPU-> id: %d\n", current.getId()));

            cpu.setCurrentTask(null);
            finished.add(current);
        }
    }

    //Funcao necessaria para RM e EDFs, no qual o periodo deve, obrigatoriamente ser igual ao Deadline
    protected void handleDeadlineCoherence(ArrayList<TCB> tasks) {
        tasks.forEach(
                t -> t.setDeadline(t.getPeriod_time())
        );
    }

    protected void checkForDeadlineMiss(int currentTime, ArrayList<TCB> tasks) {
        Iterator<TCB> iterator = rq.getQueue().iterator();
        while (iterator.hasNext()) {
            TCB task = iterator.next();
            if (task.getAbolute_deadline() <= currentTime && !task.isFinished()) {
                LOG.log(Level.WARNING, String.format("Deadline missed -> Task id: %d, at time: %d\n", task.getId(), currentTime));
                task.setDeadline_miss_instant(currentTime);
                iterator.remove(); // Optei por usar iterator para nao dar ConcurrentModificationException
            }
        }

        // Verifica a task atual na CPU
        TCB current = cpu.getCurrentTask();
        if (current != null && current.getAbolute_deadline() <= currentTime && !current.isFinished()) {
            LOG.log(Level.WARNING, String.format("Deadline missed -> Task id: %d, at time: %d\n", current.getId(), currentTime));
            current.setDeadline_miss_instant(currentTime);
            cpu.setCurrentTask(null);
        }
    }

    // ===== Atributos
    protected ReadyQueue rq;
    protected CPU cpu;
    protected List<TCB> finished;

    // ===== Construtor
    public Scheduler(ReadyQueue rq) {
        this.rq = rq;
        this.cpu = new CPU();
        this.finished = new ArrayList<>();
    }

    public ReadyQueue getRq() {
        return rq;
    }

    public CPU getCpu() {
        return cpu;
    }

    public List<TCB> getFinished() {
        return finished;
    }
}
