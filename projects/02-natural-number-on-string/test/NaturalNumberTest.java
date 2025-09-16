import static org.junit.Assert.assertEquals;

import org.junit.Test;

import components.naturalnumber.NaturalNumber;

/**
 * JUnit test fixture for {@code NaturalNumber}'s constructors and kernel
 * methods.
 *
 * @author Put your name here
 *
 */
public abstract class NaturalNumberTest {

    /**
     * Invokes the appropriate {@code NaturalNumber} constructor for the
     * implementation under test and returns the result.
     *
     * @return the new number
     * @ensures constructorTest = 0
     */
    protected abstract NaturalNumber constructorTest();

    /**
     * Invokes the appropriate {@code NaturalNumber} constructor for the
     * implementation under test and returns the result.
     *
     * @param i
     *            {@code int} to initialize from
     * @return the new number
     * @requires i >= 0
     * @ensures constructorTest = i
     */
    protected abstract NaturalNumber constructorTest(int i);

    /**
     * Invokes the appropriate {@code NaturalNumber} constructor for the
     * implementation under test and returns the result.
     *
     * @param s
     *            {@code String} to initialize from
     * @return the new number
     * @requires there exists n: NATURAL (s = TO_STRING(n))
     * @ensures s = TO_STRING(constructorTest)
     */
    protected abstract NaturalNumber constructorTest(String s);

    /**
     * Invokes the appropriate {@code NaturalNumber} constructor for the
     * implementation under test and returns the result.
     *
     * @param n
     *            {@code NaturalNumber} to initialize from
     * @return the new number
     * @ensures constructorTest = n
     */
    protected abstract NaturalNumber constructorTest(NaturalNumber n);

    /**
     * Invokes the appropriate {@code NaturalNumber} constructor for the
     * reference implementation and returns the result.
     *
     * @return the new number
     * @ensures constructorRef = 0
     */
    protected abstract NaturalNumber constructorRef();

    /**
     * Invokes the appropriate {@code NaturalNumber} constructor for the
     * reference implementation and returns the result.
     *
     * @param i
     *            {@code int} to initialize from
     * @return the new number
     * @requires i >= 0
     * @ensures constructorRef = i
     */
    protected abstract NaturalNumber constructorRef(int i);

    /**
     * Invokes the appropriate {@code NaturalNumber} constructor for the
     * reference implementation and returns the result.
     *
     * @param s
     *            {@code String} to initialize from
     * @return the new number
     * @requires there exists n: NATURAL (s = TO_STRING(n))
     * @ensures s = TO_STRING(constructorRef)
     */
    protected abstract NaturalNumber constructorRef(String s);

    /**
     * Invokes the appropriate {@code NaturalNumber} constructor for the
     * reference implementation and returns the result.
     *
     * @param n
     *            {@code NaturalNumber} to initialize from
     * @return the new number
     * @ensures constructorRef = n
     */
    protected abstract NaturalNumber constructorRef(NaturalNumber n);

    // TODO - add test cases for four constructors, multiplyBy10, divideBy10, isZero

    @Test
    public void testConstructor() {
        NaturalNumber testNat = this.constructorTest();
        NaturalNumber expNat = this.constructorRef();
        assertEquals(expNat, testNat);
    }

    @Test
    public void testConstructorInt() {
        final int zero = 0;
        NaturalNumber testNat = this.constructorTest(zero);
        NaturalNumber expNat = this.constructorRef(zero);
        assertEquals(expNat, testNat);
    }

    @Test
    public void testConstructorString() {
        final int zeroInt = 0;
        String zero = Integer.toString(zeroInt);
        NaturalNumber testNat = this.constructorTest(zero);
        NaturalNumber expNat = this.constructorRef(zero);
        assertEquals(expNat, testNat);
    }

    @Test
    public void testConstructorNat() {
        final int zero = 0;
        NaturalNumber testNat = this.constructorTest(zero);
        NaturalNumber expNat = this.constructorRef(zero);
        assertEquals(expNat, testNat);
    }

    @Test
    public void mul10Int() {
        final int initialNum = 3;
        final int finalNum = 30;
        NaturalNumber testNat = this.constructorTest(initialNum);
        NaturalNumber expNat = this.constructorTest(finalNum);
        testNat.multiplyBy10(0);
        assertEquals(expNat, testNat);
    }

    @Test
    public void mul10MaxInt() {
        final int maxInt = 500000000;
        NaturalNumber testNat = this.constructorTest(maxInt);
        NaturalNumber expNat = this.constructorRef(maxInt);
        testNat.multiplyBy10(0);
        assertEquals(expNat, testNat);
    }

    @Test
    public void mul10Zero() {
        final int zero = 0;
        NaturalNumber testNat = this.constructorTest(zero);
        NaturalNumber expNat = this.constructorRef(zero);
        testNat.multiplyBy10(0);
        assertEquals(expNat, testNat);
    }

    @Test
    public void mul10String() {
        String initialNum = "3";
        String finalNum = "31";
        NaturalNumber testNat = this.constructorTest(initialNum);
        NaturalNumber expNat = this.constructorRef(finalNum);
        testNat.multiplyBy10(1);
        assertEquals(expNat, testNat);
    }

    @Test
    public void mul10MaxString() {
        String maxInt = Integer.toString(500000000);
        NaturalNumber testNat = this.constructorTest(maxInt);
        NaturalNumber expNat = this.constructorRef(maxInt);
        testNat.multiplyBy10(0);
        assertEquals(expNat, testNat);
    }

    @Test
    public void mul10ZeroString() {
        final int zeroInt = 0;
        String zero = Integer.toString(zeroInt);
        NaturalNumber testNat = this.constructorTest(zero);
        NaturalNumber expNat = this.constructorRef(zero);
        testNat.multiplyBy10(0);
        assertEquals(expNat, testNat);
    }

    @Test
    public void mul10Nat() {
        final int initialNum = 3;
        final int finalNum = 33;
        NaturalNumber testNat = this.constructorTest(initialNum);
        NaturalNumber expNat = this.constructorTest(finalNum);
        testNat.multiplyBy10(3);
        assertEquals(expNat, testNat);
    }

    @Test
    public void mul10MaxNat() {
        int maxNum = 500000000;
        NaturalNumber testNat = this.constructorTest(maxNum);
        NaturalNumber expNat = this.constructorTest(maxNum);
        testNat.multiplyBy10(0);
        assertEquals(expNat, testNat);
    }

    @Test
    public void mul10ZeroNat() {
        final int zero = 0;
        NaturalNumber testNat = this.constructorTest(zero);
        NaturalNumber expNat = this.constructorTest(zero);
        testNat.multiplyBy10(0);
        assertEquals(expNat, testNat);
    }

    @Test
    public void div10Int() {
        final int initialNum = 33;
        final int finalNum = 3;
        final int remainder = 3;
        NaturalNumber testNat = this.constructorTest(initialNum);
        NaturalNumber expNat = this.constructorTest(finalNum);
        int testRem = testNat.divideBy10();
        assertEquals(expNat, testNat);
        assertEquals(remainder, testRem);
    }

    @Test
    public void div10MaxInt() {
        int maxNum = 500000000;
        final int remainder = 0;
        NaturalNumber testNat = this.constructorTest(maxNum);
        NaturalNumber expNat = this.constructorTest(maxNum);
        int testRem = testNat.divideBy10();
        assertEquals(expNat, testNat);
        assertEquals(remainder, testRem);
    }

    @Test
    public void div10ZeroInt() {
        final int zero = 0;
        final int remainder = 0;
        NaturalNumber testNat = this.constructorTest(zero);
        NaturalNumber expNat = this.constructorTest(zero);
        int testRem = testNat.divideBy10();
        assertEquals(expNat, testNat);
        assertEquals(remainder, testRem);
    }

    @Test
    public void div10String() {
        final int initialInt = 35;
        final int finalInt = 3;
        final int remainderInt = 5;
        String initialNum = Integer.toString(initialInt);
        String finalNum = Integer.toString(finalInt);
        String remainder = Integer.toString(remainderInt);
        NaturalNumber testNat = this.constructorTest(initialNum);
        NaturalNumber expNat = this.constructorTest(finalNum);
        String testRem = Integer.toString(testNat.divideBy10());
        assertEquals(expNat, testNat);
        assertEquals(remainder, testRem);
    }

    @Test
    public void div10MaxString() {
        String maxInt = Integer.toString(500000000);
        final int remainderInt = 0;
        String remainder = Integer.toString(remainderInt);
        NaturalNumber testNat = this.constructorTest(maxInt);
        NaturalNumber expNat = this.constructorRef(maxInt);
        String testRem = Integer.toString(testNat.divideBy10());
        assertEquals(expNat, testNat);
        assertEquals(remainder, testRem);
    }

    @Test
    public void div10ZeroString() {
        final int zero = 0;
        final int remainderInt = 0;
        String initialNum = Integer.toString(zero);
        String finalNum = Integer.toString(zero);
        String remainder = Integer.toString(remainderInt);
        NaturalNumber testNat = this.constructorTest(initialNum);
        NaturalNumber expNat = this.constructorTest(finalNum);
        String testRem = Integer.toString(testNat.divideBy10());
        assertEquals(expNat, testNat);
        assertEquals(remainder, testRem);
    }

    @Test
    public void div10Nat() {
        final int initialNum = 27;
        final int finalNum = 2;
        final int remainder = 7;
        NaturalNumber testNat = this.constructorTest(initialNum);
        NaturalNumber expNat = this.constructorTest(finalNum);
        int testRem = testNat.divideBy10();
        assertEquals(expNat, testNat);
        assertEquals(remainder, testRem);
    }

    @Test
    public void div10MaxNat() {
        int maxNum = 500000000;
        final int remainder = 0;
        NaturalNumber testNat = this.constructorTest(maxNum);
        NaturalNumber expNat = this.constructorTest(maxNum);
        int testRem = testNat.divideBy10();
        assertEquals(expNat, testNat);
        assertEquals(remainder, testRem);
    }

    @Test
    public void div10ZeroNat() {
        final int zero = 0;
        final int remainder = 0;
        NaturalNumber testNat = this.constructorTest(zero);
        NaturalNumber expNat = this.constructorTest(zero);
        int testRem = testNat.divideBy10();
        assertEquals(expNat, testNat);
        assertEquals(remainder, testRem);
    }

    @Test
    public void isZeroInt() {
        final int zero = 0;
        NaturalNumber testNat = this.constructorTest(zero);
        boolean testZero = testNat.isZero();
        assertEquals(true, testZero);
    }

    @Test
    public void isNotZeroInt() {
        final int zero = 5;
        NaturalNumber testNat = this.constructorTest(zero);
        boolean testZero = testNat.isZero();
        assertEquals(false, testZero);
    }

    @Test
    public void isZeroString() {
        final int zeroInt = 0;
        String zero = Integer.toString(zeroInt);
        NaturalNumber testNat = this.constructorTest(zero);
        boolean testZero = testNat.isZero();
        assertEquals(true, testZero);
    }

    @Test
    public void isNotZeroString() {
        final int zeroInt = 66;
        String zero = Integer.toString(zeroInt);
        NaturalNumber testNat = this.constructorTest(zero);
        boolean testZero = testNat.isZero();
        assertEquals(false, testZero);
    }

    @Test
    public void isZeroNat() {
        final int zero = 0;
        NaturalNumber testNat = this.constructorTest(zero);
        boolean testZero = testNat.isZero();
        assertEquals(true, testZero);
    }

    @Test
    public void isNotZeroNat() {
        final int zero = 10;
        NaturalNumber testNat = this.constructorTest(zero);
        boolean testZero = testNat.isZero();
        assertEquals(false, testZero);
    }

}
