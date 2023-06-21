import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Comparator;
import java.util.LinkedList;

import org.junit.Test;

import components.sortingmachine.SortingMachine;

/**
 * JUnit test fixture for {@code SortingMachine<String>}'s constructor and
 * kernel methods.
 *
 * @author Zack Zhu.
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

    @Test
    public final void testConstructor() {
        SortingMachine<String> test = this.constructorTest(ORDER);
        SortingMachine<String> ref = this.constructorRef(ORDER);
        assertEquals(ref, test);
    }

    /**
     * Helper method that tests method {@code add}.
     *
     * @param x
     *            the element to be added.
     * @param elements
     *            elements already added in the object.
     */
    private void testAdd(String x, String... elements) {
        SortingMachine<String> test = this.createFromArgsTest(ORDER, true,
                elements);
        SortingMachine<String> ref = this.createFromArgsRef(ORDER, true,
                elements);

        test.add(x);
        ref.add(x);

        assertEquals(ref, test);
    }

    @Test
    public final void testAddEmpty1() {
        this.testAdd("green");
    }

    @Test
    public final void testAddEmpty2() {
        this.testAdd("a", "Ab", "A", "a");
    }

    @Test
    public final void testAddEmpty3() {
        this.testAdd("a", "b", "Cat");
    }

    @Test
    public final void testAddEmpty4() {
        this.testAdd("ab", "ba", "abc");
    }

    @Test
    public final void testAddEmpty5() {
        this.testAdd("");
    }

    @Test
    public final void testAddEmpty6() {
        this.testAdd("", "");
    }

    @Test
    public final void testAddEmpty7() {
        this.testAdd("", "", "a", "b");
    }

    @Test
    public final void testAddEmpty8() {
        this.testAdd("ab", "ab", "a", "b");
    }

    @Test
    public final void testAddEmpty9() {
        this.testAdd("AD", "sFdE", "SfdE", "SaVd", "adEv");
    }

    /**
     * Helper method that tests method {@code changeToExtractionMode}.
     *
     * @param elements
     *            elements already added in the object.
     */
    private void testChangeMode(String... elements) {
        SortingMachine<String> test = this.createFromArgsTest(ORDER, true,
                elements);
        SortingMachine<String> ref = this.createFromArgsRef(ORDER, true,
                elements);

        test.changeToExtractionMode();
        ref.changeToExtractionMode();

        assertEquals(ref, test);
    }

    @Test
    public final void testChangeMode1() {
        this.testChangeMode();
    }

    @Test
    public final void testChangeMode2() {
        this.testChangeMode("");
    }

    @Test
    public final void testChangeMode3() {
        this.testChangeMode("", "");
    }

    @Test
    public final void testChangeMode4() {
        this.testChangeMode("a", "b", "", "");
    }

    @Test
    public final void testChangeMode5() {
        this.testChangeMode("gre", "sat", "act", "Ab", "ab");
    }

    @Test
    public final void testChangeMode6() {
        this.testChangeMode("aA", "", "Ab", "AA", "bC");
    }

    @Test
    public final void testChangeMode7() {
        this.testChangeMode("aA", "", "Ab", "AA", "bC", "Aa");
    }

    /**
     * Helper method that test method {@code removeFirst}.
     *
     * According to the contract, each pair of removed values from reference
     * instance and testing instance have the same priority. And these instances
     * remove the same set of elements.
     *
     * @param elements
     *            elements already added in the object.
     */
    private void testRemoveFirst(String... elements) {
        SortingMachine<String> test = this.createFromArgsTest(ORDER, false,
                elements);
        SortingMachine<String> ref = this.createFromArgsRef(ORDER, false,
                elements);

        // multiset from java.util.
        LinkedList<String> tList = new LinkedList<>();
        LinkedList<String> rList = new LinkedList<>();

        /*
         * Check that they removed the items that have the same priority.
         */
        while (test.size() > 0) {
            assertTrue(ref.size() > 0);
            // call to removeFirst.
            String tOut = test.removeFirst();
            String rOut = ref.removeFirst();
            // same priority.
            assertEquals(0, ORDER.compare(rOut, tOut));
            // construct the set(multiset) of elements removed.
            tList.add(tOut);
            rList.add(rOut);
        }
        /*
         * Check that they removed the same set(multiset) of elements.
         */
        for (String s : rList) {
            assertTrue(tList.contains(s));
            tList.remove(s);
        }
        /*
         * Check that they removed the same set(the left is empty).
         */
        assertEquals(0, tList.size());
    }

    @Test
    public final void testRemoveFirst1() {
        this.testRemoveFirst("");
    }

    @Test
    public final void testRemoveFirst2() {
        this.testRemoveFirst("a");
    }

    @Test
    public final void testRemoveFirst3() {
        this.testRemoveFirst("a", "b");
    }

    @Test
    public final void testRemoveFirst4() {
        this.testRemoveFirst("a", "Aa", "ab", "ba", "aa");
    }

    @Test
    public final void testRemoveFirst5() {
        this.testRemoveFirst("dc", "C", "d", "a", "edf");
    }

    @Test
    public final void testRemoveFirst6() {
        this.testRemoveFirst("ef", "eF", "a", "A", "a", "st");
    }

    @Test
    public final void testRemoveFirst7() {
        this.testRemoveFirst("eF", "Ef", "Aab", "Abc", "aAd", "st");
    }

    /**
     * Helper method that tests the method {@code isInInsertionMode}.
     *
     * @param mode
     *            mode of the sorting machine.
     * @param elements
     *            elements already added into the object.
     */
    private void testIsInMode(boolean mode, String... elements) {
        SortingMachine<String> test = this.createFromArgsTest(ORDER, mode,
                elements);
        SortingMachine<String> ref = this.createFromArgsRef(ORDER, mode,
                elements);

        boolean tOut = test.isInInsertionMode();
        boolean rOut = ref.isInInsertionMode();

        assertEquals(ref, test);
        assertEquals(rOut, tOut);
    }

    @Test
    public final void testIsInModeE1() {
        this.testIsInMode(true);
    }

    @Test
    public final void testIsInModeE2() {
        this.testIsInMode(false);
    }

    @Test
    public final void testIsInMode2() {
        this.testIsInMode(false, "");
    }

    @Test
    public final void testIsInMode3() {
        this.testIsInMode(true, "a");
    }

    @Test
    public final void testIsInMode4() {
        this.testIsInMode(true, "a", "b", "AB");
    }

    @Test
    public final void testIsInMode5() {
        this.testIsInMode(false, "a", "b", "Aa", "ac");
    }

    @Test
    public final void testIsInMode6() {
        this.testIsInMode(true, "bg", "A", "a");
    }

    @Test
    public final void testIsInMode7() {
        this.testIsInMode(false, "bg", "A", "a", "Egd", "BG");
    }

    /**
     * Helper method that tests the method {@code order}.
     *
     * @param mode
     *            the mode of the sorting machine.
     * @param elements
     *            elements already added into the object.
     */
    private void testOrder(boolean mode, String... elements) {
        SortingMachine<String> test = this.createFromArgsTest(ORDER, mode,
                elements);
        SortingMachine<String> ref = this.createFromArgsRef(ORDER, mode,
                elements);

        Comparator<String> tOut = test.order();
        Comparator<String> rOut = test.order();

        assertEquals(ref, test);
        assertEquals(rOut, tOut);
    }

    @Test
    public final void testOrderE1() {
        this.testOrder(true);
    }

    @Test
    public final void testOrderE2() {
        this.testOrder(false);
    }

    @Test
    public final void testOrder2() {
        this.testOrder(true, "");
    }

    @Test
    public final void testOrder3() {
        this.testOrder(true, "a", "b");
    }

    @Test
    public final void testOrder4() {
        this.testOrder(false, "a", "", "b");
    }

    /**
     * Helper method that tests the method {@code size}.
     *
     * @param mode
     *            the mode of the sorting machine.
     * @param elements
     *            elements already added into the object.
     */
    private void testSize(boolean mode, String... elements) {
        SortingMachine<String> test = this.createFromArgsTest(ORDER, mode,
                elements);
        SortingMachine<String> ref = this.createFromArgsRef(ORDER, mode,
                elements);

        int tSize = test.size();
        int rSize = ref.size();

        assertEquals(ref, test);
        assertEquals(rSize, tSize);
    }

    @Test
    public final void testSizeE1() {
        this.testSize(true);
    }

    @Test
    public final void testSizeE2() {
        this.testSize(false);
    }

    @Test
    public final void testSize2() {
        this.testSize(true, "");
    }

    @Test
    public final void testSize3() {
        this.testSize(false, "");
    }

    @Test
    public final void testSize4() {
        this.testSize(true, "", "", "ab", "Ab", "cs");
    }

    @Test
    public final void testSize5() {
        this.testSize(false, "a", "a", "", "b", "");
    }

}
