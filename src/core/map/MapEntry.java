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
}

