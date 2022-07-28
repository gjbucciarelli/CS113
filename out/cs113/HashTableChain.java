
import java.util.*;

/** Hash table implementation using chaining. */
public class HashTableChain <K, V> implements Map<K, V> {

    /** Contains key-value pairs for a hash table. */
    private static class Entry<K, V> {
        /**  The key */
        private final K key;
        /** The value */
        private V value;

        /** Creates a new key-value pair.
         * @param key The key
         * @param value The value
         */
        public Entry(K key, V value){
            this.key = key;
            this.value = value;
        }

        /** Retrieves the key.
         * @return The key
         */
        public K getKey() {
            return key;
        }

        /** Retrieves the value
         * @return The value
         */
        public V getValue() {
            return value;
        }

        /** Sets the value
         * @param val The new value
         */
        public V setValue(V val) {
            V oldVal = value;
            value = val;
            return oldVal;
        }
    }// Entry class

    /** The table */
    private LinkedList<Entry<K, V>>[] table;
    /** The number of keys */
    private int numKeys;
    /** The capacity */
    private static final int CAPACITY = 101;
    /** The maximum lodad factor */
    private static final double LOAD_THRESHOLD = 3.0;

    // Constructor
    public HashTableChain() {
        table = new LinkedList[CAPACITY];
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean containsKey(Object key) {
        return false;
    }

    @Override
    public boolean containsValue(Object value) {
        return false;
    }

    /** Method get for class HashtableChain
     * @param key The key being sought
     * @return The value associated with this key if found;
     *          otherwise, null
     */
    @Override
    public V get(Object key) {
        int index = key.hashCode() % table.length;
        if (index < 0)
            index += table.length;
        if(table[index] == null)
            return null;          // key is not in the table

        // Search the list at table[index} to find the key
        for (Entry<K, V> nextItem : table[index]) {
            if (nextItem.getKey().equals(key))
                return nextItem.getValue();
        }
        // assert: key is not in the table.
        return null;
    }

    /** Method put for class HashtabbleChain.
     * @post This key-value pair is inserted in the
     *      table and numKeys is incremented. If the key is already
     *      in the table, its value is changed to the argument
     *      value and numKeys is not changed.
     * @param key The key of item being inserted
     * @param value The value for this key
     * @return The old value associated with this key if found;
     *          otherwise null.
     */
    @Override
    public V put(K key, V value) {
        int index = key.hashCode() % table.length;
        if (index < 0)
            index += table.length;
        if (table[index] == null) {
            // Create a new linked list at table[index]
            table[index] = new LinkedList<>();
        }

        // Search the list at table[index] to find the key.
        for (Entry<K, V> nextItem : table[index]){
            // If the search is successful, replace the old value.
            if (nextItem.getKey().equals(key)) {
                // Replace value for this key.
                V oldVal = nextItem.getValue();
                nextItem.setValue(value);
                return oldVal;
            }
        }
        // assert: key is not in the table, add new item.
        table[index].addFirst(new Entry<>(key, value));
        numKeys++;
        if (numKeys > (LOAD_THRESHOLD * table.length))
            return null;
        //    rehash();
        return null;
    }

    @Override
    public V remove(Object key) {
        return null;
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {

    }

    @Override
    public void clear() {

    }

    @Override
    public Set<K> keySet() {
        return null;
    }

    @Override
    public Collection<V> values() {
        return null;
    }

    @Override
    public Set<Map.Entry<K, V>> entrySet() {
        return null;
    }




}// Hashtablechain
