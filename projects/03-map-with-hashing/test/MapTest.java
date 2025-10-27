import static org.junit.Assert.assertEquals;

import org.junit.Test;

import components.map.Map;

/**
 * JUnit test fixture for {@code Map<String, String>}'s constructor and kernel
 * methods.
 *
 * @author Michael Xu, Jeng Zhuang, Leo Zhuang
 *
 */
public abstract class MapTest {

    /**
     * Invokes the appropriate {@code Map} constructor for the implementation
     * under test and returns the result.
     *
     * @return the new map
     * @ensures constructorTest = {}
     */
    protected abstract Map<String, String> constructorTest();

    /**
     * Invokes the appropriate {@code Map} constructor for the reference
     * implementation and returns the result.
     *
     * @return the new map
     * @ensures constructorRef = {}
     */
    protected abstract Map<String, String> constructorRef();

    /**
     *
     * Creates and returns a {@code Map<String, String>} of the implementation
     * under test type with the given entries.
     *
     * @param args
     *            the (key, value) pairs for the map
     * @return the constructed map
     * @requires <pre>
     * [args.length is even]  and
     * [the 'key' entries in args are unique]
     * </pre>
     * @ensures createFromArgsTest = [pairs in args]
     */
    private Map<String, String> createFromArgsTest(String... args) {
        assert args.length % 2 == 0 : "Violation of: args.length is even";
        Map<String, String> map = this.constructorTest();
        for (int i = 0; i < args.length; i += 2) {
            assert !map.hasKey(args[i]) : ""
                    + "Violation of: the 'key' entries in args are unique";
            map.add(args[i], args[i + 1]);
        }
        return map;
    }

    /**
     *
     * Creates and returns a {@code Map<String, String>} of the reference
     * implementation type with the given entries.
     *
     * @param args
     *            the (key, value) pairs for the map
     * @return the constructed map
     * @requires <pre>
     * [args.length is even]  and
     * [the 'key' entries in args are unique]
     * </pre>
     * @ensures createFromArgsRef = [pairs in args]
     */
    private Map<String, String> createFromArgsRef(String... args) {
        assert args.length % 2 == 0 : "Violation of: args.length is even";
        Map<String, String> map = this.constructorRef();
        for (int i = 0; i < args.length; i += 2) {
            assert !map.hasKey(args[i]) : ""
                    + "Violation of: the 'key' entries in args are unique";
            map.add(args[i], args[i + 1]);
        }
        return map;
    }

    // TODO - add test cases for constructor, add, remove, removeAny, value,
    // hasKey, and size

    /**
     * Tests that the constructor creates an empty map.
     */
    @Test
    public void testConstructor() {
        Map<String, String> testMap = this.constructorTest();
        Map<String, String> expMap = this.constructorRef();
        assertEquals(expMap, testMap);
    }

    /**
     * Tests that adding a new key-value pair to an empty map updates the map
     * correctly.
     */
    @Test
    public void addToEmptyTest() {
        Map<String, String> testMap = this.constructorTest();
        Map<String, String> expMap = this.constructorRef();
        expMap.add("One", "Two");
        testMap.add("One", "Two");
        assertEquals(expMap, testMap);
    }

    /**
     * Tests that adding a new key-value pair updates the map correctly.
     */
    @Test
    public void addToNonEmptyTest() {
        Map<String, String> testMap = this.createFromArgsTest("One", "Two");
        Map<String, String> expMap = this.createFromArgsRef("One", "Two",
                "Three", "Four");
        testMap.add("Three", "Four");
        assertEquals(expMap, testMap);
    }

    /**
     * Tests that removing a specific key-value pair updates the map correctly.
     */
    @Test
    public void removeSimpleTest() {
        Map<String, String> testMap = this.createFromArgsRef("One", "Two",
                "Three", "Four");
        Map<String, String> expMap = this.createFromArgsTest("One", "Two");
        testMap.remove("Three");
        assertEquals(expMap, testMap);
    }

    /**
     * Tests that removing the only key-value pair from a map results in an
     * empty map.
     */
    @Test
    public void removeOnlyElementTest() {
        Map<String, String> testMap = this.createFromArgsTest("One", "Two");
        Map<String, String> expMap = this.constructorRef();
        testMap.remove("One");
        assertEquals(expMap, testMap);
    }

    /**
     * Tests that removing the first key-value pair from a map updates the map
     * correctly.
     */
    @Test
    public void removeFirstElementTest() {
        Map<String, String> testMap = this.createFromArgsTest("One", "Two",
                "Three", "Four");
        Map<String, String> expMap = this.createFromArgsRef("Three", "Four");
        testMap.remove("One");
        assertEquals(expMap, testMap);
    }

    /**
     * Tests that removeAny removes and returns some key-value pair, and the
     * returned pair is actually from the map.
     */
    @Test
    public void removeAnyTest() {
        Map<String, String> testMap = this.createFromArgsRef("One", "Two",
                "Three", "Four");
        Map<String, String> expMap = this.createFromArgsRef("One", "Two",
                "Three", "Four");

        Map.Pair<String, String> removed = testMap.removeAny();
        assertEquals(expMap.value(removed.key()), removed.value());
        expMap.remove(removed.key());

        assertEquals(expMap, testMap);
    }

    /**
     * Tests that removeAny removes and returns the only key-value pair from a
     * single-element map, resulting in an empty map.
     */
    @Test
    public void removeAnySingleElementTest() {
        Map<String, String> testMap = this.createFromArgsRef("One", "Two");
        Map<String, String> expMap = this.createFromArgsRef("One", "Two");

        Map.Pair<String, String> removed = testMap.removeAny();
        assertEquals(expMap.value(removed.key()), removed.value());
        expMap.remove(removed.key());

        assertEquals(expMap, testMap);
    }

    /**
     * Tests that value returns the correct value when given a normal key in the
     * map.
     */
    @Test
    public void valueNormalKeyTest() {
        Map<String, String> testMap = this.createFromArgsTest("One", "Two",
                "Three", "Four", "Five", "Six");
        String val = testMap.value("Three");
        assertEquals("Four", val);
    }

    /**
     * Tests that value returns the correct value when given the first key in
     * the map.
     */
    @Test
    public void valueFirstKeyTest() {
        Map<String, String> testMap = this.createFromArgsTest("One", "Two",
                "Three", "Four");
        String val = testMap.value("One");
        assertEquals("Two", val);
    }

    /**
     * Tests that value returns the correct value when given the last key in the
     * map.
     */
    @Test
    public void valueLastKeyTest() {
        Map<String, String> testMap = this.createFromArgsTest("One", "Two",
                "Three", "Four");
        String val = testMap.value("Three");
        assertEquals("Four", val);
    }

    /**
     * Tests that value returns the correct value when given the only key in the
     * map.
     */
    @Test
    public void valueSingleElementTest() {
        Map<String, String> testMap = this.createFromArgsTest("A", "B");
        String val = testMap.value("A");
        assertEquals("B", val);
    }

    /**
     * Tests that hasKey returns true when the key is in the map.
     */
    @Test
    public void hasKeyTrueTest() {
        Map<String, String> testMap = this.createFromArgsRef("One", "Two",
                "Three", "Four");
        boolean found = testMap.hasKey("One");
        assertEquals(true, found);
    }

    /**
     * Tests that hasKey returns false when the key is not in the map.
     */
    @Test
    public void hasKeyFalseTest() {
        Map<String, String> testMap = this.createFromArgsRef("One", "Two",
                "Three", "Four");
        boolean found = testMap.hasKey("Five");
        assertEquals(false, found);
    }

    /**
     * Tests that hasKey returns false when called on an empty map.
     */
    @Test
    public void hasKeyEmptyTest() {
        Map<String, String> testMap = this.constructorTest();
        boolean found = testMap.hasKey("Anything");
        assertEquals(false, found);
    }

    /**
     * Tests that size() returns 0 for an empty map.
     */
    @Test
    public void sizeEmptyTest() {
        Map<String, String> testMap = this.constructorTest();
        int size = testMap.size();
        assertEquals(0, size);
    }

    /**
     * Tests that size() returns the correct number of key-value pairs.
     */
    @Test
    public void sizeNonEmptyTest() {
        Map<String, String> testMap = this.createFromArgsTest("One", "Two",
                "Three", "Four", "Five", "Six");
        int size = testMap.size();
        final int expSize = 3;
        assertEquals(expSize, size);
    }

}
