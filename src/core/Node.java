package core;

public class Node<T> {
    protected T data;
    protected Node<T> next;
    protected Node<T> prev;
    protected Node(T data){
        this.data = data;
        this.next = null;
        this.prev = null;
    }
}
