package api;

import expression.Expression;
import tree.Constant;
import tree.Consequence;
import tree.Variable;
import tree.Identity;
import tree.Negation;
import tree.And;
import tree.Or;
import tree.Xor;
import tree.Equivalence;


/**
 * API zur Erstellung von Expression-Instanzen.
 * 
 * @author aan, avh, mhe, mre, tti, Ilana Schmara, Joshua-Scott Schöttke Gruppe 21
 * 
 */
public class ExpressionAPI {

    /**
     * Erzeugt einen neuen konstanten Ausdruck, dessen Wert entweder "true" oder "false" ist.
     * 
     * @param value Der Wert der Konstanten
     * @return Ein neuer konstanter Ausdruck.
     */
    public Expression makeConstantExpression(boolean value) {

        return new Constant(0, value);
    }

    /**
     * Erzeugt einen neuen variablen Ausdruck.
     * 
     * @param name Der Bezeichner der Variablen.
     * @pre Der Bezeichner darf nicht null sein.
     * @pre Der Bezeichner darf nur Buchstaben enthalten.
     * @pre Der Bezeichner darf nicht leer sein.
     * 
     * @return Ein neuer variabler Ausdruck.
     */
    public Expression makeVariableExpression(String name) {
        assert (name != null);
        assert (name.matches("^[a-zA-Z]+$"));

        return new Variable(0, name);
    }

    /**
     * Erzeugt einen neuen Ausdruck, der die Identitätsoperation auf einen bestehenden Ausdruck
     * anwendet.
     * 
     * @param operand Der Ausdruck, auf den die Operation angewendet wird.
     * @pre operand ist nicht null.
     * @return Ein neuer ID-Ausdruck.
     */
    public Expression makeIdExpression(Expression operand) {
        assert (operand != null);

        return new Identity(operand.getChildrenCount(), operand);
    }

    /**
     * Erzeugt einen neuen Ausdruck, der einen bestehenden Ausdruck negiert.
     * 
     * @param operand Der Ausdruck, der negiert werden soll.
     * @pre operand ist nicht null.
     * @return Ein neuer Negationsausdruck.
     */
    public Expression makeNotExpression(Expression operand) {
        assert (operand != null);

        return new Negation(operand.getChildrenCount(), operand);
    }

    /**
     * Erzeugt einen neuen Ausdruck mit einer binären Operation, die eine Und-Verknüpfung darstellt.
     * 
     * @param left Der linke Operand.
     * @param right Der rechte Operand.
     * @pre left und right sind nicht null.
     * @return Ein neuer Konjunktionsausdruck.
     */
    public Expression makeAndExpression(Expression left, Expression right) {
        assert (left != null) && (right != null);

        return new And(left.getChildrenCount() + right.getChildrenCount(), left, right);
    }

    /**
     * Erzeugt einen neuen Ausdruck mit einer binären Operation, die eine Oder-Verknüpfung
     * darstellt.
     * 
     * @param left Der linke Operand.
     * @param right Der rechte Operand.
     * @pre left und right sind nicht null.
     * @return Ein neuer Disjunktionsausdruck.
     */
    public Expression makeOrExpression(Expression left, Expression right) {
        assert (left != null) && (right != null);
        return new Or(left.getChildrenCount() + right.getChildrenCount(), left, right);

    }

    /**
     * Erzeugt einen neuen Ausdruck mit einer binären Operation, die eine Exklusiv-Oder-Verknüpfung
     * darstellt.
     * 
     * @param left Der linke Operand.
     * @param right Der rechte Operand.
     * @pre left und right sind nicht null.
     * @return Ein neuer Alternativausdruck.
     */
    public Expression makeXorExpression(Expression left, Expression right) {
        assert (left != null) && (right != null);

        return new Xor(left.getChildrenCount() + right.getChildrenCount(), left, right);
    }

    /**
     * Erzeugt einen neuen Ausdruck mit einer binären Operation, die eine Äquivalenz darstellt.
     * 
     * @param left Der linke Operand.
     * @param right Der rechte Operand.
     * @pre left und right sind nicht null.
     * @return Ein neuer Äquivalenzausdruck.
     */
    public Expression makeEquivalenceExpression(Expression left, Expression right) {
        assert (left != null) && (right != null);

        return new Equivalence(left.getChildrenCount() + right.getChildrenCount(), left, right);
    }

    /**
     * Erzeugt einen neuen Ausdruck mit einer binären Operation, die eine Implikation darstellt.
     * 
     * @param left Der linke Operand.
     * @param right Der rechte Operand.
     * @pre left und right sind nicht null.
     * @return Ein neuer Implikationsausdruck.
     */
    public Expression makeConsequenceExpression(Expression left, Expression right) {
        assert (left != null) && (right != null);

        return new Consequence(left.getChildrenCount() + right.getChildrenCount(), left, right);
    }

}
