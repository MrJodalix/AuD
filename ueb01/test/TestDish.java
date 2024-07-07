import static org.junit.Assert.assertEquals;

import org.junit.Test;

import menu.Diet;
import menu.Dish;

/**
 * Tests für die Operationen eines Gerichtes
 *
 * @author aan, avh, mhe, mre, tti
 */
public class TestDish {


    /**
     * Test für {@link Dish#toString()}.
     */
    @Test
    public final void test_toString() {
        assertEquals("Beispiel aus der Aufgabenstellung", "Gericht1 14,99€", new Dish("Gericht1", 1499, Diet.VEGAN).toString());
        assertEquals("leerer Name", " 5,73€", new Dish("", 573, Diet.VEGAN).toString());
        assertEquals("null Name", "null 1,23€", new Dish(null, 123, Diet.VEGAN).toString());
        assertEquals("Preis unter 1€", "Suppe 0,65€", new Dish("Suppe", 65, Diet.VEGAN).toString());
        assertEquals("Preis unter 10 Cent", "Suppe 0,01€", new Dish("Suppe", 1, Diet.VEGAN).toString());
    }
}
