package expression;

/**
 * Ein threadsicherer Zähler.
 * 
 * Diese Klasse ist bereits komplett vorgegeben und darf nicht verändert werden.
 * 
 * @author aan, avh, mhe, mre, tti
 */
public class Counter {

    /**
     * aktueller Zählerstand
     */
    private static int counter = 0;

    /**
     * @return Liefert den aktuellen Zählerstand.
     */
    public static synchronized int getCounter() {
        return counter;
    }

    /**
     * Initialisiert den Zählerstand mit 0.
     */
    public static synchronized void initialize() {
        counter = 0;
    }

    /**
     * Erhöht den Zählerstand um 1.
     */
    public static synchronized void increment() {
        counter++;
    }

}
