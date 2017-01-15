package org.jhrcore.comm;

/**
 * Wrap.java
 *
 * Wrap is a thread pool with an incoming BlockingQueue of GameEvents
 *
 * @author wzh
 * @version 1.0
 */
public abstract class Wrap implements Runnable {

    /** milliseconds to sleep between processing runs */
    protected static final long WORKER_SLEEP_MILLIS = 10;
    /** incoming event queue */
    protected EventQueue eventQueue;
    /** are we running? * */
    protected boolean running = false;
    /** our pool of worker threads */
    private Thread workers[];
    /** short Class name of the implementing class */
    private String shortname;

    /**
     * @param numWorkers
     *            number of worker threads to spawn
     */
    public final void initWrap(int numWorkers) {
        // setup the log4j Logger
        shortname = this.getClass().getName().substring(
                this.getClass().getName().lastIndexOf(".") + 1);

        eventQueue = new EventQueue(shortname + "-in");

        // spawn worker threads
        workers = new Thread[numWorkers];
        for (int i = 0; i < numWorkers; i++) {
            workers[i] = new Thread(this, shortname + "-" + (i + 1));
            workers[i].setPriority(Thread.MAX_PRIORITY - 1);
            // workers[i].setDaemon(true);
            workers[i].start();
        }
    }

    /**
     * shutdown the worker threads
     */
    public void shutdown() {
        running = false;
        if (workers != null) {
            for (int i = 0; i < workers.length; i++) {
                workers[i].interrupt();
            }
        }
    }

    /**
     * queue the event for later processing by worker threads
     */
    public void handleEvent(Object event) {
        eventQueue.enQueue(event);
    }

    /**
     * retrieve events from the queue and process.
     */
    public void run() {
        Object event;
        running = true;
        while (running) {
            try {
                if ((event = eventQueue.deQueue()) != null) {
                    try {
                        if (event instanceof Runnable) {
                            ((Runnable) event).run();
                        } else {
                            processEvent(event);
                        }
                    } catch (Exception e) {
                    }
                }
            } catch (InterruptedException e) {
            }
        }
    }

    /**
     * subclasses must implement to do their processing
     */
    protected abstract boolean processEvent(Object event);
}
