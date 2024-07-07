package tree;

import expression.AbstractExpression;
import expression.Expression;
import expression.IncompleteContextException;
import expression.Context;
import expression.ExpressionRunner;
import expression.Counter;

/**
 * Abstrakte Klasse um Funktionalität von binären Ausdrücken zusammenzufassen.
 *
 * @author Ilana Schmara, Joshua-Scott Schöttke Gruppe 21
 */
public abstract class BinaryOperation extends AbstractExpression {

    /**
     * Attribut für das linke Blatt
     */
    protected final Expression left;

    /**
     * Attribut für das rechte Blatt
     */
    protected final Expression right;

    /**
     * Konstruktor
     *
     * @param childrenCount die Anzahl der Kindsknoten
     * @param left          das Linke Blatt
     * @param right         das rechte Blatt
     */
    public BinaryOperation(int childrenCount, Expression left, Expression right) {
        super(childrenCount);
        this.left = left;
        this.right = right;
        this.childrenCount++;
    }

    /**
     * Hilfsmethode um Codeverdopplung zu vermeiden
     *
     * @param c Der Kontext in dem geprüft werden soll
     * @param bound die Grenze ab der parallelisiert werden soll
     * @return Ein zwei elementiges Array, welches die booleschen Werte aus den Teilbäumen enthält
     * @throws IncompleteContextException wenn eine Variable nicht im Kontext ist
     */
    public boolean evaluateParallel(Context c, int bound)
            throws IncompleteContextException {

        //Wenn die Anzahl der Unterknoten des aktuellen Ausdrucks groesser gleich bound ist,
        if ((left.getChildrenCount() + right.getChildrenCount()) >= bound) {
            //neuen Thread starten
            ExpressionRunner runner = new ExpressionRunner(right, c, bound);
            Thread           thread = new Thread(runner);
            thread.start();
            //Threadzaehler erhoehen
            Counter.increment();
            boolean leftResult = left.evaluateParallel(c, bound);

            try {
                thread.join();
            } catch (InterruptedException e) {
                throw new AssertionError(e);
            }

            //IncompleteContextException, wenn diese in dem anderen Thread aufgetreten ist
            if (runner.getResult() == null) {
                throw runner.getException();
            }

            boolean[] leftRight = new boolean[2];
            leftRight[0] = leftResult;
            leftRight[1] = runner.getResult();

            return evaluate(leftRight);

        } else {
            //Wenn keine Parallelisierung benoetigt wurde
            return evaluateComplete(c);
        }

    }

    /**
     * Abstrakte Hilfsmethode zur Auswertung von booleschen Werten
     *
     * @param leftRight ein Array welches zwei Werte repräsentiert
     * @return die Auswertung in der jeweiligen Klasse
     */
    protected abstract boolean evaluate(boolean[] leftRight);

    /**
     * Hilfsmethode für die String-Methoden von binären Ausdrücken, da diese alle die gleiche
     * Methode nutzen bis auf den binären Ausdruck.
     *
     * @param builder Der StringBuilder, in den die Stringdarstellung des (Teil-)Ausdrucks
     *            geschrieben werden soll
     * @param operator der binäre Operator, des jeweiligen Knotens
     */
    protected void toString(StringBuilder builder, String operator) {
        builder.append("(").append(left.toString()).append(" ").append(operator).append(" ")
                .append(right.toString()).append(")");
    }

    /**
     * Hilfsmethode für die Graphiz-Methoden von binären Ausdrücken, da diese alle die gleiche
     * Methode nutzen bis auf den binären Ausdruck.
     *
     * @param builder Der StringBuilder, in den die Repräsentation des (Teil-)Baum geschrieben
     *            werden soll
     * @param prefix Das Präfix für das aktuelle Element des Baums
     * @param operator der binäre Operator, des jeweiligen Knotens
     */
    protected void toGraphviz(StringBuilder builder, String prefix, String operator) {
        assert (builder != null);
        assert (prefix != null);
        String prefixLeft = prefix + "l";
        String prefixRight = prefix + "r";
        builder.append(prefix).append(" [label=\"").append(operator)
                .append(" [").append(this.childrenCount - 1).append("]\"]\n");
        builder.append(prefix).append(" -> ").append(prefixLeft).append(" [label=\"\"]\n");
        builder.append(prefix).append(" -> ").append(prefixRight).append(" [label=\"\"]\n");
        StringBuilder nextLeft = new StringBuilder();
        StringBuilder nextRight = new StringBuilder();
        left.toGraphviz(nextLeft, prefixLeft);
        builder.append(nextLeft);
        right.toGraphviz(nextRight, prefixRight);
        builder.append(nextRight);
    }

}
