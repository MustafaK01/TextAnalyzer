package core;

public class DLLQueue<T> {

    private DoublyLinkedLst<T> ll = new DoublyLinkedLst<>();

    public void enqueue(T item){
        ll.addNodeToTail(item);
    }

    public T dequeue(){
        return ll.getAndRemoveNodeFromHead();
    }

    public T peek(){
        return ll.getHead();
    }

    public boolean isEmpty(){
        return ll.isEmpty();
    }

}
