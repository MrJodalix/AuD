package tree;

import expression.AbstractExpression;
import expression.Context;
import expression.IncompleteContextException;

/**
 * Zur Erstellung von der Expression-Instanz Constant.
 *
 * @author Ilana Schmara, Joshua-Scott Schöttke Gruppe 21
 */
public class Constant extends AbstractExpression {

    /**
     * Attribut für die Konstante
     */
    private final boolean bool;

    /**
     * Konstruktor
     *
     * @param childrenCount die Anzahl der Kindsknoten
     * @param bool der Wahrheitswert der Konstanten
     */
    public Constant(int childrenCount, boolean bool) {
        super(childrenCount);
        this.bool = bool;
        this.childrenCount++;
    }


    @Override
    public boolean evaluateShort(Context c) throws IncompleteContextException {
        return bool;
    }

    @Override
    public boolean evaluateComplete(Context c) throws IncompleteContextException {
        return bool;
    }

    @Override
    public boolean evaluateParallel(Context c, int bound) throws IncompleteContextException {
        //Konstanten haben keine Unterknoten
        return bool;
    }

    @Override
    public void toString(StringBuilder builder) {
        assert (builder != null);
        String s;
        if (bool) {
            s = "T";
        } else {
            s = "F";
        }
        builder.append(s);
    }

    @Override
    public void toGraphviz(StringBuilder builder, String prefix) {
        assert (builder != null);
        assert (prefix != null);
        builder.append(prefix).append(" [label=\"").append(this).append("\"]\n");
    }
}
