import java.util.Iterator;

import components.binarytree.BinaryTree;
import components.set.Set;
import components.set.SetSecondary;

/**
 * {@code Set} represented as a {@code BinaryTree} (maintained as a binary
 * search tree) of elements with implementations of primary methods.
 *
 * @param <T>
 *            type of {@code Set} elements
 * @mathdefinitions <pre>
 * IS_BST(
 *   tree: binary tree of T
 *  ): boolean satisfies
 *  [tree satisfies the binary search tree properties as described in the
 *   slides with the ordering reported by compareTo for T, including that
 *   it has no duplicate labels]
 * </pre>
 * @convention IS_BST($this.tree)
 * @correspondence this = labels($this.tree)
 *
 * @author Leo Zhuang, Michael Xu, Jeng Zhuang
 *
 */
public class Set3a<T extends Comparable<T>> extends SetSecondary<T> {

    /*
     * Private members --------------------------------------------------------
     */

    /**
     * Elements included in {@code this}.
     */
    private BinaryTree<T> tree;

    /**
     * Returns whether {@code x} is in {@code t}.
     *
     * @param <T>
     *            type of {@code BinaryTree} labels
     * @param t
     *            the {@code BinaryTree} to be searched
     * @param x
     *            the label to be searched for
     * @return true if t contains x, false otherwise
     * @requires IS_BST(t)
     * @ensures isInTree = (x is in labels(t))
     */
    private static <T extends Comparable<T>> boolean isInTree(BinaryTree<T> t,
            T x) {
        assert t != null : "Violation of: t is not null";
        assert x != null : "Violation of: x is not null";
        boolean found = false;
        if (t.height() > 0) {
            BinaryTree<T> leftT = t.newInstance();
            BinaryTree<T> rightT = t.newInstance();
            T root = t.disassemble(leftT, rightT);
            int compare = x.compareTo(root);
            if (compare == 0) {
                found = true;
            } else if (compare < 0) {
                found = isInTree(leftT, x);
            } else {
                found = isInTree(rightT, x);
            }
            t.assemble(root, leftT, rightT);
        }
        return found;
    }

    /**
     * Inserts {@code x} in {@code t}.
     *
     * @param <T>
     *            type of {@code BinaryTree} labels
     * @param t
     *            the {@code BinaryTree} to be searched
     * @param x
     *            the label to be inserted
     * @aliases reference {@code x}
     * @updates t
     * @requires IS_BST(t) and x is not in labels(t)
     * @ensures IS_BST(t) and labels(t) = labels(#t) union {x}
     */
    private static <T extends Comparable<T>> void insertInTree(BinaryTree<T> t,
            T x) {
        assert t != null : "Violation of: t is not null";
        assert x != null : "Violation of: x is not null";
        if (t.height() == 0) {
            BinaryTree<T> leftT = t.newInstance();
            BinaryTree<T> rightT = t.newInstance();
            t.assemble(x, leftT, rightT);
        } else {
            BinaryTree<T> leftT = t.newInstance();
            BinaryTree<T> rightT = t.newInstance();
            T root = t.disassemble(leftT, rightT);
            if (x.compareTo(root) < 0) {
                insertInTree(leftT, x);
            } else {
                insertInTree(rightT, x);
            }
            t.assemble(root, leftT, rightT);
        }
    }

    /**
     * Removes and returns the smallest (left-most) label in {@code t}.
     *
     * @param <T>
     *            type of {@code BinaryTree} labels
     * @param t
     *            the {@code BinaryTree} from which to remove the label
     * @return the smallest label in the given {@code BinaryTree}
     * @updates t
     * @requires IS_BST(t) and |t| > 0
     * @ensures <pre>
     * IS_BST(t)  and  removeSmallest = [the smallest label in #t]  and
     *  labels(t) = labels(#t) \ {removeSmallest}
     * </pre>
     */
    private static <T> T removeSmallest(BinaryTree<T> t) {
        assert t != null : "Violation of: t is not null";
        assert t.size() > 0 : "Violation of: |t| > 0";

        T smallest = null;
        BinaryTree<T> leftT = t.newInstance();
        BinaryTree<T> rightT = t.newInstance();
        T root = t.disassemble(leftT, rightT);
        if (leftT.height() > 0) {
            smallest = removeSmallest(leftT);
            t.assemble(root, leftT, rightT);
        } else {
            smallest = root;
            t.transferFrom(rightT);
        }
        return smallest;
    }

    /**
     * Finds label {@code x} in {@code t}, removes it from {@code t}, and
     * returns it.
     *
     * @param <T>
     *            type of {@code BinaryTree} labels
     * @param t
     *            the {@code BinaryTree} from which to remove label {@code x}
     * @param x
     *            the label to be removed
     * @return the removed label
     * @updates t
     * @requires IS_BST(t) and x is in labels(t)
     * @ensures <pre>
     * IS_BST(t)  and  removeFromTree = x  and
     *  labels(t) = labels(#t) \ {x}
     * </pre>
     */
    private static <T extends Comparable<T>> T removeFromTree(BinaryTree<T> t,
            T x) {
        assert t != null : "Violation of: t is not null";
        assert x != null : "Violation of: x is not null";
        assert t.size() > 0 : "Violation of: x is in labels(t)";

        T returnValue = null;
        BinaryTree<T> leftT = t.newInstance();
        BinaryTree<T> rightT = t.newInstance();
        T root = t.disassemble(leftT, rightT);
        if (root.equals(x)) {
            returnValue = root;
            if (rightT.height() > 0 && leftT.height() > 0) {
                root = removeSmallest(rightT);
                t.assemble(root, leftT, rightT);
            } else if (leftT.height() > 0) {
                t.transferFrom(leftT);
            } else if (rightT.height() > 0) {
                t.transferFrom(rightT);
            } else {
                t.clear();
            }
        } else {
            if (root.compareTo(x) > 0) {
                returnValue = removeFromTree(leftT, x);
            } else {
                returnValue = removeFromTree(rightT, x);
            }
            t.assemble(root, leftT, rightT);
        }

        return returnValue;
    }

    /**
     * Creator of initial representation.
     */
    private void createNewRep() {
        this.tree = new components.binarytree.BinaryTree1<T>();
    }

    /*
     * Constructors -----------------------------------------------------------
     */

    /**
     * No-argument constructor.
     */
    public Set3a() {
        this.createNewRep();
    }

    /*
     * Standard methods -------------------------------------------------------
     */

    @SuppressWarnings("unchecked")
    @Override
    public final Set<T> newInstance() {
        try {
            return this.getClass().getConstructor().newInstance();
        } catch (ReflectiveOperationException e) {
            throw new AssertionError(
                    "Cannot construct object of type " + this.getClass());
        }
    }

    @Override
    public final void clear() {
        this.createNewRep();
    }

    @Override
    public final void transferFrom(Set<T> source) {
        assert source != null : "Violation of: source is not null";
        assert source != this : "Violation of: source is not this";
        assert source instanceof Set3a<?> : ""
                + "Violation of: source is of dynamic type Set3<?>";
        /*
         * This cast cannot fail since the assert above would have stopped
         * execution in that case: source must be of dynamic type Set3a<?>, and
         * the ? must be T or the call would not have compiled.
         */
        Set3a<T> localSource = (Set3a<T>) source;
        this.tree = localSource.tree;
        localSource.createNewRep();
    }

    /*
     * Kernel methods ---------------------------------------------------------
     */

    @Override
    public final void add(T x) {
        assert x != null : "Violation of: x is not null";
        assert !this.contains(x) : "Violation of: x is not in this";

        insertInTree(this.tree, x);

    }

    @Override
    public final T remove(T x) {
        assert x != null : "Violation of: x is not null";
        assert this.contains(x) : "Violation of: x is in this";

        T returnValue = removeFromTree(this.tree, x);

        return returnValue;
    }

    @Override
    public final T removeAny() {
        assert this.size() > 0 : "Violation of: this /= empty_set";

        T returnValue = removeSmallest(this.tree);

        return returnValue;
    }

    @Override
    public final boolean contains(T x) {
        assert x != null : "Violation of: x is not null";

        boolean inTree = isInTree(this.tree, x);

        return inTree;
    }

    @Override
    public final int size() {

        return this.tree.size();
    }

    @Override
    public final Iterator<T> iterator() {
        return this.tree.iterator();
    }

}
