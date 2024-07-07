package tree;

import expression.Context;
import expression.Expression;
import expression.IncompleteContextException;

/**
 * Zur Erstellung von der Expression-Instanzen Negation.
 *
 * @author Ilana Schmara, Joshua-Scott Schöttke Gruppe 21
 */
public class Negation extends UnaryOperation {

    /**
     * String Attribut
     */
    protected final String operator = "!";

    /**
     * Konstruktor
     *
     * @param childrenCount die Anzahl der Kindsknoten
     * @param operand der folgende Operand
     */
    public Negation(int childrenCount, Expression operand) {
        super(childrenCount, operand);
    }

    @Override
    public boolean evaluateShort(Context c) throws IncompleteContextException {
        return (!operand.evaluateShort(c));
    }

    @Override
    public boolean evaluateComplete(Context c) throws IncompleteContextException {
        return (!operand.evaluateComplete(c));
    }

    @Override
    public boolean evaluateParallel(Context c, int bound) throws IncompleteContextException {
        return (!operand.evaluateParallel(c, bound));
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

