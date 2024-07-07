package expression;

/**
 * Schnittstelle für einen Ausdruck.
 * 
 * Dieses Interface ist bereits komplett vorgegeben und darf nicht verändert werden.
 * 
 * @author aan, avh, mhe, mre, tti
 * 
 */
public interface Expression {

    /**
     * Wertet den Ausdruck verkürzt aus. Hierbei werden die Elemente des Ausdrucks von links nach
     * rechts ausgewertet. Steht das Ergebnis einer binären Operation fest, wird die weitere
     * Auswertung übersprungen.
     * 
     * @param c Der Kontext, in dem der Ausdruck ausgewertet werden soll. Falls der Kontext nicht
     *            benötigt wird, darf c leer oder null sein.
     * @return Der Wahrheitswert des Ausdrucks
     * @throws IncompleteContextException Sofern eine der Variablen innerhalb des Ausdrucks nicht im
     *             Kontext definiert ist
     */
    boolean evaluateShort(Context c) throws IncompleteContextException;

    /**
     * Wertet den Ausdruck vollständig aus, d.h. keiner der Operatoren wird verkürzt ausgewertet.
     * 
     * @param c Der Kontext, in dem der Ausdruck ausgewertet werden soll. Falls der Kontext nicht
     *            benötigt wird, darf c leer oder null sein.
     * @return Der Wahrheitswert des Ausdrucks.
     * @throws IncompleteContextException Sofern eine der Variablen innerhalb des Ausdrucks nicht im
     *             Kontext definiert ist
     */
    boolean evaluateComplete(Context c) throws IncompleteContextException;

    /**
     * Nimmt eine parallelisierte und vollständige Auswertung des Ausdrucks vor. Hierbei werden
     * Verarbeitungsschritte genau dann in eigene Threads ausgelagert, wenn die Anzahl der
     * Kindknoten einer binären Operation größer gleich der übergebenen Grenze ist.
     * 
     * @param c Der Kontext, in dem der Ausdruck ausgewertet werden soll. Falls der Kontext nicht
     *            benötigt wird, darf c leer oder null sein.
     * @param bound Die Grenze, ab der parallelisiert werden soll.
     * @return Der Wahrheitswert des Ausdrucks.
     * @throws IncompleteContextException Sofern eine der Variablen innerhalb des Ausdrucks nicht im
     *             Kontext definiert ist
     */
    boolean evaluateParallel(Context c, int bound) throws IncompleteContextException;

    /**
     * Gibt die Anzahl aller Unterknoten dieses Ausdrucks zurück.
     * 
     * @return Die Anzahl aller Unterknoten dieses Ausdrucks
     */
    int getChildrenCount();

    /**
     * Erzeugt eine Stringdarstellung des Ausdrucks.
     * 
     * @return Die Stringdarstellung des Ausdrucks
     */
    @Override
    String toString();

    /**
     * Hilfsmethode für toString(), schreibt die Stringdarstellung des (Teil-)Ausdrucks in den
     * übergebenen StringBuilder.
     * 
     * @param builder Der StringBuilder, in den die Stringdarstellung des (Teil-)Ausdrucks
     *            geschrieben werden soll
     * @pre builder != null
     */
    void toString(StringBuilder builder);

    /**
     * Gibt die Graphviz-Repräsentation des Baums als String zurück.
     * 
     * @return Die Graphviz-Repräsentation des Baums
     */
    String toGraphviz();

    /**
     * Hilfsmethode für toGraphviz(), schreibt die Graphviz-Repräsentation des (Teil-)Baums in den
     * übergebenen StringBuilder. Die Kopfzeile "digraph G {\n" und die Fußzeile "}\n" des
     * Graphviz-Formats wird in dieser Methode nicht geschrieben.
     * 
     * @param builder Der StringBuilder, in den die Repräsentation des (Teil-)Baum geschrieben
     *            werden soll
     * @param prefix Das Präfix für das aktuelle Element des Baums
     * @pre builder != null
     * @pre prefix != null
     */
    void toGraphviz(StringBuilder builder, String prefix);

}
