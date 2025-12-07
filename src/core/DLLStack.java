package core;

public class DLLStack<T> {

    private DoublyLinkedLst<T> list = new DoublyLinkedLst<>();

    public void push(T item){
        list.addItemToHead(item);
    }

    public T pop(){
        return list.getAndRemoveItemFromHead();
    }

    public T peek(){
        return list.getHead();
    }

    public boolean isEmpty(){
        return list.isEmpty();
    }

}