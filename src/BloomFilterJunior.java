/*
 * NAME: Diane Li
 * PID: A15773774
 */

/**
 * Simple prototype of a bloom filter backed by a boolean array to simulate the hash table,
 * and using three hash functions.
 * @author Diane Li
 * @since 03/11/2021
 */
public class BloomFilterJunior {

    /* Constants */
    private static final int MIN_INIT_CAPACITY = 50;
    private static final int BASE256_LEFT_SHIFT = 8;
    private static final int HORNERS_BASE = 27;
    private static final int LEFT_SHIFT = 5;
    private static final int RIGHT_SHIFT = 27;

    /* Instance variables */
    private boolean[] table;

    public BloomFilterJunior(int capacity) {
        if (capacity < MIN_INIT_CAPACITY) { throw new IllegalArgumentException(); }
        else { this.table = new boolean[capacity]; }
    }

    public void insert(String value) {
        if (value == null) { throw new NullPointerException(); }

        this.table[hashBase256(value)] = true;
        this.table[hashCRC(value)] = true;
        this.table[hashHorners(value)] = true;
    }

    public boolean lookup(String value) {
        if (value == null) { throw new NullPointerException(); }

        return (table[hashBase256(value)] && table[hashCRC(value)] && table[hashHorners(value)]);
    }

    /**
     * Base-256 hash function.
     * @param value string to hash
     * @return hash value
     */
    private int hashBase256(String value) {
        int hash = 0;
        for (char c : value.toCharArray()) {
            hash = ((hash << BASE256_LEFT_SHIFT) + c) % table.length;
        }
        return Math.abs(hash % table.length);
    }

    /**
     * Simplified CRC hash function.
     * @param value string to hash
     * @return hash value
     */
    private int hashCRC(String value) {
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
     * Horner's hash function.
     * @param value string to hash
     * @return hash value
     */
    private int hashHorners(String value) {
        int hash = 0;
        for (char c : value.toCharArray()) {
            hash = (hash * HORNERS_BASE + c) % table.length;
        }
        return Math.abs(hash % table.length);
    }

}
