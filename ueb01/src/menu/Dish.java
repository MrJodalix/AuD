package menu;

/**
 * Dieses Record stellt ein einzelnes Gericht dar.
 *
 * @param name Names des Gerichtes
 * @param price Preis des Gerichtes in Cent
 * @param diet Ernährungsform des Gerichtes
 *
 * @author aan, avh, mhe, mre, tti, Ilana Schmara, Joshua-Scott Schöttke, Gruppe 21
 */
public record Dish(String name, int price, Diet diet) {

    private static final int EURO = 100;

    /**
     * Liefert eine Zeichenkette zurück, welche zur Visualisierung dieses Gerichtes dient.
     * Ein Gericht wird angegeben durch den Namen und den Preis des Gerichtes,
     * durch ein Leerzeichen getrennt.
     * <br>
     * Der Preis wird in Euro angegeben, wobei ein Euro-Zeichen direkt hinter der
     * Zahl folgt (z.b. 9,99€). Euro und Cent sind dabei durch ein Komma separiert.
     * Bei einem einstelligen Centbetrag wird eine Null vorangestellt.
     *
     * @return Zeichenkette, welche dieses Gericht darstellt
     */
    @Override
    public String toString() {
        return String.format("%s %d,%,02d€", name, (price / EURO), (price % EURO));
    }
}
