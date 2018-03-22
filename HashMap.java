import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.HashSet;

/**
 * Your implementation of HashMap.
 * 
 * @author Bang Pham
 * @version 1.0
 */
public class HashMap<K, V> implements HashMapInterface<K, V> {

    // Do not make any new instance variables.
    private MapEntry<K, V>[] backingArray;
    private int size;

    /**
     * Create a hash map with no entries. The backing array has an initial
     * capacity of {@code INITIAL_CAPACITY}.
     *
     * Use constructor chaining.
     */
    public HashMap() {
        this(INITIAL_CAPACITY);
    }

    /**
     * Create a hash map with no entries. The backing array has an initial
     * capacity of {@code initialCapacity}.
     *
     * You may assume {@code initialCapacity} will always be positive.
     *
     * @param initialCapacity initial capacity of the backing array
     */
    @SuppressWarnings("unchecked")
    public HashMap(int initialCapacity) {
        backingArray = (MapEntry<K, V>[]) new MapEntry[initialCapacity];
    }

    @Override
    public V put(K key, V value) {
        if (key == null || value == null) {
            throw new IllegalArgumentException("key or value is null");
        }
        if ((size + 1) > (backingArray.length * MAX_LOAD_FACTOR)) {
            resizeBackingArray(backingArray.length * 2 + 3);
        }
        int hash = hash(key, backingArray.length);
        int firstAvailable = -1;
        boolean found = false;
        V temp = null;
        while (backingArray[hash] != null
                && !backingArray[hash].getKey().equals(key)) {
            //memorize index
            if (backingArray[hash].isRemoved() && !found) {
                firstAvailable = hash;
                found = true;
            }
            hash = (hash + 1) % backingArray.length;
        }
        if (backingArray[hash] == null || backingArray[hash].isRemoved()) {
            if (found) {
                hash = firstAvailable;
            }
            backingArray[hash] = new MapEntry<K, V>(key, value);
            size++;
        } else {
            if (!backingArray[hash].isRemoved()) {
                temp = backingArray[hash].getValue();
            } else {
                backingArray[hash].setRemoved(false);
            }
            backingArray[hash].setValue(value);
        }   
        return temp;
    }
    /**
     * Helper method to hash value;
     * @param key key to hash.
     * @param length length of array.
     * @return hash value;
     */
    private int hash(K key, int length) {
        int hash = Math.abs(key.hashCode()) % length;
        return hash;
    }

    @Override
    public V remove(K key) {
        if (key == null) {
            throw new IllegalArgumentException("key is null");
        }
        int count = 0;
        int hash = hash(key, backingArray.length);
        while (backingArray[hash] != null && count < backingArray.length
                && !backingArray[hash].getKey().equals(key)) {
            count++;
            hash = (hash + 1) % backingArray.length;
        }
        if (backingArray[hash] == null
                || !(backingArray[hash].getKey().equals(key))
                || backingArray[hash].isRemoved()) {
            throw new java.util.NoSuchElementException("key does not exist");
        }
        MapEntry<K, V> temp = backingArray[hash];
        backingArray[hash].setRemoved(true);
        size--;
        return temp.getValue();
    }

    @Override
    public V get(K key) {
        if (key == null) {
            throw new IllegalArgumentException("key is null");
        }
        int hash = hash(key, backingArray.length);
        int count = 0;
        while (backingArray[hash] != null
                && !backingArray[hash].getKey().equals(key)
                        && count < backingArray.length) {
            count++;
            hash = (hash + 1) % backingArray.length;
        } 
        if (backingArray[hash] == null || backingArray[hash].isRemoved()
                || !backingArray[hash].getKey().equals(key)) {
            throw new java.util.NoSuchElementException("key does not exist");
        }
        
        return backingArray[hash].getValue();
    }

    @Override
    public int count(V value) {
        if (value == null) {
            throw new IllegalArgumentException("key is null");
        }
        int count = 0;
        for (int i = 0; i < backingArray.length; i++) {
            if (backingArray[i] != null) {
                if (backingArray[i].getValue().equals(value)
                        && !backingArray[i].isRemoved()) {
                    count++;
                }
            }
        }
        return count;
    }

    @Override
    public boolean containsKey(K key) {
        if (key == null) {
            throw new IllegalArgumentException("key is null");
        }
        int count = 0;
        int hash2 = -1;
        int hash = hash(key, backingArray.length);
        while (backingArray[hash] != null && count < backingArray.length
                && !backingArray[hash].getKey().equals(key)) {
            hash = (hash + 1) % backingArray.length;
            count++;
        }
        if (backingArray[hash] != null
                && backingArray[hash].getKey().equals(key)
                && !(backingArray[hash].isRemoved())) {
            return true;
        }
        return false;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void clear() {
        backingArray = (MapEntry<K, V>[]) new MapEntry[INITIAL_CAPACITY];
        size = 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public Set<K> keySet() {
        Set<K> set = new HashSet<K>();
        for (int i = 0; i < backingArray.length; i++) {
            if (backingArray[i] != null
                    && !backingArray[i].isRemoved()) {
                set.add(backingArray[i].getKey());
            }
        }
        return set;
    }

    @Override
    public List<V> values() {
        List<V> list = new ArrayList<V>();
        for (MapEntry<K, V> entry : backingArray) {
            if (entry != null && !entry.isRemoved()) {
                list.add(entry.getValue());
            }
        }
        return list;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void resizeBackingArray(int length) {
        if (length < 1 || length < size) {
            throw new IllegalArgumentException("length is less "
                + "than the number of entries in the hash map");   
        }
        MapEntry<K, V>[] temp = (MapEntry<K, V>[]) new MapEntry[length];
        int hash;
        for (int i = 0; i < backingArray.length; i++) { 
            if (backingArray[i] != null
                    && !(backingArray[i].isRemoved())) {
                hash = hash(backingArray[i].getKey(), temp.length);
                while (temp[hash] != null
                        && !(temp[hash]
                            .getKey().equals(backingArray[i].getKey()))) {
                    hash = (hash + 1) % temp.length;
                }
                temp[hash] = backingArray[i];
            }
        }
        backingArray = temp;
    }

    // DO NOT MODIFY OR USE CODE BEYOND THIS POINT.

    @Override
    public MapEntry<K, V>[] getArray() {
        return backingArray;
    }
}
