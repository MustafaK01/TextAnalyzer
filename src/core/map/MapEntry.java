package core.map;

public class MapEntry<K,V> {

    final K key;
    V value;
    int hash;

    public MapEntry(K key, V value, int hash) {
        this.key = key;
        this.value = value;
        this.hash = hash;
    }

    @Override
    public String toString() {
        return key + " : " + value;
    }

    public V getValue() {
        return value;
    }

    public K getKey() {
        return key;
    }

}

