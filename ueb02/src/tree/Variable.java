package tree;

import expression.AbstractExpression;
import expression.Context;
import expression.IncompleteContextException;


/**
 * Zur Erstellung von der Expression-Instanz Variable.
 *
 * @author Ilana Schmara, Joshua-Scott Schöttke Gruppe 21
 */
public class Variable extends AbstractExpression {

    /**
     * Attribut für die Variable
     */
    private final String variable;

    /**
     * Konstruktor
     *
     * @param childrenCount die Anzahl der Kindsknoten
     * @param variable die Variable
     */
    public Variable(int childrenCount, String variable) {
        super(childrenCount);
        this.variable = variable;
        this.childrenCount++;
    }

    @Override
    public boolean evaluateShort(Context c) throws IncompleteContextException {
        //Kontext null, obwohl er benoetigt wird oder Wort nicht im Kontext
        Boolean con;
        if (c == null) {
            throw new IncompleteContextException(variable);
        } else {
            con = c.get(variable);
        }
        return con;
    }

    @Override
    public boolean evaluateComplete(Context c) throws IncompleteContextException {
        return evaluateShort(c);
    }

    @Override
    public boolean evaluateParallel(Context c, int bound) throws IncompleteContextException {
        return evaluateShort(c);
    }

    @Override
    public void toString(StringBuilder builder) {
        assert (builder != null);
        builder.append(variable);
    }

    @Override
    public void toGraphviz(StringBuilder builder, String prefix) {
        assert (builder != null);
        assert (prefix != null);
        builder.append(prefix).append(" [label=\"").append(variable).append("\"]\n");
    }
}
