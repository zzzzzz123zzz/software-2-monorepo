import static org.junit.Assert.assertEquals;

import org.junit.Test;

import components.naturalnumber.NaturalNumber;

/**
 * JUnit test fixture for {@code NaturalNumber}'s constructors and kernel
 * methods.
 *
 * @author Jeng(Zizheng) Zhuang; Leo Zhuang; Michael Xu;
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

    /**
     * Tests the default constructor of NaturalNumber. Ensures the value is
     * initialized to 0.
     */
    @Test
    public void testConstructor() {
        NaturalNumber testNat = this.constructorTest();
        NaturalNumber expNat = this.constructorRef();
        assertEquals(expNat, testNat);
    }

    /**
     * Tests the constructor with an int input of zero. Ensures the value is
     * initialized to 0.
     */
    @Test
    public void testConstructorIntZero() {
        NaturalNumber testNat = this.constructorTest(0);
        NaturalNumber expNat = this.constructorRef(0);
        assertEquals(expNat, testNat);
    }

    /**
     * Tests the constructor with a non-zero int input. Ensures the value is
     * initialized correctly.
     */
    @Test
    public void testConstructorIntNonZero() {
        final int input = 123;
        NaturalNumber testNat = this.constructorTest(input);
        NaturalNumber expNat = this.constructorRef(input);
        assertEquals(expNat, testNat);
    }

    /**
     * Tests the constructor with a String input of "0". Ensures the value is
     * initialized to 0.
     */
    @Test
    public void testConstructorStringZero() {
        NaturalNumber testNat = this.constructorTest("0");
        NaturalNumber expNat = this.constructorRef("0");
        assertEquals(expNat, testNat);
    }

    /**
     * Tests the constructor with a non-zero String input. Ensures the value is
     * initialized correctly.
     */
    @Test
    public void testConstructorStringNonZero() {
        String input = "456";
        NaturalNumber testNat = this.constructorTest(input);
        NaturalNumber expNat = this.constructorRef(input);
        assertEquals(expNat, testNat);
    }

    /**
     * Tests the copy constructor with a NaturalNumber input of zero. Ensures
     * the new object is equal to the original.
     */
    @Test
    public void testConstructorNatZero() {
        NaturalNumber zeroNN = this.constructorRef(0);
        NaturalNumber testNat = this.constructorTest(zeroNN);
        NaturalNumber expNat = this.constructorRef(zeroNN);
        assertEquals(expNat, testNat);
    }

    /**
     * Tests the copy constructor with a NaturalNumber input. Ensures the new
     * object is equal to the original.
     */
    @Test
    public void testConstructorNatNonZero() {
        final int k = 789;
        NaturalNumber input = this.constructorRef(k);
        NaturalNumber testNat = this.constructorTest(input);
        NaturalNumber expNat = this.constructorRef(input);
        assertEquals(expNat, testNat);
    }

    /**
     * Tests multiplyBy10 with zero appended. Ensures the number is correctly
     * multiplied by 10.
     */
    @Test
    public void mul10Int() {
        final int initialNum = 3;
        final int finalNum = 30;
        NaturalNumber testNat = this.constructorTest(initialNum);
        NaturalNumber expNat = this.constructorTest(finalNum);
        testNat.multiplyBy10(0);
        assertEquals(expNat, testNat);
    }

    /**
     * Tests multiplyBy10 with a non-zero digit appended. Ensures the number is
     * correctly multiplied by 10 and the digit added.
     */
    @Test
    public void mul10Int2() {
        final int initialNum = 3;
        final int finalNum = 35;
        final int digit = 5;
        NaturalNumber testNat = this.constructorTest(initialNum);
        NaturalNumber expNat = this.constructorTest(finalNum);
        testNat.multiplyBy10(digit);
        assertEquals(expNat, testNat);
    }

    /**
     * Tests multiplyBy10 on zero with a non-zero digit appended. Ensures the
     * number is correctly set to the digit.
     */
    @Test
    public void mul10Int3() {
        final int initialNum = 0;
        final int finalNum = 5;
        final int digit = 5;
        NaturalNumber testNat = this.constructorTest(initialNum);
        NaturalNumber expNat = this.constructorTest(finalNum);
        testNat.multiplyBy10(digit);
        assertEquals(expNat, testNat);
    }

    /**
     * Tests multiplyBy10 on a large number with zero appended. Ensures the
     * number remains unchanged.
     */
    @Test
    public void mul10BigInt() {
        final int maxInt = 5000;
        final int fInt = 50000;
        NaturalNumber testNat = this.constructorTest(maxInt);
        NaturalNumber expNat = this.constructorRef(fInt);
        testNat.multiplyBy10(0);
        assertEquals(expNat, testNat);
    }

    /**
     * Tests multiplyBy10 on zero with zero appended. Ensures the number remains
     * zero.
     */
    @Test
    public void mul10Zero() {
        final int zero = 0;
        NaturalNumber testNat = this.constructorTest(zero);
        NaturalNumber expNat = this.constructorRef(zero);
        testNat.multiplyBy10(0);
        assertEquals(expNat, testNat);
    }

    /**
     * Tests divideBy10 on a large number. Ensures the number is correctly
     * divided and the remainder is correct.
     */
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

    /**
     * Tests divideBy10 on a small number. Ensures the number is correctly
     * divided and the remainder is correct.
     */
    @Test
    public void div10SmalNum() {
        final int initialInt = 5;
        final int finalInt = 0;
        final int remainder = 5;
        NaturalNumber testNat = this.constructorTest(initialInt);
        NaturalNumber expNat = this.constructorTest(finalInt);
        int testRem = testNat.divideBy10();
        assertEquals(expNat, testNat);
        assertEquals(remainder, testRem);
    }

    /**
     * Tests divideBy10 on zero. Ensures the number remains zero and the
     * remainder is zero.
     */
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

    /**
     * Tests isZero on a zero number. Ensures the method returns true.
     */
    @Test
    public void isZeroTrue() {
        final int zero = 0;
        NaturalNumber testNat = this.constructorTest(zero);
        boolean testZero = testNat.isZero();
        assertEquals(true, testZero);
    }

    /**
     * Tests isZero on a non-zero number. Ensures the method returns false.
     */
    @Test
    public void isNotZeroFalse() {
        final int zero = 5;
        NaturalNumber testNat = this.constructorTest(zero);
        boolean testZero = testNat.isZero();
        assertEquals(false, testZero);
    }

}
