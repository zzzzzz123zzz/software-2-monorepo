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
     * Tests that adding a new key-value pair updates the map correctly.
     */
    @Test
    public void addTest() {
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
    public void removeTest() {
        Map<String, String> testMap = this.createFromArgsRef("One", "Two",
                "Three", "Four");
        Map<String, String> expMap = this.createFromArgsTest("One", "Two");
        testMap.remove("Three");
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
     * Tests that value(k) returns the correct value for a given key.
     */
    @Test
    public void valueTest() {
        Map<String, String> testMap = this.createFromArgsRef("One", "Two",
                "Three", "Four");
        Map<String, String> expMap = this.createFromArgsRef("One", "Two",
                "Three", "Four");
        String testValue = testMap.value("One");
        String expValue = expMap.value("One");
        assertEquals(expValue, testValue);
    }

    /**
     * Tests that hasKey returns true when the key is in the map.
     */
    @Test
    public void hasKeyTest() {
        Map<String, String> testMap = this.createFromArgsRef("One", "Two",
                "Three", "Four");
        Map<String, String> expMap = this.createFromArgsRef("One", "Two",
                "Three", "Four");
        String testKey = testMap.remove("One").key();
        boolean found = expMap.hasKey(testKey);
        assertEquals(true, found);
    }

    /**
     * Tests that size() returns the correct number of key-value pairs.
     */
    @Test
    public void sizeTest() {
        Map<String, String> testMap = this.constructorTest();
        testMap.add("One", "Two");
        testMap.add("Three", "Four");
        testMap.add("Five", "Six");
        final int expSize = 3;
        assertEquals(expSize, testMap.size());
    }

}
