package com.mycompany.trabalho_so.queues.readyqueue;

import com.mycompany.trabalho_so.model.task.TCB;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 *
 * @author manoCorbas
 */
public class ReadyQueue {

    protected Queue<TCB> queue;

    /**
     * Um construtor que passa como parametro um comparador. Assim, a criacao de
     * RQs em classes filhas é simplificada.
     *
     * @param c comparador
     */
    public ReadyQueue(Comparator<TCB> c) {
        this.queue = new PriorityQueue<>(c);
    }

    /**
     * Construtor de overloading para Schedulers que precisam de insercoes sem
     * comparador, como a do round robin.
     *
     * Funciona pois LinkedList(Instanciada no RR) chama offer() que chama
     * linkLast(), i. e. insere no final da "fila"
     *
     * @param q
     */
    public ReadyQueue(Queue<TCB> q) {
        this.queue = q;
    }

    /**
     * Adiciona uma task a RQ
     *
     * chama queue.offer para inserir a task na queue
     *
     * @param task task a ser adicionada
     */
    public void addTask(TCB task) {
        queue.offer(task);
    }

    /**
     * Remove primeira task na fila (FIFO)
     *
     * chama queue.poll para remover a task
     *
     * @return a task removida
     */
    public TCB pollTask() {
        return queue.poll();
    }

    /**
     * Mostra (sem remocao) a primeira task da fila
     *
     * @return a primeira task da fila
     */
    public TCB peekTask() {
        return queue.peek();
    }

    /**
     * verifica se a fila esta vazia
     *
     * @return true se a fila estiver vazia
     */
    public boolean isEmpty() {
        return queue.isEmpty();
    }

    /**
     *
     * @return a quantidade de tasks na fila
     */
    public int size() {
        return queue.size();
    }

    /**
     * Getter para queue
     *
     * @return atributo queue
     */
    public Queue<TCB> getQueue() {
        return queue;
    }

    public void showQueue(){
        System.out.println("Ready queue");
        System.out.println("=================");
        for (TCB tcb : queue) {
            System.out.println("id: T" + tcb.getId());
        }
        System.out.println("=================");
        System.out.println("");
    }
}
