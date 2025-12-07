package core;

public class DoublyLinkedLst<T> {

    private Node<T> head;
    private Node<T> tail;

    public DoublyLinkedLst(){
        head = null;
        tail = null;
    }

    public void addItemToHead(T item){
        Node<T> node = new Node<>(item);
        if(this.head != null) {
            node.next = head;
            head.prev = node;
            node.prev = null;
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
            node.prev = tail;
            node.next = null;
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
        Node<T> curr = head;
        while(curr!=null){
            System.out.println(curr.data);
            curr = curr.next;
        }
    }

    public void printItemWithIndex(int index){
        Node<T> curr = head;
        int i = 0;
        while(curr!=null){
            if(i == index) {
                System.out.println(curr.data);
                return;
            }
            curr = curr.next;
            i++;
        }
    }

    public T getAndRemoveItemFromHead(){
        if(this.isEmpty()) return null;
        T temp = head.data;
        if(head.next != null){
            head = head.next;
            head.prev = null;
        }else{
            head = null;
            tail = null;
        }
        return temp;
    }

    public T getAndRemoveItemFromTail(){
        if(this.isEmpty()) return null;
        T temp = tail.data;
        if(tail.prev != null){
            tail = tail.prev;
            tail.next = null;
        }else{
            head = null;
            tail = null;
        }
        return temp;
    }

    public boolean isEmpty(){
        return head==null;
    }

    public T getHead(){
        if (head == null) return null;
        return head.data;
    }

}
