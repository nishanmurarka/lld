package lru.localCache;

import java.util.HashMap;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

public class lru_dequeue {
    
    static class Expiry implements Delayed{
        int key;
        long expiryTime;
        Expiry(int key, long expiryTime)
        {
            this.key=key;
            this.expiryTime=expiryTime;
        }

        @Override
        public long getDelay(TimeUnit unit) {
            return unit.convert(
                expiryTime - System.nanoTime(),
                TimeUnit.NANOSECONDS
            );
        }

        @Override
        public int compareTo(Delayed other) {
            return Long.compare(
                expiryTime,
                ((Expiry) other).expiryTime
            );
        }
    }

    static class Node {
        int key;
        int value;
        Node prev;
        Node next;
        Node(int key,int value)
        {
            this.key=key;
            this.value=value;
        }
    }

    Thread cleaner=new Thread(this::cleanup);
    HashMap<Integer,Node> map;
    HashMap<Integer,Expiry> expiry=new HashMap<>();
    DelayQueue<Expiry> delay=new DelayQueue<>();

    private int capacity;
    private Node head;
    private Node tail;

    public lru_dequeue(int capacity)
    {
        map=new HashMap<>();
        this.capacity=capacity;
        head=null;
        tail=null;
        
        cleaner.setDaemon(true);
        cleaner.start();

    }

    private void addNode(Node node)
    {
        if(head==null)
        {
            head=node;
            tail=node;
        }
        else{
            node.next=head;
            head.prev=node;
            head=node;
        }
        map.put(node.key, node);
    }

    private void removeNode(Node node)
    {
        if(node==head)
            head=head.next;

        if(node==tail)
            tail=tail.prev;

        if(node.prev!=null)
            node.prev.next=node.next;

        if(node.next!=null)
            node.next.prev=node.prev;

        map.remove(node.key);

        node.prev=null;
        node.next=null;

        if(head==null)
            tail=null;

        if(map.size()==0)
        {
            head=null;
            tail=null;
        }

    }

    public synchronized int get(int key)
    {
        Node node = map.get(key);
        if(node==null)
            return -1;
        
        removeNode(node);

        Expiry exp=expiry.get(node.key);

        if(exp==null || exp.getDelay(TimeUnit.NANOSECONDS)<=0)
        {
            return -1;
        }

        addNode(node);

        return map.get(key).value;
    }

    public synchronized void put(int key, int value, long ttl)
    {
        Node node = map.get(key);
        if(node!=null)
        {
            removeNode(node);
        }
        if(map.size()==capacity)
            removeNode(tail);

        addNode(new Node(key,value));
        Expiry exp=new Expiry(key, System.nanoTime()+TimeUnit.MILLISECONDS.toNanos(ttl));
        expiry.put(key,exp);
        delay.offer(exp);
    }

    public void cleanup()
    {
        try{
            while(!Thread.currentThread().isInterrupted())
            {
                Expiry exp = delay.take();
                synchronized(this){
                    if(expiry.get(exp.key) == exp)
                    {
                        Node node = map.get(exp.key);
                        if(node!=null)
                            removeNode(map.get(exp.key));  

                        expiry.remove(exp.key);
                    }
                }
                
            }
        } catch (InterruptedException e)
        {
            Thread.currentThread().interrupt();       
        }
    }

    public synchronized void invalidate(int key)
    {
        Node node = map.get(key);
        if(node!=null)
            removeNode(node);

        expiry.remove(key);
    }
}
