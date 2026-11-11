package lru;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class lru_lazy {
    
    static class Node {
        int key;
        int value;
        Node prev;
        Node next;
        long expiryTime;
        Node(int key,int value, long ttl)
        {
            this.key=key;
            this.value=value;
            expiryTime = System.currentTimeMillis()+ttl;
        }
    }
    HashMap<Integer,Node> map;
    ScheduledExecutorService cleaner = Executors.newSingleThreadScheduledExecutor();
    int capacity;
    Node head;
    Node tail;

    public lru_lazy(int capacity)
    {
        map=new HashMap<>();
        this.capacity=capacity;
        head=null;
        tail=null;

        cleaner.scheduleAtFixedRate(this:: cleanup, 1, 1, TimeUnit.SECONDS);

    }

    public void addNode(Node node)
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

    public void removeNode(Node node)
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

    public int get(int key)
    {
        Node node = map.get(key);
        if(node==null)
            return -1;

        removeNode(node);
        if(System.currentTimeMillis()>= node.expiryTime)
        {
            return -1;
        }
        addNode(node);

        return map.get(key).value;
    }

    public void put(int key, int value, long ttl)
    {
        Node node = map.get(key);
        if(node!=null)
        {
            removeNode(node);
        }
        if(map.size()==capacity)
            removeNode(tail);

        addNode(new Node(key,value, ttl));
            
    }

    public void cleanup(){
        for(Map.Entry<Integer,Node> entry: map.entrySet())
        {
            if(entry.getValue().expiryTime>=System.nanoTime())
            {
                removeNode(entry.getValue());
                map.remove(entry.getKey());
                
            }
        }
    }
}
