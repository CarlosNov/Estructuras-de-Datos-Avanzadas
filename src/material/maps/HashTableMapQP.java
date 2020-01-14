package material.maps;

import java.util.function.LongToIntFunction;

/**
 * @param <K> The hey
 * @param <V> The stored value
     */
public class HashTableMapQP<K, V> extends AbstractHashTableMap<K, V> {

    public HashTableMapQP(int size) {
        super(size);
    }

    public HashTableMapQP() {
        super();
    }

    public HashTableMapQP(int p, int cap) {
        super(p,cap);
    }

    @Override
    protected int offset(K key, int i) {
        this.checkKey(key);
        return (int) (5 * i + 6 *i*i);
    }
}
