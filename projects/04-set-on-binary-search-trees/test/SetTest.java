import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import components.set.Set;

/**
 * JUnit test fixture for {@code Set<String>}'s constructor and kernel methods.
 *
 * @author Put your name here
 *
 */
public abstract class SetTest {

    /**
     * Invokes the appropriate {@code Set} constructor for the implementation
     * under test and returns the result.
     *
     * @return the new set
     * @ensures constructorTest = {}
     */
    protected abstract Set<String> constructorTest();

    /**
     * Invokes the appropriate {@code Set} constructor for the reference
     * implementation and returns the result.
     *
     * @return the new set
     * @ensures constructorRef = {}
     */
    protected abstract Set<String> constructorRef();

    /**
     * Creates and returns a {@code Set<String>} of the implementation under
     * test type with the given entries.
     *
     * @param args
     *            the entries for the set
     * @return the constructed set
     * @requires [every entry in args is unique]
     * @ensures createFromArgsTest = [entries in args]
     */
    private Set<String> createFromArgsTest(String... args) {
        Set<String> set = this.constructorTest();
        for (String s : args) {
            assert !set.contains(
                    s) : "Violation of: every entry in args is unique";
            set.add(s);
        }
        return set;
    }

    /**
     * Creates and returns a {@code Set<String>} of the reference implementation
     * type with the given entries.
     *
     * @param args
     *            the entries for the set
     * @return the constructed set
     * @requires [every entry in args is unique]
     * @ensures createFromArgsRef = [entries in args]
     */
    private Set<String> createFromArgsRef(String... args) {
        Set<String> set = this.constructorRef();
        for (String s : args) {
            assert !set.contains(
                    s) : "Violation of: every entry in args is unique";
            set.add(s);
        }
        return set;
    }

    // TODO - add test cases for constructor, add, remove, removeAny, contains, and size
    /**
     * Test the no-argument constructor.
     */
    @Test
    public void testConstructor() {
        Set<String> sTest = this.constructorTest();
        Set<String> sExpected = this.constructorRef();
        assertEquals(sExpected, sTest);
    }

    /**
     * Test add to empty set.
     */
    @Test
    public void testAddToEmpty() {
        Set<String> sTest = this.createFromArgsTest();
        Set<String> sExpected = this.createFromArgsRef("apple");

        sTest.add("apple");

        assertEquals(sExpected, sTest);
    }

    /**
     * Test add to non-empty set.
     */
    @Test
    public void testAddNonEmpty() {
        Set<String> sTest = this.createFromArgsTest("apple");
        Set<String> sExpected = this.createFromArgsRef("apple", "banana");

        sTest.add("banana");

        assertEquals(sExpected, sTest);
    }

    /**
     * Test remove from one-element set.
     */
    @Test
    public void testRemoveSingleElement() {
        Set<String> sTest = this.createFromArgsTest("apple");
        Set<String> sExpected = this.createFromArgsRef();

        String removed = sTest.remove("apple");

        assertEquals("apple", removed);
        assertEquals(sExpected, sTest);
    }

    /**
     * Test remove from multi-element set.
     */
    @Test
    public void testRemoveFromMultiple() {
        Set<String> sTest = this.createFromArgsTest("apple", "banana",
                "cherry");
        Set<String> sExpected = this.createFromArgsRef("apple", "cherry");

        String removed = sTest.remove("banana");

        assertEquals("banana", removed);
        assertEquals(sExpected, sTest);
    }

    /**
     * test if there is only one element.
     */
    @Test
    public void testRemoveAnyOneElement() {
        Set<String> s = createFromArgsTest("a");
        String removed = s.removeAny();
        assertEquals("a", removed);
        assertEquals(0, s.size());
    }

    /**
     * test if ther is more than one element.
     */
    @Test
    public void testRemoveAnyThreeElements() {
        Set<String> s = createFromArgsTest("x", "y", "z");
        String removed = s.removeAny();
        assertTrue(removed.equals("x") || removed.equals("y")
                || removed.equals("z"));
        assertEquals(2, s.size());
        assertFalse(s.contains(removed));
    }

    /**
     * test when it contains.
     */
    @Test
    public void testContainsTrue() {
        Set<String> s = createFromArgsTest("cat", "dog", "fish");
        assertTrue(s.contains("dog"));
    }

    /**
     * test if it does not contains.
     */
    @Test
    public void testContainsFalse() {
        Set<String> s = createFromArgsTest("apple", "banana", "grape");
        assertFalse(s.contains("orange"));
    }

    /**
     * test if it is empty at all.
     */
    @Test
    public void testContainsOnEmptySet() {
        Set<String> s = createFromArgsTest();
        assertFalse(s.contains("anything"));
    }

    /**
     * test for size when it is empty.
     */
    @Test
    public void testSizeEmpty() {
        Set<String> s = createFromArgsTest();
        assertEquals(0, s.size());
    }

    /**
     * test when there are elements in it.
     */
    @Test
    public void testSizeThreeElements() {
        Set<String> s = createFromArgsTest("red", "green", "blue");
        assertEquals(3, s.size());
    }

    /**
     * test when the elements in it are the smae.
     */
    @Test
    public void testSizeWithDuplicatesPassedIn() {
        Set<String> s = createFromArgsTest("hi", "hi", "hi");
        assertEquals(1, s.size());
    }
}
