import org.junit.*;
import static org.junit.Assert.*;

public class HashTableTest {

    HashTable hash1;
    HashTable hash2;
    HashTable hash3;

    @Before
    public void setUp(){
        hash1 = new HashTable(5);
        String[] words1 = {"mar", "m", "ma", "Marina", "marina", "m"};
        for (String word : words1) { hash1.insert(word); }

        hash2 = new HashTable(8);
        String[] words2 = {"diane", "li"};
        for (String word : words2) { hash2.insert(word); }

        hash3 = new HashTable();
    }

    @Test (expected = IllegalArgumentException.class)
    public void testConstructorThrowsIAE() { HashTable hash4 = new HashTable(4); }

    @Test (expected = NullPointerException.class)
    public void testInsertThrowsNPE() { hash1.insert(null); }

    @Test
    public void testInsert() {
        assertTrue(hash2.insert("no middle"));
        assertFalse(hash2.insert("no middle"));
        assertTrue(hash2.insert("il"));
    }

    @Test (expected = NullPointerException.class)
    public void testDeleteThrowsNPE() { hash2.delete(null); }

    @Test
    public void testDelete() {
        assertTrue(hash1.delete("mar"));
        assertFalse(hash1.lookup("mar"));
        assertTrue(hash2.delete("diane"));
        assertFalse(hash3.delete("0"));
    }

    @Test (expected = NullPointerException.class)
    public void testLookupThrowsNPE() { hash3.lookup(null); }

    @Test
    public void testLookup() {
        assertTrue(hash1.lookup("mar"));
        assertTrue(hash1.lookup("mar"));
        assertFalse(hash1.lookup("MAR"));
        assertFalse(hash3.lookup("77"));
    }

    @Test
    public void testSize() {
        assertEquals(5, hash1.size());
        hash1.insert("mar");
        assertEquals(5, hash1.size());
        assertTrue(hash1.insert("MAR"));
        hash1.insert("MA");
        assertEquals(7, hash1.size());

        assertEquals(2, hash2.size());

        assertEquals(0, hash3.size());
        String[] words3 = {"dsc", "d", "s", "c", "dsc 30", "dsc30", "DSC30", "Dsc30"};
        for (String word : words3) { hash3.insert(word); }
        assertEquals(8, hash3.size());
    }

    @Test
    public void testCapacity() {
        assertEquals(10, hash1.capacity());
        assertEquals(8, hash2.capacity());

        assertEquals(15, hash3.capacity());
        String[] words3 = {"dsc", "d", "s", "c", "dsc 30", "dsc30", "DSC30", "Dsc30"};
        for (String word : words3) { hash3.insert(word); }
        assertEquals(15, hash3.capacity());
        hash3.insert("data");
        assertEquals(15, hash3.capacity());
        hash3.insert("science");
        assertEquals(30, hash3.capacity());
    }

    @Test
    public void testGetStatsLog() {
        String message3 = "";
        assertEquals(message3, hash3.getStatsLog());

        String message1 = "Before rehash # 1: load factor 0.0, 0 collision(s).\n" +
                "Before rehash # 2: load factor 0.5, 1 collision(s).\n";
        assertEquals(message3, hash3.getStatsLog());

        String message2 = "";
        assertEquals(message2, hash2.getStatsLog());
    }
}