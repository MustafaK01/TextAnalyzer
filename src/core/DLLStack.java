package core;

public class DLLStack<T> {

    private DoublyLinkedLst<T> ll = new DoublyLinkedLst<>();

    public void push(T item){
        ll.addNodeToHead(item);
    }

    public T pop(){
        return ll.getAndRemoveNodeFromHead();
    }

    public T peek(){
        return ll.getHead();
    }

    public boolean isEmpty(){
        return ll.isEmpty();
    }

}