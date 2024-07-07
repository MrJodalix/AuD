package util;

/**
 * Eine Klasse, die ein generisches Paar darstellt.
 * 
 * @author uhl, aan, avh, mhe, mre, tti
 * 
 * @param <L> Typ des linken Werts
 * @param <R> Typ des rechten Werts
 */
public class Pair<L, R> {
    private final L left;
    private final R right;

    /**
     * Konstruktor
     * 
     * @param left linker Wert
     * @param right rechter Wert
     */
    public Pair(L left, R right) {
        this.left = left;
        this.right = right;
    }

    /**
     * Gibt den linken Wert des Paares wieder
     *
     * @return Liefert den linken Wert des Paars.
     */
    public L l() {
        return this.left;
    }

    /**
     * Gibt den rechten Wert des Paares wieder
     *
     * @return Liefert den rechten Wert des Paars.
     */
    public R r() {
        return this.right;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((left == null) ? 0 : left.hashCode());
        result = prime * result + ((right == null) ? 0 : right.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Pair)) {
            return false;
        }
        Pair<?, ?> other = (Pair<?, ?>) obj;
        if (left == null) {
            if (other.left != null) {
                return false;
            }
        } else if (!left.equals(other.left)) {
            return false;
        }
        if (right == null) {
            if (other.right != null) {
                return false;
            }
        } else if (!right.equals(other.right)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return this.l() + ":" + this.r();
    }

}
