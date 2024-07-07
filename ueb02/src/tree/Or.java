package tree;

import expression.Expression;
import expression.Context;
import expression.IncompleteContextException;

/**
 * Zur Erstellung von der Expression-Instanz Or.
 *
 * @author Ilana Schmara, Joshua-Scott Schöttke Gruppe 21
 */
public class Or extends BinaryOperation {

    /** String-Darstellung des Operators */
    private final String operator = "||";

    /**
     * Konstruktor
     *
     * @param childrenCount die Anzahl der Kindsknoten
     * @param left das Linke Blatt
     * @param right das rechte Blatt
     */
    public Or(int childrenCount, Expression left, Expression right) {
        super(childrenCount, left, right); }

    @Override
    public boolean evaluateShort(Context c) throws IncompleteContextException {
        return (left.evaluateShort(c) || right.evaluateShort(c));
    }

    @Override
    public boolean evaluateComplete(Context c) throws IncompleteContextException {
        return (left.evaluateComplete(c) | right.evaluateComplete(c));
    }

    @Override
    public boolean evaluate(boolean[] leftRight) {
        return (leftRight[0] | leftRight[1]);
    }

    @Override
    public void toString(StringBuilder builder) {
        super.toString(builder, operator);
    }

    @Override
    public void toGraphviz(StringBuilder builder, String prefix) {
        super.toGraphviz(builder, prefix, operator);
    }
}
