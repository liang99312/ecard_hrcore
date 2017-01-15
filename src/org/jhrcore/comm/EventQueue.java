package org.jhrcore.comm;

import java.util.LinkedList;

/**
 * EventQueue.java
 *
 * Blocking queue of GameEvents.
 *
 * @author wzh
 * @version 1.0
 */
public class EventQueue
{
    private LinkedList<Object> events;
    private int count = 0;

    /**
     * Constructor.  Initializes the logger and event list
     */
    public EventQueue (String name)
    {
    	events = new LinkedList<Object>();
    }

    /**
     * add an event to the queue
     */
    public synchronized void enQueue(Object event)
    {
    	events.addLast(event);
    	notifyAll();
    }

    /**
     * blocks until an event is available
     * and then removes and returns the first
     * available event
     */
    public synchronized Object deQueue() throws InterruptedException
	{
    	while (events.size() == 0) {
    		count++;
    		wait();
    		count --;
    	}

    	Object e = events.removeFirst();
    	return e;
    }

    /**
     * get the current # of events in the queue
     */
    public synchronized int size()
    {
    	return events.size();
    }

}// EventQueue
