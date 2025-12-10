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

    public T getData() {
        return data;
    }

    public Node<T> getNext() {
        return next;
    }

    public Node<T> getPrev() {
        return prev;
    }

    @Override
    public String toString() {
        if (data == null) return "null";

        return data.toString();
    }
}
