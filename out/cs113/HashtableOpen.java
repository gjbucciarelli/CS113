
// Gina Bucciarelli
// CS 113 Spring 2022
// Make up work



import java.util.*;

/** Hash table implementation using open addressing. */
public class HashtableOpen <K, V> implements Map<K, V>{

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


    //Data Fields
    private Entry<K, V>[] table;                     // The hash table array
    private static final int START_CAPACITY = 101;   // The initial capacity
    private double LOAD_THRESHOLD = 0.75;            // The maximum load factor
    private int numKeys;                             // The number of keys in the table
    // excluding deleted
    private int numDeletes;                          // The number of keys deleted.
    private final Entry<K, V> DELETED =              // A special object to indicate
            new Entry<>(null, null);                    // that an entry has been deleted


    /** Finds either the target key or the first empty slot in the
     *  search chain using linear probing.
     * @pre The table is not full.
     * @param key the key of the target object
     * @return The position of the target or the first empty slot if
     *          the target is not in the table.
     */
    private int find(Object key){
        // Calculate the starting index.
        int index = key.hashCode() % table.length;
        if (index < 0)
            index += table.length;      // Make it positive.

        // Increment index until an empty slot is reached
        // or the key is found.
        while ((table[index] != null) && (!key.equals(table[index].getKey()))) {
            index++;
            // Check for wraparound.
            if (index >= table.length)
                index = 0;     // Wrap around.
        }
        return index;
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

    /** Method get for class HashTableChain
     * @param key The key being sought
     * @return the value associated with this key if found;
     *         otherwise, null
     */

    @Override
    public V get(Object key) {
        // Find the first table element that is empty
        // or the table element that contains the key.
        int index = find(key);

        // If the search is successful, return the value.
        if (table[index] != null)
            return table[index].getValue();
        else
            return null;   // key not found.
    }

    /** Method put for class HashtableOpen
     * @post This key-value pair is inserted in the
     *      table and numKeys is incremented. If the key is already
     *      in the table, its value is changed to the argument
     *      value and numKeys is not changed. If the LOAD_THRESHOLD
     *      is exceeded, the table is expanded.
     * @param key The key of item being inserted
     * @param value The value for this key
     * @return Old value associated with this key if found;
     *         otherwise, null.
     */
    @Override
    public V put(K key, V value) {
        // Find the first table element that is empty
        // or the table element that contains the key.o
        int index = find(key);

        // If an empty element was found, insert new entry.
        if (table[index] == null) {
            table[index] = new Entry<>(key, value);
            numKeys++;
            // Check whether rehash is needed.
            double loadFactor =
                    (double) (numKeys + numDeletes) / table.length;
            if (loadFactor > LOAD_THRESHOLD)
                rehash();
            return null;
        }
        // assert: table element that contains the key was found.
        // Replace value for this key.
        V oldVal = table[index].getValue();
        table[index].setValue(value);
        return oldVal;
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

    /** Expands table size when load Factor exceeds LOAD_THRESHOLD
     * @post The size of the table is doubled and is an odd integer.
     *       Each non-deleted entry from the original table is
     *       reinserted into the expanded table.
     *       The value of numKeys is reset to the number of items
     *       actually inserted; numDeletes is reset to 0.
     */
    private void rehash() {
        // Save a reference to oldTable.
        Entry<K, V>[] oldTable = table;
        // Double capacity of this table.
        table = new Entry[2 * oldTable.length + 1];

        // Reinsert all items in oldTable into expanded table.
        numKeys = 0;
        numDeletes = 0;
        for (int i = 0; i < oldTable.length; i++) {
            if ((oldTable[i] != null) && (oldTable[i] != DELETED)) {
                // Insert entry in expanded table
                put(oldTable[i].getKey(), oldTable[i].getValue());
            }
        }
    }



}// HashtableOpen Class
