package core.map;

import core.DoublyLinkedLst;
import core.Node;

public class HMap<K,V> {

    DoublyLinkedLst<MapEntry<K,V>>[] llMapBuckets;
    int capacity;
    int size = 0;
    double loadFactor = 0.75;

    public HMap() {
        this.capacity = 16;
        llMapBuckets = (DoublyLinkedLst<MapEntry<K,V>>[]) new DoublyLinkedLst[this.capacity];
    }

    public HMap(int capacity) {
        this.capacity = capacity;
        llMapBuckets = (DoublyLinkedLst<MapEntry<K,V>>[]) new DoublyLinkedLst[capacity];
    }

    public HMap(int capacity,int loadF) {
        this.capacity = capacity;
        this.loadFactor = loadF;
        llMapBuckets = (DoublyLinkedLst<MapEntry<K,V>>[]) new DoublyLinkedLst[capacity];
    }

    public void put(K key,V value){
        if(key == null) throw new IllegalArgumentException("NULL KEY ERROR");

        int h = key.hashCode() & 0x7fffffff; // sign biti kaldırır
        int i = h % capacity;
        if(llMapBuckets[i] == null){
            llMapBuckets[i] = new DoublyLinkedLst<MapEntry<K,V>>();
        }
        Node<MapEntry<K,V>> curr = llMapBuckets[i].getHeadNode();
        while (curr!=null){
            MapEntry<K,V> entry = curr.getData();
            if(entry.hash == h && entry.key.equals(key)){
                entry.value = value;
                return;
            }
            curr = curr.getNext();
        }
        llMapBuckets[i].addNodeToTail(new MapEntry<>(key,value,h));
        size++;
        if(size>= capacity * loadFactor){
            this.rehash();
        }
    }


    private void rehash() {
        DoublyLinkedLst<MapEntry<K,V>>[] oldBuckets = llMapBuckets;
        capacity = capacity * 2;
        DoublyLinkedLst<MapEntry<K,V>>[] newBuckets = (DoublyLinkedLst<MapEntry<K,V>>[]) new DoublyLinkedLst[capacity];
        for (DoublyLinkedLst<MapEntry<K, V>> bucket : oldBuckets){
            if (bucket == null) continue;
            Node<MapEntry<K,V>> curr = bucket.getHeadNode();
            while (curr!=null){
                MapEntry<K,V> entry = curr.getData();
                int newIndex = entry.hash % capacity;
                if(newBuckets[newIndex] == null){
                    newBuckets[newIndex] = new DoublyLinkedLst<>();
                }
                newBuckets[newIndex].addNodeToTail(entry);
                curr = curr.getNext();
            }
        }
        llMapBuckets = newBuckets;
    }

    public V getVal(K key){
        if(key==null) return null;
        int h = key.hashCode() & 0x7fffffff;
        int i = h % capacity;
        DoublyLinkedLst<MapEntry<K, V>> bucket = llMapBuckets[i];
        if (bucket == null) return null;
        Node<MapEntry<K, V>> curr = bucket.getHeadNode();
        while (curr != null) {
            MapEntry<K, V> entry = curr.getData();
            if (entry.hash == h && entry.key.equals(key)) {
                return entry.value;
            }
            curr = curr.getNext();
        }
        return null;
    }

    public void remove(K key){
        if(key==null) return;
        int h = key.hashCode() & 0x7fffffff;
        int i = h % capacity;
        DoublyLinkedLst<MapEntry<K, V>> bucket = llMapBuckets[i];
        if (bucket == null) return;
        Node<MapEntry<K, V>> curr = bucket.getHeadNode();
        while(curr!=null){
            MapEntry<K, V> entry = curr.getData();
            if(entry.key.equals(key)){
                bucket.removeNode(curr);
                size--;
                return;
            }
            curr = curr.getNext();
        }
    }



}
