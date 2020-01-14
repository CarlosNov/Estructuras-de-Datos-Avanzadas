package material.maps;

import java.util.*;

/**
 * Separate chaining table implementation of hash tables. Note that all
 * "matching" is based on the equals method.
 *
 * @author A. Duarte, J. Vélez, J. Sánchez-Oro, JD. Quintana
 */
public class HashTableMapSC<K, V> implements Map<K, V> {

    private class HashEntry<T, U> implements Entry<T, U> {

        protected T key;
        protected U value;

        public HashEntry(T k, U v) {
            key = k;
            value = v;
        }

        @Override
        public U getValue() {
            return value;
        }

        @Override
        public T getKey() {
            return key;
        }

        public U setValue(U val) {
            U oldValue = value;
            value = val;
            return oldValue;
        }

        @Override
        public boolean equals(Object o) {
            if (o.getClass() != this.getClass()) {
                return false;
            }

            HashEntry<T, U> ent;
            try {
                ent = (HashEntry<T, U>) o;
            } catch (ClassCastException ex) {
                return false;
            }
            return (ent.getKey().equals(this.key))
                    && (ent.getValue().equals(this.value));
        }
    }

    private class HashTableMapIterator<T, U> implements Iterator<Entry<T, U>>
    {
        private int pos;
        private List<HashEntry<T, U>>[] bucket;
        private List<HashEntry<T, U>> currentList;

        public HashTableMapIterator(List<HashEntry<T, U>>[] map, int numElems) {
            this.bucket = map;
            this.currentList = null;
            if (numElems == 0)
            {
                this.pos = bucket.length;
            }
            else {
                this.pos = 0;
                goToNextPosition(0);
            }
        }

        private void goToNextPosition(int start) {
            this.pos = start;
            while ((this.pos < bucket.length) && (this.bucket[this.pos] == null) && (this.bucket[this.pos].size()==0)) {
                this.pos++;
            }
        }

        @Override
        public boolean hasNext() {
            return (this.pos < this.bucket.length || currentList.size() != 0);
        }

        @Override
        public Entry<T, U> next() {
            if (hasNext()) {
                Entry entry;
                if(this.currentList.size() != 0)
                {
                    entry = this.currentList.get(0);
                    this.currentList.remove(0);
                }
                else
                {
                    goToNextPosition(this.pos + 1);
                    this.currentList = this.bucket[pos];
                    entry = this.currentList.get(0);
                    this.currentList.remove(0);
                }
                return entry;
            } else {
                throw new IllegalStateException("The map has not more elements");
            }
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("Not implemented.");
        }
    }

    private class HashTableMapKeyIterator<T, U> implements Iterator<T> {


        public HashTableMapIterator<T, U> it;

        public HashTableMapKeyIterator(HashTableMapIterator<T, U> it) {
            this.it = it;
        }

        @Override
        public T next() {
            return it.next().getKey();
        }

        @Override
        public boolean hasNext() {
            return it.hasNext();
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("Not implemented.");
        }
    }

    private class HashTableMapValueIterator<T, U> implements Iterator<U> {

        public HashTableMapIterator<T, U> it;

        public HashTableMapValueIterator(HashTableMapIterator<T, U> it) {
            this.it = it;
        }

        @Override
        public U next() {
            return it.next().getValue();
        }

        @Override
        public boolean hasNext() {
            return it.hasNext();
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("Not implemented.");
        }
    }

    protected int n; // number of entries in the dictionary
    protected int prime, capacity; // prime factor and capacity of bucket array
    protected long scale, shift; // the shift and scaling factors
    protected LinkedList<HashEntry<K, V>>[] bucket;// bucket array

    /**
     * Creates a hash table with prime factor 109345121 and capacity 1000.
     */
    public HashTableMapSC() {
        this(109345121, 1000);
    }

    /**
     * Creates a hash table with prime factor 109345121 and given capacity.
     *
     * @param cap initial capacity
     */
    public HashTableMapSC(int cap) {
        this(109345121, cap);
    }

    /**
     * Creates a hash table with the given prime factor and capacity.
     *
     * @param p   prime number
     * @param cap initial capacity
     */
    public HashTableMapSC(int p, int cap) {
        this.n = 0;
        this.prime = p;
        this.capacity = cap;
        this.bucket = (LinkedList<HashEntry<K,V>>[]) new LinkedList[capacity]; // safe cast
        Random rand = new Random();
        this.scale = rand.nextInt(prime - 1) + 1;
        this.shift = rand.nextInt(prime);
    }

    /**
     * Hash function applying MAD method to default hash code.
     *
     * @param key Key
     * @return the hash value
     */
    protected int hashValue(K key) {
        return (int) ((Math.abs(key.hashCode() * scale + shift) % prime) % capacity);
    }


    @Override
    public int size(){
        return n;
    }

    @Override
    public boolean isEmpty() {
        return (n == 0);
    }

    @Override
    public V get(K key) {
        checkKey(key);

        int hashcode = hashValue(key);

        if(bucket[hashcode] != null)
        {
            for(HashEntry entry : bucket[hashcode])
            {
                if(key.equals(entry.getKey()))
                {
                    return (V) entry.getValue();
                }
            }
        }
        return null;
    }

    @Override
    public V put(K key, V value) {
        checkKey(key);

        int hashcode = hashValue(key);

        if (bucket[hashcode] == null)
        {
            bucket[hashcode] = new LinkedList<>();
        }
        else
        {
            for(HashEntry entry : bucket[hashcode])
            {
                if(key.equals(entry.getKey()))
                {
                    V oldValue = (V) entry.getValue();
                    entry.setValue(value);
                    return oldValue;
                }
            }
        }
        bucket[hashcode].add(new HashEntry<>(key, value));
        n++;
        return null;
    }

    @Override
    public V remove(K key) {
        checkKey(key);

        int hashcode = hashValue(key);

        if(bucket[hashcode] != null)
        {
            for(HashEntry entry : bucket[hashcode])
            {
                if(key.equals(entry.getKey()))
                {
                    V toReturn = (V) entry.getValue();
                    bucket[hashcode].remove(entry);
                    n--;
                    return toReturn;
                }
            }
        }
        return null;
    }


    @Override
    public Iterator<Entry<K, V>> iterator() {
        return new HashTableMapIterator<K,V>(this.bucket, 1);
    }

    @Override
    public Iterable<K> keys() {
        LinkedList<K> keys = new LinkedList<>();

        for (LinkedList<HashEntry<K,V>> entries: bucket){
            if (entries != null){
                for (HashEntry<K,V> entry: entries){
                    keys.add (entry.key);
                }
            }
        }
        return Collections.unmodifiableCollection(keys);
    }

    @Override
    public Iterable<V> values() {
        LinkedList<V> values = new LinkedList<>();

        for (LinkedList<HashEntry<K,V>> entries: bucket){
            if (entries != null){
                for (HashEntry<K,V> entry: entries){
                    values.add(entry.value);
                }
            }
        }
        return Collections.unmodifiableCollection(values);
    }

    @Override
    public Iterable<Entry<K, V>> entries() {
        LinkedList<Entry<K,V>> e = new LinkedList<>();

        for (LinkedList<HashEntry<K,V>> entries: bucket){
            if (entries != null){
                e.addAll(entries);
            }
        }
        return Collections.unmodifiableCollection(e);
    }

    /**
     * Determines whether a key is valid.
     *
     * @param k Key
     */
    protected void checkKey(K k) {
        if (k == null) {
            throw new IllegalStateException("Invalid key: null.");
        }
    }


    /**
     * Increase/reduce the size of the hash table and rehashes all the entries.
     */
    protected void rehash(int newCap) {

    }
}
