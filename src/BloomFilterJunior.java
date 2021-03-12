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

    /* Instance variables */
    private boolean[] table;

    public BloomFilterJunior(int capacity) {
        /* TODO */
    }

    public void insert(String value) {
        /* TODO */
    }

    public boolean lookup(String value) {
        /* TODO */
        return false;
    }

    /**
     * Base-256 hash function.
     *
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
     *
     * @param value string to hash
     * @return hash value
     */
    private int hashCRC(String value) {
        /* TODO: Copy and paste from your HashTable */
        return -1;
    }

    /**
     * Horner's hash function.
     *
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
