package core;

public class DoublyLinkedLst<T> {

    private Node<T> head;
    private Node<T> tail;

    public DoublyLinkedLst(){
        head = null;
        tail = null;
    }

    public void addNodeToHead(T item){
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

    public void addNodeToTail(T item){
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

    public void addNodesAsArray(T[] items){
        for (int i = 0; i < items.length; i++) {
            addNodeToTail(items[i]);
        }
    }

    public void printAllNodes(){
        Node<T> curr = head;
        while(curr!=null){
            System.out.println(curr.data);
            curr = curr.next;
        }
    }

    public void printNodeWithIndex(int index){
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

    public T getAndRemoveNodeFromHead(){
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

    public T getAndRemoveNodeFromTail(){
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

    public Node<T> getHeadNode(){
        if (head == null) return null;
        return head;
    }

    public void removeNode(Node<T> curr){
        Node<T> left = curr.prev;
        Node<T> right = curr.next;
        if (curr == head && curr == tail){
            head = null;
            tail = null;
            return;
        }
        if(curr == head){
            head = right;
            head.prev = null;
            return;
        }
        if(curr == tail){
            tail = left;
            head.next = null;
            return;
        }
        if(left!=null){
            left.next = right;
            curr.next = null;
        }
        if(right!=null){
            right.prev = left;
            curr.prev = null;
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        Node current = head;
        while (current != null) {
            sb.append(current.toString()).append("\n");
            current = current.next;
        }
        return sb.toString();
    }

}
