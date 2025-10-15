import static org.junit.Assert.assertEquals;

import java.util.Comparator;

import org.junit.Test;

import components.sortingmachine.SortingMachine;

/**
 * JUnit test fixture for {@code SortingMachine<String>}'s constructor and
 * kernel methods.
 *
 * @author Put your name here
 *
 */
public abstract class SortingMachineTest {

    /**
     * Invokes the appropriate {@code SortingMachine} constructor for the
     * implementation under test and returns the result.
     *
     * @param order
     *            the {@code Comparator} defining the order for {@code String}
     * @return the new {@code SortingMachine}
     * @requires IS_TOTAL_PREORDER([relation computed by order.compare method])
     * @ensures constructorTest = (true, order, {})
     */
    protected abstract SortingMachine<String> constructorTest(
            Comparator<String> order);

    /**
     * Invokes the appropriate {@code SortingMachine} constructor for the
     * reference implementation and returns the result.
     *
     * @param order
     *            the {@code Comparator} defining the order for {@code String}
     * @return the new {@code SortingMachine}
     * @requires IS_TOTAL_PREORDER([relation computed by order.compare method])
     * @ensures constructorRef = (true, order, {})
     */
    protected abstract SortingMachine<String> constructorRef(
            Comparator<String> order);

    /**
     *
     * Creates and returns a {@code SortingMachine<String>} of the
     * implementation under test type with the given entries and mode.
     *
     * @param order
     *            the {@code Comparator} defining the order for {@code String}
     * @param insertionMode
     *            flag indicating the machine mode
     * @param args
     *            the entries for the {@code SortingMachine}
     * @return the constructed {@code SortingMachine}
     * @requires IS_TOTAL_PREORDER([relation computed by order.compare method])
     * @ensures <pre>
     * createFromArgsTest = (insertionMode, order, [multiset of entries in args])
     * </pre>
     */
    private SortingMachine<String> createFromArgsTest(Comparator<String> order,
            boolean insertionMode, String... args) {
        SortingMachine<String> sm = this.constructorTest(order);
        for (int i = 0; i < args.length; i++) {
            sm.add(args[i]);
        }
        if (!insertionMode) {
            sm.changeToExtractionMode();
        }
        return sm;
    }

    /**
     *
     * Creates and returns a {@code SortingMachine<String>} of the reference
     * implementation type with the given entries and mode.
     *
     * @param order
     *            the {@code Comparator} defining the order for {@code String}
     * @param insertionMode
     *            flag indicating the machine mode
     * @param args
     *            the entries for the {@code SortingMachine}
     * @return the constructed {@code SortingMachine}
     * @requires IS_TOTAL_PREORDER([relation computed by order.compare method])
     * @ensures <pre>
     * createFromArgsRef = (insertionMode, order, [multiset of entries in args])
     * </pre>
     */
    private SortingMachine<String> createFromArgsRef(Comparator<String> order,
            boolean insertionMode, String... args) {
        SortingMachine<String> sm = this.constructorRef(order);
        for (int i = 0; i < args.length; i++) {
            sm.add(args[i]);
        }
        if (!insertionMode) {
            sm.changeToExtractionMode();
        }
        return sm;
    }

    /**
     * Comparator<String> implementation to be used in all test cases. Compare
     * {@code String}s in lexicographic order.
     */
    private static class StringLT implements Comparator<String> {

        @Override
        public int compare(String s1, String s2) {
            return s1.compareToIgnoreCase(s2);
        }

    }

    /**
     * Comparator instance to be used in all test cases.
     */
    private static final StringLT ORDER = new StringLT();

    /*
     * Sample test cases.
     */

    @Test
    public final void testConstructor() {
        SortingMachine<String> m = this.constructorTest(ORDER);
        SortingMachine<String> mExpected = this.constructorRef(ORDER);
        assertEquals(mExpected, m);
    }

    @Test
    public final void testAddEmpty() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true);
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true,
                "green");
        m.add("green");
        assertEquals(mExpected, m);
    }

    // TODO - add test cases for add, changeToExtractionMode, removeFirst,
    // isInInsertionMode, order, and size

    @Test
    public void testAddNonEmptyInsertionMode() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true, "red",
                "blue");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true,
                "red", "blue", "green");
        m.add("green");
        assertEquals(mExpected, m);
    }

    @Test
    public void testAddAnotherInsertionMode() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true, "red",
                "orange", "blue", "green");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true,
                "red", "orange", "blue", "green", "purple");
        m.add("purple");
        assertEquals(mExpected, m);
    }

    @Test
    public void testAddDuplicate() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true, "apple",
                "banana");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true,
                "apple", "banana", "apple");
        m.add("apple");
        assertEquals(mExpected, m);
    }

    @Test
    public void testChangeToExtractionModeEmpty() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true);
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false);
        m.changeToExtractionMode();
        assertEquals(mExpected, m);
    }

    @Test
    public void testChangeToExtractionModeSingle() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true,
                "apple");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false,
                "apple");
        m.changeToExtractionMode();
        assertEquals(mExpected, m);
    }

    @Test
    public void testChangeToExtractionModeMultiple() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true, "red",
                "purple", "blue", "green", "orange");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false,
                "blue", "green", "orange", "purple", "red");
        m.changeToExtractionMode();
        assertEquals(mExpected, m);
    }

    @Test
    public void removeFirstTest() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, false, "red",
                "purple", "blue", "green", "orange");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false,
                "green", "orange", "purple", "red");
        m.removeFirst();
        assertEquals(mExpected, m);

    }

    @Test
    public void testRemoveFirstSingle() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, false,
                "apple");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false);
        m.removeFirst(); // removes "apple"
        assertEquals(mExpected, m);
    }

    @Test
    public final void testIsInInsertionModeTrue() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true);
        assertEquals(true, m.isInInsertionMode());
    }

    @Test
    public final void testIsInInsertionModeFalse() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, false, "a",
                "b");
        assertEquals(false, m.isInInsertionMode());
    }

    @Test
    public final void testOrder() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true);
        assertEquals(ORDER, m.order());
    }

    @Test
    public final void testOrderExtractionMode() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, false, "a",
                "b", "c");
        assertEquals(ORDER, m.order());
    }

    @Test
    public final void testSizeInsertionMode() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true, "a",
                "b", "c");
        assertEquals(3, m.size());
    }

    @Test
    public final void testSizeInsertionModeEmpty() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true);
        assertEquals(0, m.size());
    }

    @Test
    public final void testSizeExtractionMode() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, false, "a",
                "b", "c", "d");
        assertEquals(4, m.size());
    }

    @Test
    public final void testSizeExtractionModeEmpty() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, false);
        assertEquals(0, m.size());
    }
}