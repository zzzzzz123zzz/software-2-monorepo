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
    public void testConstructorIntNonZero() {
        final int input = 123;
        NaturalNumber testNat = this.constructorTest(input);
        NaturalNumber expNat = this.constructorRef(input);
        assertEquals(expNat, testNat);
    }

    @Test
    public void testConstructorStringNonZero() {
        String input = "456";
        NaturalNumber testNat = this.constructorTest(input);
        NaturalNumber expNat = this.constructorRef(input);
        assertEquals(expNat, testNat);
    }

    @Test
    public void testConstructorNatNonZero() {
        NaturalNumber input = this.constructorRef(789);
        NaturalNumber testNat = this.constructorTest(input);
        NaturalNumber expNat = this.constructorRef(input);
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
    public void mul10Int2() {
        final int initialNum = 3;
        final int finalNum = 35;
        NaturalNumber testNat = this.constructorTest(initialNum);
        NaturalNumber expNat = this.constructorTest(finalNum);
        testNat.multiplyBy10(5);
        assertEquals(expNat, testNat);
    }

    @Test
    public void mul10BigInt() {
        final int maxInt = 5000;
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
    public void div10BigNum() {
        final int initialNum = 567;
        final int finalNum = 56;
        final int remainder = 7;
        NaturalNumber testNat = this.constructorTest(initialNum);
        NaturalNumber expNat = this.constructorTest(finalNum);
        int testRem = testNat.divideBy10();
        assertEquals(expNat, testNat);
        assertEquals(remainder, testRem);
    }

    @Test
    public void div10SmalNum() {
        int initialInt = 5;
        int finalInt = 0;
        final int remainder = 5;
        NaturalNumber testNat = this.constructorTest(initialInt);
        NaturalNumber expNat = this.constructorTest(finalInt);
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
    public void isZeroTrue() {
        final int zero = 0;
        NaturalNumber testNat = this.constructorTest(zero);
        boolean testZero = testNat.isZero();
        assertEquals(true, testZero);
    }

    @Test
    public void isNotZeroFalse() {
        final int zero = 5;
        NaturalNumber testNat = this.constructorTest(zero);
        boolean testZero = testNat.isZero();
        assertEquals(false, testZero);
    }

}
