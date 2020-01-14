package material.maps;

/**
 * @param <K> The hey
 * @param <V> The stored value
 */
public class HashTableMapDH<K, V> extends AbstractHashTableMap<K, V> {

    public HashTableMapDH(int size) {
        super(size);
    }

    public HashTableMapDH() {
        super();
    }

    public HashTableMapDH(int p, int cap) {
        super(p, cap);
    }

    protected int offset(K key, int i) {
        this.checkKey(key);
        return secondHashValue(key) * i;
    }

    public int secondHashValue(K key) {
        return prime - (key.hashCode() % prime);
    }
}
