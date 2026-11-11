package lru;

import java.util.HashMap;

public class lru {
    
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
    HashMap<Integer,Node> map;

    int capacity;
    Node head;
    Node tail;

    public lru(int capacity)
    {
        map=new HashMap<>();
        this.capacity=capacity;
        head=null;
        tail=null;

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

        addNode(new Node(key,value));
            
    }
}
