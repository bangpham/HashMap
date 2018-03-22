import java.util.Set;
import java.util.List;

/**
 * Interface explaining the methods required for your implementation of HashMap.
 *
 * DO NOT ALTER THIS FILE!!
 *
 * @version 1.0
 * @author CS 1332 TAs
 */
public interface HashMapInterface<K, V> {
    int INITIAL_CAPACITY = 10;
    double MAX_LOAD_FACTOR = 0.75;

    /**
     * Adds the given key-value pair to the HashMap.
     * If the key is already in the HashMap, then replace the value with the new
     * value.
     * 
     * In the case of a collision, use linear probing as your resolution
     * strategy.
     *
     * Check to see if the backing array needs to be regrown BEFORE
     * adding. For example, if the HashMap has a backing array of size 10, and 7
     * elements in it, you should regrow it at the start of the next put
     * operation (even if it is a key that is already in the hash map).
     * 
     * @param key key to add into the HashMap
     * @param value value to add into the HashMap
     * @throws IllegalArgumentException if key or value is null
     * @return null if the key was not already in the map or was deleted.
     * Otherwise, return the old value associated with it
     */
    V put(K key, V value);

    /**
     * Removes the entry associated with the given key.
     *
     * When removing, do not set the index in the array to {@code null}, nor set
     * the key and value to {@code null}; mark the entry as removed.
     * 
     * @param key the key to remove
     * @throws IllegalArgumentException if key is null
     * @throws java.util.NoSuchElementException if the key does not exist
     * @return the value previously associated with the key
     */
    V remove(K key);

    /**
     * Gets the value associated with the given key.
     * 
     * @param key the key to search for
     * @throws IllegalArgumentException if key is null
     * @throws java.util.NoSuchElementException if the key is not in the map
     * @return the value associated with the given key
     */
    V get(K key);

    /**
     * Counts the number of entries containing the specified value.
     *
     * @param value the value to count
     * @throws IllegalArgumentException if the value is null
     * @return the number of entries with the given value
     */
    int count(V value);

    /**
     * Returns whether or not the key is in the map.
     * 
     * @param key the key to search for
     * @throws IllegalArgumentException if key is null
     * @return whether or not the key is in the map
     */
    boolean containsKey(K key);

    /**
     * Clears the array and resets it to the default size.
     */
    void clear();

    /**
     * Returns the number of elements in the map.
     * 
     * @return number of elements in the HashMap
     */
    int size();

    /**
     * Returns a Set view of the keys contained in this map.
     * Use {@code java.util.HashSet}.
     *
     * @return set of keys in this map
     */
    Set<K> keySet();

    /**
     * Returns a List view of the values contained in this map.
     * Use any class that implements the List interface.
     *
     * The order of values in your list should be the same as they appear in
     * your backing array.
     *
     * @return list of values in this map
     */
    List<V> values();

    /**
     * Resize the backing array to {@code length}. This may mean that after
     * resizing, the load factor may exceed {@code MAX_LOAD_FACTOR}. In this
     * case, do NOT regrow the table; keep it at {@code length}.
     *
     * Remember that you cannot just simply copy the entries over to the new
     * array.
     *
     * @param length new length of the backing table
     * @throws IllegalArgumentException if length is less than the number of
     * entries in the hash map.
     */
    void resizeBackingArray(int length);

    /**
     * DO NOT USE THIS METHOD IN YOUR CODE.  IT IS FOR TESTING PURPOSES ONLY.
     * 
     * @return the backing array of the data structure.
     */
    MapEntry<K, V>[] getArray();
}
