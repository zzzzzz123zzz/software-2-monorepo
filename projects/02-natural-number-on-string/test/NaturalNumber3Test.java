import components.naturalnumber.NaturalNumber;
import components.naturalnumber.NaturalNumber2;

/**
 * Customized JUnit test fixture for {@code NaturalNumber3}.
 */
public class NaturalNumber3Test extends NaturalNumberTest {

    /**
     * Returns a new {@code NaturalNumber3} initialized to 0.
     */
    @Override
    protected final NaturalNumber constructorTest() {

        return new NaturalNumber3();
    }

    /**
     * Returns a new {@code NaturalNumber3} initialized to the given int.
     *
     * @param i
     *            non-negative integer
     */
    @Override
    protected final NaturalNumber constructorTest(int i) {

        return new NaturalNumber3(i);
    }

    /**
     * Returns a new {@code NaturalNumber3} initialized from the given String.
     *
     * @param s
     *            string representation of a natural number
     */
    @Override
    protected final NaturalNumber constructorTest(String s) {

        return new NaturalNumber3(s);
    }

    /**
     * Returns a new {@code NaturalNumber3} copied from another NaturalNumber.
     *
     * @param n
     *            the number to copy
     */
    @Override
    protected final NaturalNumber constructorTest(NaturalNumber n) {

        return new NaturalNumber3(n);
    }

    /**
     * Returns a new {@code NaturalNumber2} initialized to 0 as the reference.
     */
    @Override
    protected final NaturalNumber constructorRef() {

        return new NaturalNumber2();
    }

    /**
     * Returns a new {@code NaturalNumber2} initialized to the given int as the
     * reference.
     */
    @Override
    protected final NaturalNumber constructorRef(int i) {

        return new NaturalNumber2(i);
    }

    /**
     * Returns a new {@code NaturalNumber2} initialized from the given String as
     * the reference.
     */
    @Override
    protected final NaturalNumber constructorRef(String s) {

        return new NaturalNumber2(s);
    }

    /**
     * Returns a new {@code NaturalNumber2} copied from another NaturalNumber as
     * the reference.
     */
    @Override
    protected final NaturalNumber constructorRef(NaturalNumber n) {

        return new NaturalNumber2(n);
    }

}
