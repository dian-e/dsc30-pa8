/*
 * Name: Diane Li
 * PID: A15773774
 */

import java.util.Arrays;

/**
 * HashTable that uses linear probing (h(k, i) = (h(k) + i) % m) to handle collisions
 * 
 * @author Diane
 * @since 03/04/2021
 */
public class HashTable implements IHashTable {

    /* the bridge for lazy deletion */
    private static final String bridge = new String("[BRIDGE]".toCharArray());
    private static final int DEFAULT_INIT_CAPACITY = 15;
    private static final int MIN_INIT_CAPACITY = 5;
    private static final double LF_REHASH = 0.55;
    private static final int REHASHED_SIZE = 2;
    private static final int LEFT_SHIFT = 5;
    private static final int RIGHT_SHIFT = 27;

    /* instance variables */
    private int size; // number of elements stored
    private String[] table; // data table
    private String statsLog = "";
    private int countRehashes = 1;
    private int countCollisions = 0;

    /**
     * Initialize a hash table with default 15 total capacity and all instance variables
     * @throws IllegalArgumentException if capacity <5 (minimum initial total capacity)
     */
    public HashTable() {
        this(DEFAULT_INIT_CAPACITY);
    }

    /**
     * Initialize a hash table with given total capacity and all instance variables
     * @param capacity for the initial table
     * @throws IllegalArgumentException if capacity <5 (minimum initial total capacity)
     */
    public HashTable(int capacity) {
        if (capacity < MIN_INIT_CAPACITY) { throw new IllegalArgumentException(); }
        this.table = new String[capacity];
        this.size = 0;
    }

    /**
     * Insert the string value into the hash table.
     * @param value value to insert
     * @throws NullPointerException if value is null
     * @return true if the value was inserted, false if the value was already present
     */
    @Override
    public boolean insert(String value) {
        if (value == null) { throw new NullPointerException(); }

        int bucket = hashString(value);
        int bucketsProbed = 0;
        String elem;

        while (bucketsProbed < this.capacity()) {
            // insert item into next empty bucket or return false if already existing
            elem = this.table[bucket];
            if (elem == null || elem == bridge) {
                // rehashes before inserting if necessary
                double loadFactor = (double)this.size() / this.capacity();
                if (loadFactor > LF_REHASH) {
                    rehash();
                } else {
                    this.table[bucket] = value;
                    this.size++;
                    this.countCollisions += bucketsProbed;
                    return true;
                }
            } else if (elem.equals(value)) {
                return false;
            }

            // increment bucket index and number of buckets probed
            bucket = (bucket + 1) % this.capacity();
            bucketsProbed++;
        }
        return false;
    }

    /**
     * Delete the given value from the hash table.
     * @param value value to delete
     * @throws NullPointerException if value is null
     * @return true if the value was deleted, false if the value was not found
     */
    @Override
    public boolean delete(String value) {
        if (value == null) { throw new NullPointerException(); }

        int bucket = hashString(value);
        int bucketsProbed = 0;

        while (this.table[bucket] != null && bucketsProbed < this.capacity()) {
            if (this.table[bucket] == value) {
                this.table[bucket] = bridge;
                this.size--;
                return true;
            }

            // increment bucket index and number of buckets probed, update element
            bucket = (bucket + 1) % this.capacity();
            bucketsProbed++;
        }
        return false;
    }

    /**
     * Check if the given value is present in the hash table.
     * @param value value to look up
     * @throws NullPointerException if value is null
     * @return true if the value was found, false if the value was not found
     */
    @Override
    public boolean lookup(String value) {
        if (value == null) { throw new NullPointerException(); }

        int bucket = hashString(value);
        int bucketsProbed = 0;
        String elem = this.table[bucket];

        while (elem != null && bucketsProbed < this.capacity()) {
            if (elem.equals(value)) { return true; }

            // increment bucket index and number of buckets probed, update element
            bucket = (bucket + 1) % this.capacity();
            bucketsProbed++;
            elem = this.table[bucket];
        }
        return false;
    }

    /**
     * Return the number of elements currently stored in the hashtable.
     * @return number of elements
     */
    @Override
    public int size() { return this.size; }

    /**
     * Get the total capacity of the hash table.
     * @return total capacity
     */
    @Override
    public int capacity() { return this.table.length; }

    /**
     * Returns the stats log of the number of times the table has been rehashed, the load factor in
     * the table (before rehashing) rounded to 2 decimal places, adn the number of collisions
     * detected during insertions (before rehashing)
     * @return total capacity
     */
    public String getStatsLog() { return this.statsLog; }

    /**
     * Rehashes the table by doubling the capacity and iterating through the old table to re-insert
     * event valid element to the new table
     */
    private void rehash() {
        double loadFactor = this.size() / this.capacity();
        this.statsLog += "Before rehash # " + this.countRehashes + ": load factor " +
                String.format("%.2f", loadFactor) + ", " +
                this.countCollisions + " collision(s).\n";

        String[] oldTable = this.table;
        this.table = new String[this.capacity() * REHASHED_SIZE];
        this.size = 0;
        for (String elem : oldTable) {
            if (elem != null && elem != bridge)  {
                this.insert(elem);
            }
        }

        this.countRehashes++;
        this.countCollisions = 0;
    }

    /**
     * Returns the hash value of the given string using the Simplified CRC hash function
     * @param value to be hashed
     */
    private int hashString(String value) {
        int hashValue = 0;
        for (int i = 0; i < value.length(); i++) {
            int leftShiftedValue = hashValue << LEFT_SHIFT; // left shift
            int rightShiftedValue = hashValue >>> RIGHT_SHIFT; // right shift
            // | is bitsize OR, ^ is bitwise XOR
            hashValue = (leftShiftedValue | rightShiftedValue) ^ value.charAt(i);
        }
        return Math.abs(hashValue % this.table.length);
    }

    /**
     * Returns the string representation of the hash table.
     * This method internally uses the string representation of the table array.
     * DO NOT MODIFY. You can use it to test your code.
     * @return string representation
     */
    @Override
    public String toString() {
        return Arrays.toString(table);
    }
}
