package core;

import java.util.Queue;

public class LinkedLst<T> {
    public Node<T> head;
    public Node<T> tail;
    public LinkedLst(){
        head = null;
        tail = null;
    }

    public void addItemToHead(T item){
        Node<T> node = new Node<>(item);
        if(this.head != null) {
            node.next = head;
            head = node;
        } else {
            head = node;
            tail = node;
        }

    }

    public void addItemToTail(T item){
        Node<T> node = new Node<>(item);
        if(this.head != null) {
            tail.next = node;
        } else {
            head = node;
        }
        tail = node;
    }

    public void addItemsAsArray(T[] items){
        for (int i = 0; i < items.length; i++) {
            addItemToTail(items[i]);
        }
    }

    public void printAllItems(){
        while(head!=null){
            System.out.println(head.data);
            head = head.next;
        }
    }

//    public void String[] convertTheTextToStringArray(String text){
//
//
//
//    }

}
