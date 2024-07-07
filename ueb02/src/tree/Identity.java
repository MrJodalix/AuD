package tree;

import expression.Context;
import expression.Expression;
import expression.IncompleteContextException;

/**
 * Zur Erstellung von der Expression-Instanz Identity.
 *
 * @author Ilana Schmara, Joshua-Scott Schöttke Gruppe 21
 */
public class Identity extends UnaryOperation {

    /**
     * String-Attribut zur Darstellung
     */
    protected final String operator = "()";
    /**
     * Konstruktor
     *
     * @param childrenCount die Anzahl der Kindsknoten
     * @param operand der folgende Operand
     */
    public Identity(int childrenCount, Expression operand) {
        super(childrenCount, operand);
    }

    @Override
    public boolean evaluateShort(Context c) throws IncompleteContextException {
        return operand.evaluateShort(c);
    }

    @Override
    public boolean evaluateComplete(Context c) throws IncompleteContextException {
        return operand.evaluateComplete(c);
    }

    @Override
    public boolean evaluateParallel(Context c, int bound) throws IncompleteContextException {
        return operand.evaluateParallel(c, bound);
    }

    @Override
    public void toString(StringBuilder builder) {
        assert (builder != null);
        builder.insert(0, "(").append(operand).append(")");
    }

    @Override
    public void toGraphviz(StringBuilder builder, String prefix) {
        super.toGraphviz(builder, prefix, operator);
    }
}
