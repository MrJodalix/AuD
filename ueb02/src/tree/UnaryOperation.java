package tree;

import expression.AbstractExpression;
import expression.Expression;

/**
 * Abstrakte-klasse zur Zusammenfassung von Funktionalitäten der Unären Operatoren
 *
 * @author Ilana Schmara, Joshua-Scott Schöttke, Gruppe 21
 */
public abstract class UnaryOperation extends AbstractExpression {

    /**
     * Operator-Attribut
     */
    protected final Expression operand;
    /**
     * Konstruktor.
     *
     * @param childrenCount Anzahl der Unterknoten dieses Ausdrucks
     * @param operand der unäre Operator
     */
    public UnaryOperation(int childrenCount, Expression operand) {
        super(childrenCount);
        this.operand = operand;
        this.childrenCount++;
    }

    /**
     * String-darstellung der Unären Operatoren
     *
     * @param builder die String-builder Instanz
     * @param operator die String-darstellung des Operators
     */
    public void toString(StringBuilder builder, String operator) {
        assert (builder != null);
        builder.insert(0, "(").append(operator).append(operand).append(")");
    }

    /**
     * String-darstellung der Graphviz variante
     *
     * @param builder die String-builder Instanz
     * @param prefix das Präfix für die/den jeweilige/n Kante/Knoten
     * @param operator die String-darstellung des Operators
     */
    public void toGraphviz(StringBuilder builder, String prefix, String operator) {
        assert (builder != null);
        assert (prefix != null);
        String s = prefix + "_";
        builder.append(prefix).append(" [label=\"").append(operator).
                append(" [").append(childrenCount - 1).append("]\"]\n");
        builder.append(prefix).append(" -> ").append(s).append(" [label=\"\"]\n");
        StringBuilder next = new StringBuilder();
        operand.toGraphviz(next, s);
        builder.append(next);
    }

}
