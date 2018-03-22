import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class HashMapStudentTests {
    private static final int TIMEOUT = 500;
    private HashMapInterface<Integer, String> map;
    private MapEntry<Integer, String>[] expected;

    @Test(timeout = TIMEOUT)
    public void testConstructors() {
        map = new HashMap<>();
        assertEquals(HashMapInterface.INITIAL_CAPACITY, map.getArray().length);
        assertEquals(0, map.size());

        map = new HashMap<>(101);
        assertEquals(101, map.getArray().length);
        assertEquals(0, map.size());
    }

    @Test(timeout = TIMEOUT)
    public void testPut() {
        map = new HashMap<>(10);
        map.put(1, "one");
        map.put(2, "two");
        map.put(3, "three");
        map.put(12, "twelve");
        map.put(11, "eleven");
        map.put(3, "new three");

        expected = (MapEntry<Integer, String>[]) new MapEntry[10];
        expected[1] = new MapEntry<>(1, "one");
        expected[2] = new MapEntry<>(2, "two");
        expected[3] = new MapEntry<>(3, "new three");
        expected[4] = new MapEntry<>(12, "twelve");
        expected[5] = new MapEntry<>(11, "eleven");
        assertArrayEquals(expected, map.getArray());
    }

    @Test(timeout = TIMEOUT)
    public void testRemove() {
        map = new HashMap<>(6);
        map.put(1, "one");
        map.put(2, "two");
        map.put(3, "three");
        assertEquals("two", map.remove(2));

        expected = (MapEntry<Integer, String>[]) new MapEntry[6];
        expected[1] = new MapEntry<>(1, "one");
        MapEntry<Integer, String> removedEntry = new MapEntry<>(2, "two");
        removedEntry.setRemoved(true);
        expected[2] = removedEntry;
        expected[3] = new MapEntry<>(3, "three");
        assertArrayEquals(expected, map.getArray());
    }

    @Test(timeout = TIMEOUT)
    public void testGet() {
        map = new HashMap<>(6);
        map.put(1, "one");
        map.put(2, "two");
        map.put(3, "three");
        assertEquals("three", map.get(3));
    }

    @Test(timeout = TIMEOUT)
    public void testCount() {
        map = new HashMap<>(10);
        map.put(1, "yes");
        map.put(2, "no");
        map.put(3, "yes");
        map.put(9, "yes");
        assertEquals(3, map.count("yes"));
    }

    @Test(timeout = TIMEOUT)
    public void testContains() {
        map = new HashMap<>(6);
        map.put(1, "one");
        map.put(2, "two");
        map.put(3, "three");
        assertTrue(map.containsKey(2));
        assertFalse(map.containsKey(8));
    }

    @Test(timeout = TIMEOUT)
    public void testClear() {
        map = new HashMap<>(6);
        map.put(1, "one");
        map.put(2, "two");
        map.put(3, "three");
        map.clear();

        assertEquals(HashMapInterface.INITIAL_CAPACITY, map.getArray().length);
        assertEquals(0, map.size());
    }

    @Test(timeout = TIMEOUT)
    public void testSize() {
        map = new HashMap<>(6);
        map.put(1, "one");
        assertEquals(1, map.size());
        map.put(2, "two");
        assertEquals(2, map.size());
        map.put(3, "three");
        assertEquals(3, map.size());
        map.remove(2);
        assertEquals(2, map.size());
    }

    @Test(timeout = TIMEOUT)
    public void testKeySet() {
        map = new HashMap<>(10);
        map.put(1, "one");
        map.put(5, "five");
        map.put(13, "thirteen");

        Set<Integer> expectedKeys = new HashSet<>();
        expectedKeys.add(13);
        expectedKeys.add(5);
        expectedKeys.add(1);
        assertEquals(expectedKeys, map.keySet());
    }

    @Test(timeout = TIMEOUT)
    public void testValues() {
        map = new HashMap<>(10);
        map.put(1, "one");
        map.put(5, "five");
        map.put(13, "thirteen");

        List<String> expectedValues = new ArrayList<>();
        expectedValues.add("one");
        expectedValues.add("thirteen");
        expectedValues.add("five");
        assertEquals(expectedValues, map.values());
    }

    @Test(timeout = TIMEOUT)
    public void testResize() {
        map = new HashMap<>(10);
        map.put(5, "five");
        map.put(13, "thirteen");
        map.resizeBackingArray(20);

        expected = (MapEntry<Integer, String>[]) new MapEntry[20];
        expected[5] = new MapEntry<>(5, "five");
        expected[13] = new MapEntry<>(13, "thirteen");
        assertArrayEquals(expected, map.getArray());
    }
}
