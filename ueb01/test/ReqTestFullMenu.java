import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collector;

import org.junit.Test;

import menu.Category;
import menu.Diet;
import menu.Dish;
import menu.FullMenu;

public class ReqTestFullMenu {

    /**
     * Hilfsmethode für Testfälle. Prüft, ob zwei übergebene Kategorien gleich sind, ist kein
     * Referenzvergleich. Setzt voraus, dass {@link Category#getName()} und
     * {@link Category#getDishes()} funktioniert.
     *
     * @param message  Nachricht für den AssertionError
     * @param expected erwartete Kategorie
     * @param actual   tatsächliche Kategorie
     */
    private static void assertCategoryEquals(String message, Category expected, Category actual) {
        if (expected == null) {
            assertNull(message, actual);
            return;
        }

        assertNotNull(message, actual);

        assertEquals(message + ": different name", expected.getName(), actual.getName());
        assertArrayEquals(message + ": different dishes", expected.getDishes().toArray(),
                actual.getDishes().toArray());
    }

    /**
     * Test für {@link FullMenu#getCategories()}. Nutzt
     * {@link FullMenu#addCategory(String, String)}.
     */
    @Test public final void test_getCategories() {
        final FullMenu menu = new FullMenu("Speisekarte");

        assertTrue("must be empty after creation", menu.getCategories().isEmpty());

        final String[] ids   = { "A", "C", "b" };
        final String[] names = { "Vorspeisen", "Hauptspeisen", "Getränke" };
        for (int i = 0; i < ids.length; i++) {
            menu.addCategory(ids[i], names[i]);
        }

        final Category[] categories = new Category[ids.length];
        for (int i = 0; i < categories.length; i++) {
            categories[i] = menu.getCategory(ids[i]);
        }

        for (int i = 0; i < categories.length; i++) {
            assertEquals("category name not equals", names[i], categories[i].getName());
        }

        final int  index = 0;
        final Dish dish  = new Dish("Suppe", 150, Diet.VEGETARIAN);
        categories[index].addDish(dish);
        assertArrayEquals("got copy of category", new Dish[] { dish },
                menu.getCategory(ids[index]).getDishes().toArray());

        assertArrayEquals("not the right categories in the right order", categories,
                menu.getCategories().toArray());
    }
    /**
     * Test für {@link FullMenu#addCategory(String, String)}. Nutzt
     * {@link FullMenu#hasCategoryWithId(String)}, {@link FullMenu#hasCategoryWithName(String)} und
     * {@link FullMenu#getCategory(String)}.
     */
    @Test public final void test_addCategory() {
        final FullMenu menu = new FullMenu("Speisekarte");

        final String[] ids   = { "A", "b" };
        final String[] names = { "Vorspeisen", "Hauptspeisen", };

        boolean addSuccess = false;

        for (int i = 0; i < ids.length; i++) {
            addSuccess = menu.addCategory(ids[i], names[i]);
            assertTrue("adding failed (" + i + ")", addSuccess);
            assertTrue("category with id does not exist (" + i + ")", menu.hasCategoryWithId(ids[i]));
            assertTrue("category with name does not exist (" + i + ")", menu.hasCategoryWithName(names[i]));
            assertTrue("category must have no dishes after creation(" + i + ")",
                    menu.getCategory(ids[i]).getDishes().isEmpty());
        }

        // Kategorie mit gleichem Namen vorhanden
        addSuccess = menu.addCategory("C", names[1]);
        assertFalse("duplicate id shouldn't be added", addSuccess);
        assertFalse("id should not exist after failed add", menu.hasCategoryWithId("C"));
        assertTrue("failed add should not change existing names", menu.hasCategoryWithName(names[1]));

        // Kategorie mit gleicher ID vorhanden
        addSuccess = menu.addCategory(ids[1], "Getränke");
        assertFalse("duplicate name shouldn't be added", addSuccess);
        assertTrue("failed add should not change existing ids", menu.hasCategoryWithId(ids[1]));
        assertFalse("name should not exist after failed add", menu.hasCategoryWithName("Getränke"));
    }
    /**
     * Test für {@link FullMenu#addDish(String, String, int, Diet)}. Nutzt
     * {@link FullMenu#addCategory(String, String)}, {@link FullMenu#hasDish(String, int)}.
     */
    @Test public final void test_addDish() {
        final String categoryId = "o";

        final FullMenu menu = new FullMenu("Speisekarte");
        menu.addCategory(categoryId, "Vorspeise");

        final int dishId = menu.addDish(categoryId, "Suppe", 743, Diet.OMNIVORE);
        assertEquals("first dishId must be 1", 1, dishId);
        assertTrue("does not contain dishId after add", menu.hasDish(categoryId, dishId));

        assertEquals("add with non existing category must return 0", 0,
                menu.addDish("irgendwasAnderes", "Suppe", 743, Diet.OMNIVORE));
    }
    /**
     * Test für {@link FullMenu#toString()}.
     */
    @Test public final void test_toString() {
        final String name = "Speisekarte";

        final String[] categoryIds   = { "A", "B", "C" };
        final String[] categoryNames = { "Kategorie1", "Kategorie2", "Kategorie3" };

        final FullMenu menu = new FullMenu(name);



        final String[] actual = menu.toString().split("\n");

        final List<String> expected = new ArrayList<>();
        expected.add("----------SPEISEKARTE----------");

        assertArrayEquals(expected.toArray(), actual);
    }
    /**
     * Test für {@link FullMenu#toString()}.
     */
    @Test public final void test_removeCategory() {
        final String name = "Speisekarte";

        final String[] categoryIds   = { "A", "B", "C", "D" };
        final String[] categoryNames = { "Kategorie1", "Kategorie2", "Kategorie3", "Kategorie4" };

        final FullMenu menu = new FullMenu(name);

        for (int i = 0; i < categoryIds.length; i++) {
            menu.addCategory(categoryIds[i], categoryNames[i]);
        }

        menu.addDish(categoryIds[0], "Gericht1", 999, Diet.OMNIVORE);
        menu.addDish(categoryIds[0], "Gericht2", 999, Diet.VEGAN);
        menu.addDish(categoryIds[0], "Gericht3", 99, Diet.PESCETARIAN);
        menu.addDish(categoryIds[0], "Gericht4", 9900, Diet.VEGETARIAN);

        menu.addDish(categoryIds[2], "Gericht1", 1499, Diet.OMNIVORE);
        menu.addDish(categoryIds[2], "Gericht2", 1999, Diet.VEGAN);
        menu.addDish(categoryIds[2], "Gericht3", 90, Diet.PESCETARIAN);
        menu.addDish(categoryIds[2], "Gericht7", 9, Diet.VEGETARIAN);

        menu.removeCategory("A");

        final String[] actual = menu.toString().split("\n");

        final List<String> expected = new ArrayList<>();
        expected.add("----------SPEISEKARTE----------");
        expected.add("");
        expected.add("B. Kategorie2");
        expected.add("");
        expected.add("C. Kategorie3");
        expected.add("   1. Gericht1 14,99€");
        expected.add("   2. Gericht2 19,99€");
        expected.add("   3. Gericht3 0,90€");
        expected.add("   4. Gericht7 0,09€");
        expected.add("");
        expected.add("D. Kategorie4");

        assertArrayEquals(expected.toArray(), actual);
    }
    /**
     * Test für {@link FullMenu#toString()}.
     */
    @Test public final void test_removeDish() {
        final String name = "Speisekarte";

        final String[] categoryIds   = { "A", "B", "C", "D" };
        final String[] categoryNames = { "Kategorie1", "Kategorie2", "Kategorie3", "Kategorie4" };

        final FullMenu menu = new FullMenu(name);

        for (int i = 0; i < categoryIds.length; i++) {
            menu.addCategory(categoryIds[i], categoryNames[i]);
        }

        menu.addDish(categoryIds[0], "Gericht1", 999, Diet.OMNIVORE);
        menu.addDish(categoryIds[0], "Gericht2", 999, Diet.VEGAN);
        menu.addDish(categoryIds[0], "Gericht3", 99, Diet.PESCETARIAN);
        menu.addDish(categoryIds[0], "Gericht4", 9900, Diet.VEGETARIAN);

        menu.addDish(categoryIds[2], "Gericht1", 1499, Diet.OMNIVORE);
        menu.addDish(categoryIds[2], "Gericht2", 1999, Diet.VEGAN);
        menu.addDish(categoryIds[2], "Gericht3", 90, Diet.PESCETARIAN);
        menu.addDish(categoryIds[2], "Gericht7", 9, Diet.VEGETARIAN);

        menu.removeDish("A", 2);

        final String[] actual = menu.toString().split("\n");

        final List<String> expected = new ArrayList<>();
        expected.add("----------SPEISEKARTE----------");
        expected.add("");
        expected.add("A. Kategorie1");
        expected.add("   1. Gericht1 9,99€");
        expected.add("   2. Gericht3 0,99€");
        expected.add("   3. Gericht4 99,00€");
        expected.add("");
        expected.add("B. Kategorie2");
        expected.add("");
        expected.add("C. Kategorie3");
        expected.add("   1. Gericht1 14,99€");
        expected.add("   2. Gericht2 19,99€");
        expected.add("   3. Gericht3 0,90€");
        expected.add("   4. Gericht7 0,09€");
        expected.add("");
        expected.add("D. Kategorie4");

        assertArrayEquals(expected.toArray(), actual);
    }
    /**
     * Test für {@link FullMenu#toString()}.
     */
    @Test public final void test_transferDish() {
        final String name = "Speisekarte";

        final String[] categoryIds   = { "A", "B" };
        final String[] categoryNames = { "Kategorie1", "Kategorie2" };

        final FullMenu menu = new FullMenu(name);

        for (int i = 0; i < categoryIds.length; i++) {
            menu.addCategory(categoryIds[i], categoryNames[i]);
        }

        menu.addDish(categoryIds[0], "Gericht1", 999, Diet.OMNIVORE);
        menu.addDish(categoryIds[0], "Gericht2", 999, Diet.VEGAN);
        menu.addDish(categoryIds[0], "Gericht3", 99, Diet.PESCETARIAN);
        menu.addDish(categoryIds[0], "Gericht4", 9900, Diet.VEGETARIAN);

        menu.transferDish("A", "B", 2);

        final String[] actual = menu.toString().split("\n");

        final List<String> expected = new ArrayList<>();
        expected.add("----------SPEISEKARTE----------");
        expected.add("");
        expected.add("A. Kategorie1");
        expected.add("   1. Gericht1 9,99€");
        expected.add("   2. Gericht3 0,99€");
        expected.add("   3. Gericht4 99,00€");
        expected.add("");
        expected.add("B. Kategorie2");
        expected.add("   1. Gericht2 9,99€");

        assertArrayEquals(expected.toArray(), actual);
    }
    /**
     * Test für {@link FullMenu#toString()}.
     */
    @Test public final void test_getFirstDishOfDiet() {
        final String name = "Speisekarte";

        final String[] categoryIds   = { "A", "B", "C", "D" };
        final String[] categoryNames = { "Kategorie1", "Kategorie2", "Kategorie3", "Kategorie4" };

        final FullMenu menu = new FullMenu(name);

        for (int i = 0; i < categoryIds.length; i++) {
            menu.addCategory(categoryIds[i], categoryNames[i]);
        }

        menu.addDish(categoryIds[0], "Gericht1", 999, Diet.OMNIVORE);
        menu.addDish(categoryIds[0], "Gericht2", 999, Diet.OMNIVORE);
        menu.addDish(categoryIds[0], "Gericht3", 99, Diet.OMNIVORE);
        menu.addDish(categoryIds[0], "Gericht4", 9900, Diet.OMNIVORE);

        menu.addDish(categoryIds[1], "Gericht1", 999, Diet.OMNIVORE);
        menu.addDish(categoryIds[1], "Gericht2", 999, Diet.VEGAN);
        menu.addDish(categoryIds[1], "Gericht3", 99, Diet.PESCETARIAN);
        menu.addDish(categoryIds[1], "Gericht5", 9900, Diet.VEGETARIAN);

        menu.addDish(categoryIds[2], "Gericht1", 999, Diet.OMNIVORE);
        menu.addDish(categoryIds[2], "Gericht2", 999, Diet.VEGAN);
        menu.addDish(categoryIds[2], "Gericht3", 99, Diet.PESCETARIAN);
        menu.addDish(categoryIds[2], "Gericht4", 9900, Diet.VEGETARIAN);

        menu.addDish(categoryIds[3], menu.getFirstDishOfDiet(Diet.VEGETARIAN).name(),
                menu.getFirstDishOfDiet(Diet.VEGETARIAN).price(),
                menu.getFirstDishOfDiet(Diet.VEGETARIAN).diet());

        final String[] actual = menu.toString().split("\n");

        final List<String> expected = new ArrayList<>();
        expected.add("----------SPEISEKARTE----------");
        expected.add("");
        expected.add("A. Kategorie1");
        expected.add("   1. Gericht1 9,99€");
        expected.add("   2. Gericht2 9,99€");
        expected.add("   3. Gericht3 0,99€");
        expected.add("   4. Gericht4 99,00€");
        expected.add("");
        expected.add("B. Kategorie2");
        expected.add("   1. Gericht1 9,99€");
        expected.add("   2. Gericht2 9,99€");
        expected.add("   3. Gericht3 0,99€");
        expected.add("   4. Gericht5 99,00€");
        expected.add("");
        expected.add("C. Kategorie3");
        expected.add("   1. Gericht1 9,99€");
        expected.add("   2. Gericht2 9,99€");
        expected.add("   3. Gericht3 0,99€");
        expected.add("   4. Gericht4 99,00€");
        expected.add("");
        expected.add("D. Kategorie4");
        expected.add("   1. Gericht5 99,00€");

        assertArrayEquals(expected.toArray(), actual);
    }
    /**
     * Test für {@link FullMenu#toString()}.
     */
    @Test public final void test_getDishesWithAtLeastDiet() {
        final String name = "Speisekarte";

        final String[] categoryIds   = { "A", "B", "C", "D" };
        final String[] categoryNames = { "Kategorie1", "Kategorie2", "Kategorie3", "Kategorie4" };

        final FullMenu menu = new FullMenu(name);



        for (int i = 0; i < categoryIds.length; i++) {
            menu.addCategory(categoryIds[i], categoryNames[i]);
        }

        menu.addDish(categoryIds[0], "Pute", 999, Diet.OMNIVORE);
        menu.addDish(categoryIds[0], "Schwein", 999, Diet.OMNIVORE);
        menu.addDish(categoryIds[0], "Rind", 99, Diet.OMNIVORE);
        menu.addDish(categoryIds[0], "Hähnchen", 9900, Diet.OMNIVORE);

        menu.addDish(categoryIds[1], "Gulasch", 999, Diet.OMNIVORE);
        menu.addDish(categoryIds[1], "Kopfsalat", 999, Diet.VEGAN);
        menu.addDish(categoryIds[1], "Lachs", 99, Diet.PESCETARIAN);
        menu.addDish(categoryIds[1], "Nudeln", 9900, Diet.VEGETARIAN);

        menu.addDish(categoryIds[2], "Eier", 999, Diet.OMNIVORE);
        menu.addDish(categoryIds[2], "Süßkartoffeln", 9900, Diet.VEGETARIAN);
        menu.addDish(categoryIds[2], "Barsch", 99, Diet.PESCETARIAN);
        menu.addDish(categoryIds[2], "Gemüsesuppe", 999, Diet.VEGAN);

        List<Dish> dishList = menu.getDishesWithAtLeastDiet(Diet.VEGETARIAN);

        menu.getCategory(categoryIds[3]).addDish(dishList.get(0));
        menu.getCategory(categoryIds[3]).addDish(dishList.get(1));
        menu.getCategory(categoryIds[3]).addDish(dishList.get(2));
        menu.getCategory(categoryIds[3]).addDish(dishList.get(3));

        final String[] actual = menu.toString().split("\n");

        final List<String> expected = new ArrayList<>();
        expected.add("----------SPEISEKARTE----------");
        expected.add("");
        expected.add("A. Kategorie1");
        expected.add("   1. Pute 9,99€");
        expected.add("   2. Schwein 9,99€");
        expected.add("   3. Rind 0,99€");
        expected.add("   4. Hähnchen 99,00€");
        expected.add("");
        expected.add("B. Kategorie2");
        expected.add("   1. Gulasch 9,99€");
        expected.add("   2. Kopfsalat 9,99€");
        expected.add("   3. Lachs 0,99€");
        expected.add("   4. Nudeln 99,00€");
        expected.add("");
        expected.add("C. Kategorie3");
        expected.add("   1. Eier 9,99€");
        expected.add("   2. Süßkartoffeln 99,00€");
        expected.add("   3. Barsch 0,99€");
        expected.add("   4. Gemüsesuppe 9,99€");
        expected.add("");
        expected.add("D. Kategorie4");
        expected.add("   1. Kopfsalat 9,99€");
        expected.add("   2. Nudeln 99,00€");
        expected.add("   3. Gemüsesuppe 9,99€");
        expected.add("   4. Süßkartoffeln 99,00€");

        assertArrayEquals(expected.toArray(), actual);
    }
    /**
     * Test für {@link FullMenu#toString()}.
     */
    @Test public final void test_getDishesWithAtLeastDiet2() {
        final String name = "Speisekarte";

        final String[] categoryIds   = { "A", "B", "C", "D" };
        final String[] categoryNames = { "Kategorie1", "Kategorie2", "Kategorie3", "Kategorie4" };

        final FullMenu menu = new FullMenu(name);

        for (int i = 0; i < categoryIds.length; i++) {
            menu.addCategory(categoryIds[i], categoryNames[i]);
        }

        menu.addDish(categoryIds[0], "Pute", 999, Diet.OMNIVORE);
        menu.addDish(categoryIds[0], "Schwein", 999, Diet.OMNIVORE);
        menu.addDish(categoryIds[0], "Rind", 99, Diet.OMNIVORE);
        menu.addDish(categoryIds[0], "Hähnchen", 9900, Diet.OMNIVORE);

        menu.addDish(categoryIds[1], "Gulasch", 999, Diet.OMNIVORE);
        menu.addDish(categoryIds[1], "Kopfsalat", 999, Diet.VEGAN);
        menu.addDish(categoryIds[1], "Lachs", 99, Diet.PESCETARIAN);
        menu.addDish(categoryIds[1], "Nudeln", 9900, Diet.VEGETARIAN);

        menu.addDish(categoryIds[2], "Eier", 999, Diet.OMNIVORE);
        menu.addDish(categoryIds[2], "Süßkartoffeln", 9900, Diet.VEGETARIAN);
        menu.addDish(categoryIds[2], "Barsch", 99, Diet.PESCETARIAN);
        menu.addDish(categoryIds[2], "Gemüsesuppe", 999, Diet.VEGETARIAN);

        List<Dish> dishList = menu.getDishesWithAtLeastDiet(Diet.VEGETARIAN);

        menu.getCategory(categoryIds[3]).addDish(dishList.get(0));
        menu.getCategory(categoryIds[3]).addDish(dishList.get(1));
        menu.getCategory(categoryIds[3]).addDish(dishList.get(2));
        menu.getCategory(categoryIds[3]).addDish(dishList.get(3));

        final String[] actual = menu.toString().split("\n");

        final List<String> expected = new ArrayList<>();
        expected.add("----------SPEISEKARTE----------");
        expected.add("");
        expected.add("A. Kategorie1");
        expected.add("   1. Pute 9,99€");
        expected.add("   2. Schwein 9,99€");
        expected.add("   3. Rind 0,99€");
        expected.add("   4. Hähnchen 99,00€");
        expected.add("");
        expected.add("B. Kategorie2");
        expected.add("   1. Gulasch 9,99€");
        expected.add("   2. Kopfsalat 9,99€");
        expected.add("   3. Lachs 0,99€");
        expected.add("   4. Nudeln 99,00€");
        expected.add("");
        expected.add("C. Kategorie3");
        expected.add("   1. Eier 9,99€");
        expected.add("   2. Süßkartoffeln 99,00€");
        expected.add("   3. Barsch 0,99€");
        expected.add("   4. Gemüsesuppe 9,99€");
        expected.add("");
        expected.add("D. Kategorie4");
        expected.add("   1. Kopfsalat 9,99€");
        expected.add("   2. Nudeln 99,00€");
        expected.add("   3. Süßkartoffeln 99,00€");
        expected.add("   4. Gemüsesuppe 9,99€");

        assertArrayEquals(expected.toArray(), actual);
    }
    /**
     * Test für {@link FullMenu#toString()}.
     */
    @Test public final void test_getDishesWithAtLeastDiet3() {
        final String name = "Speisekarte";

        final String[] categoryIds   = { "A", "B", "C", "D" };
        final String[] categoryNames = { "Kategorie1", "Kategorie2", "Kategorie3", "Kategorie4" };

        final FullMenu menu = new FullMenu(name);

        for (int i = 0; i < categoryIds.length; i++) {
            menu.addCategory(categoryIds[i], categoryNames[i]);
        }

        menu.addDish(categoryIds[0], "Pute", 999, Diet.OMNIVORE);
        menu.addDish(categoryIds[0], "Schwein", 999, Diet.OMNIVORE);
        menu.addDish(categoryIds[0], "Rind", 99, Diet.OMNIVORE);
        menu.addDish(categoryIds[0], "Hähnchen", 9900, Diet.OMNIVORE);

        menu.addDish(categoryIds[1], "Gulasch", 999, Diet.OMNIVORE);
        menu.addDish(categoryIds[1], "Kopfsalat", 999, Diet.VEGAN);
        menu.addDish(categoryIds[1], "Lachs", 99, Diet.PESCETARIAN);
        menu.addDish(categoryIds[1], "Nudeln", 9900, Diet.VEGETARIAN);

        menu.addDish(categoryIds[2], "Eier", 999, Diet.OMNIVORE);
        menu.addDish(categoryIds[2], "Süßkartoffeln", 9900, Diet.VEGETARIAN);
        menu.addDish(categoryIds[2], "Barsch", 99, Diet.PESCETARIAN);
        menu.addDish(categoryIds[2], "Gemüsesuppe", 999, Diet.VEGETARIAN);

        List<Dish> dishList = menu.getDishesWithAtLeastDiet(Diet.OMNIVORE);

        menu.getCategory(categoryIds[3]).addDish(dishList.get(0));
        menu.getCategory(categoryIds[3]).addDish(dishList.get(1));
        menu.getCategory(categoryIds[3]).addDish(dishList.get(2));
        menu.getCategory(categoryIds[3]).addDish(dishList.get(3));
        menu.getCategory(categoryIds[3]).addDish(dishList.get(4));
        menu.getCategory(categoryIds[3]).addDish(dishList.get(5));
        menu.getCategory(categoryIds[3]).addDish(dishList.get(6));
        menu.getCategory(categoryIds[3]).addDish(dishList.get(7));
        menu.getCategory(categoryIds[3]).addDish(dishList.get(8));
        menu.getCategory(categoryIds[3]).addDish(dishList.get(9));
        menu.getCategory(categoryIds[3]).addDish(dishList.get(10));
        menu.getCategory(categoryIds[3]).addDish(dishList.get(11));

        final String[] actual = menu.toString().split("\n");

        final List<String> expected = new ArrayList<>();
        expected.add("----------SPEISEKARTE----------");
        expected.add("");
        expected.add("A. Kategorie1");
        expected.add("   1. Pute 9,99€");
        expected.add("   2. Schwein 9,99€");
        expected.add("   3. Rind 0,99€");
        expected.add("   4. Hähnchen 99,00€");
        expected.add("");
        expected.add("B. Kategorie2");
        expected.add("   1. Gulasch 9,99€");
        expected.add("   2. Kopfsalat 9,99€");
        expected.add("   3. Lachs 0,99€");
        expected.add("   4. Nudeln 99,00€");
        expected.add("");
        expected.add("C. Kategorie3");
        expected.add("   1. Eier 9,99€");
        expected.add("   2. Süßkartoffeln 99,00€");
        expected.add("   3. Barsch 0,99€");
        expected.add("   4. Gemüsesuppe 9,99€");
        expected.add("");
        expected.add("D. Kategorie4");
        expected.add("   1. Pute 9,99€");
        expected.add("   2. Schwein 9,99€");
        expected.add("   3. Rind 0,99€");
        expected.add("   4. Hähnchen 99,00€");
        expected.add("   5. Kopfsalat 9,99€");
        expected.add("   6. Nudeln 99,00€");
        expected.add("   7. Lachs 0,99€");
        expected.add("   8. Gulasch 9,99€");
        expected.add("   9. Süßkartoffeln 99,00€");
        expected.add("   10. Gemüsesuppe 9,99€");
        expected.add("   11. Barsch 0,99€");
        expected.add("   12. Eier 9,99€");

        assertArrayEquals(expected.toArray(), actual);
    }
    /**
     * Test für {@link FullMenu#toString()}.
     */
    @Test public final void test_getDishesWithPriceBetween() {
        final String name = "Speisekarte";

        final String[] categoryIds = { "A", "B", "C", "D", "E" };
        final String[] categoryNames =
                { "Kategorie1", "Kategorie2", "Kategorie3", "Kategorie4", "Kategorie5" };

        final FullMenu menu = new FullMenu(name);

        for (int i = 0; i < categoryIds.length; i++) {
            menu.addCategory(categoryIds[i], categoryNames[i]);
        }

        menu.addDish(categoryIds[0], "Gericht1", 666, Diet.OMNIVORE);
        menu.addDish(categoryIds[0], "Gericht2", 999, Diet.OMNIVORE);
        menu.addDish(categoryIds[0], "Gericht3", 99, Diet.OMNIVORE);
        menu.addDish(categoryIds[0], "Gericht4", 9900, Diet.OMNIVORE);

        menu.addDish(categoryIds[1], "Gericht5", 4699, Diet.VEGAN);
        menu.addDish(categoryIds[1], "Gericht6", 999, Diet.VEGAN);
        menu.addDish(categoryIds[1], "Gericht7", 9, Diet.PESCETARIAN);
        menu.addDish(categoryIds[1], "Gericht8", 9900, Diet.VEGETARIAN);

        List<Dish> copyList = new ArrayList<>(menu.getDishesWithPriceBetween(999, 9900));

        menu.getCategory(categoryIds[2]).addDish(copyList.get(0));
        menu.getCategory(categoryIds[2]).addDish(copyList.get(1));
        menu.getCategory(categoryIds[2]).addDish(copyList.get(2));

        menu.addDish(categoryIds[3], "Gericht9", 999, Diet.OMNIVORE);
        menu.addDish(categoryIds[3], "Gericht10", 3759, Diet.VEGAN);
        menu.addDish(categoryIds[3], "Gericht11", 99, Diet.PESCETARIAN);
        menu.addDish(categoryIds[3], "Gericht12", 9900, Diet.VEGETARIAN);

        List<Dish> dishList  = menu.getDishesWithPriceBetween(999, 3759);

        menu.getCategory(categoryIds[4]).getDishes().addAll(dishList);

        menu.getCategory(categoryIds[4]).addDish(dishList.get(0));
        menu.getCategory(categoryIds[4]).addDish(dishList.get(1));
        menu.getCategory(categoryIds[4]).addDish(dishList.get(2));
        menu.getCategory(categoryIds[4]).addDish(dishList.get(3));
        menu.getCategory(categoryIds[4]).addDish(dishList.get(4));

        final String[] actual = menu.toString().split("\n");

        final List<String> expected = new ArrayList<>();
        expected.add("----------SPEISEKARTE----------");
        expected.add("");
        expected.add("A. Kategorie1");
        expected.add("   1. Gericht1 6,66€");
        expected.add("   2. Gericht2 9,99€");
        expected.add("   3. Gericht3 0,99€");
        expected.add("   4. Gericht4 99,00€");
        expected.add("");
        expected.add("B. Kategorie2");
        expected.add("   1. Gericht5 46,99€");
        expected.add("   2. Gericht6 9,99€");
        expected.add("   3. Gericht7 0,09€");
        expected.add("   4. Gericht8 99,00€");
        expected.add("");
        expected.add("C. Kategorie3");
        expected.add("   1. Gericht2 9,99€");
        expected.add("   2. Gericht5 46,99€");
        expected.add("   3. Gericht6 9,99€");
        expected.add("");
        expected.add("D. Kategorie4");
        expected.add("   1. Gericht9 9,99€");
        expected.add("   2. Gericht10 37,59€");
        expected.add("   3. Gericht11 0,99€");
        expected.add("   4. Gericht12 99,00€");
        expected.add("");
        expected.add("E. Kategorie5");
        expected.add("   1. Gericht2 9,99€");
        expected.add("   2. Gericht6 9,99€");
        expected.add("   3. Gericht2 9,99€");
        expected.add("   4. Gericht6 9,99€");
        expected.add("   5. Gericht9 9,99€");

        assertArrayEquals(expected.toArray(), actual);
    }
    /**
     * Test für {@link FullMenu#toString()}.
     */
    @Test public final void test_hasCategoriesWithIds() {
        final String firstName = "Speisekarte";

        final String[] categoryIds  = { "A", "B", "C" };
        final String[] categoryIds2 = { "A", "B", "C", "D" };
        final String[] categoryIds3 = { "A", "C", "D", "E" };
        final String[] categoryNames =
                { "Kategorie1", "Kategorie2", "Kategorie3", "Kategorie4", "Kategorie5" };

        Collection<String> collection = List.of(categoryIds);

        final FullMenu menu1 = new FullMenu(firstName);

        menu1.addCategory(categoryIds[0], categoryNames[0]);
        menu1.addCategory(categoryIds[1], categoryNames[1]);

        assertFalse("Das Menu enthält eine unterschiedliche Anzahl an Categories", menu1.hasCategoriesWithIds(collection));

        menu1.addCategory(categoryIds[2], categoryNames[2]);

        assertTrue("Alle Enthaltenen Categorien", menu1.hasCategoriesWithIds(collection));

        menu1.addCategory(categoryIds2[3], categoryNames[3]);

        assertTrue("Zu viele Kategorien in menu", menu1.hasCategoriesWithIds(collection));

        menu1.removeCategory(categoryIds[2]);
        menu1.removeCategory(categoryIds2[3]);

        menu1.addCategory(categoryIds3[3], categoryNames[4]);

        assertFalse("Zu viele Kategorien in menu", menu1.hasCategoriesWithIds(collection));

    }
    /**
     * Test für {@link FullMenu#toString()}.
     */
    @Test public final void test_combineMenu1() {
        final String firstName  = "Speisekarte";
        final String secondName = "Kombikarte";

        final String[] categoryIds   = { "A", "B", "C" };
        final String[] categoryNames = { "Kategorie1", "Kategorie2", "Kategorie3" };

        final FullMenu menu1 = new FullMenu(firstName);
        final FullMenu menu2 = new FullMenu(secondName);

        menu1.addCategory(categoryIds[0], categoryNames[0]);
        menu1.addCategory(categoryIds[1], categoryNames[1]);

        menu2.addCategory(categoryIds[1], categoryNames[1]);
        menu2.addCategory(categoryIds[2], categoryNames[2]);

        menu1.addDish(categoryIds[0], "Gericht1", 666, Diet.OMNIVORE);
        menu1.addDish(categoryIds[0], "Gericht2", 999, Diet.OMNIVORE);
        menu1.addDish(categoryIds[0], "Gericht3", 99, Diet.OMNIVORE);
        menu1.addDish(categoryIds[0], "Gericht4", 9900, Diet.OMNIVORE);

        menu1.addDish(categoryIds[1], "Gericht5", 999, Diet.OMNIVORE);
        menu1.addDish(categoryIds[1], "Gericht6", 4699, Diet.VEGAN);
        menu1.addDish(categoryIds[1], "Gericht7", 9, Diet.PESCETARIAN);
        menu1.addDish(categoryIds[1], "Gericht8", 9900, Diet.VEGETARIAN);

        menu2.addDish(categoryIds[1], "Gericht1", 666, Diet.OMNIVORE);
        menu2.addDish(categoryIds[1], "Gericht2", 999, Diet.OMNIVORE);
        menu2.addDish(categoryIds[1], "Gericht3", 99, Diet.OMNIVORE);
        menu2.addDish(categoryIds[1], "Gericht4", 9900, Diet.OMNIVORE);

        menu2.addDish(categoryIds[2], "Gericht9", 999, Diet.OMNIVORE);
        menu2.addDish(categoryIds[2], "Gericht10", 4699, Diet.VEGAN);
        menu2.addDish(categoryIds[2], "Gericht11", 9, Diet.PESCETARIAN);
        menu2.addDish(categoryIds[2], "Gericht12", 9900, Diet.VEGETARIAN);

        final FullMenu combMenu = menu1.combineMenu(menu2);

        final String[] actual = combMenu.toString().split("\n");

        final List<String> expected = new ArrayList<>();
        expected.add("----------SPEISEKARTE - KOMBIKARTE----------");
        expected.add("");
        expected.add("A. Kategorie1");
        expected.add("   1. Gericht1 6,66€");
        expected.add("   2. Gericht2 9,99€");
        expected.add("   3. Gericht3 0,99€");
        expected.add("   4. Gericht4 99,00€");
        expected.add("");
        expected.add("B. Kategorie2");
        expected.add("   1. Gericht5 9,99€");
        expected.add("   2. Gericht6 46,99€");
        expected.add("   3. Gericht7 0,09€");
        expected.add("   4. Gericht8 99,00€");
        expected.add("   5. Gericht1 6,66€");
        expected.add("   6. Gericht2 9,99€");
        expected.add("   7. Gericht3 0,99€");
        expected.add("   8. Gericht4 99,00€");
        expected.add("");
        expected.add("C. Kategorie3");
        expected.add("   1. Gericht9 9,99€");
        expected.add("   2. Gericht10 46,99€");
        expected.add("   3. Gericht11 0,09€");
        expected.add("   4. Gericht12 99,00€");

        assertArrayEquals(expected.toArray(), actual);
    }

    /**
     * Komplett unterschiedlich
     * <p>
     * Test für {@link FullMenu#toString()}.
     */
    @Test public final void test_combineMenu2() {
        final String firstName  = "Speisekarte";
        final String secondName = "Kombikarte";

        final String[] categoryIds   = { "A", "B", "C", "D" };
        final String[] categoryNames = { "Kategorie1", "Kategorie2", "Kategorie3", "Kategorie4" };

        final FullMenu menu1 = new FullMenu(firstName);
        final FullMenu menu2 = new FullMenu(secondName);

        menu1.addCategory(categoryIds[0], categoryNames[0]);
        menu1.addCategory(categoryIds[2], categoryNames[2]);

        menu2.addCategory(categoryIds[1], categoryNames[1]);
        menu2.addCategory(categoryIds[3], categoryNames[3]);

        menu1.addDish(categoryIds[0], "Gericht1", 666, Diet.OMNIVORE);
        menu1.addDish(categoryIds[0], "Gericht2", 999, Diet.OMNIVORE);
        menu1.addDish(categoryIds[0], "Gericht3", 99, Diet.OMNIVORE);
        menu1.addDish(categoryIds[0], "Gericht4", 9900, Diet.OMNIVORE);

        menu1.addDish(categoryIds[2], "Gericht5", 999, Diet.OMNIVORE);
        menu1.addDish(categoryIds[2], "Gericht6", 4699, Diet.VEGAN);
        menu1.addDish(categoryIds[2], "Gericht7", 9, Diet.PESCETARIAN);
        menu1.addDish(categoryIds[2], "Gericht8", 9900, Diet.VEGETARIAN);

        menu2.addDish(categoryIds[1], "Gericht1", 666, Diet.OMNIVORE);
        menu2.addDish(categoryIds[1], "Gericht2", 999, Diet.OMNIVORE);
        menu2.addDish(categoryIds[1], "Gericht3", 99, Diet.OMNIVORE);
        menu2.addDish(categoryIds[1], "Gericht4", 9900, Diet.OMNIVORE);

        menu2.addDish(categoryIds[3], "Gericht9", 999, Diet.OMNIVORE);
        menu2.addDish(categoryIds[3], "Gericht10", 4699, Diet.VEGAN);
        menu2.addDish(categoryIds[3], "Gericht11", 9, Diet.PESCETARIAN);
        menu2.addDish(categoryIds[3], "Gericht12", 9900, Diet.VEGETARIAN);

        final FullMenu combMenu = menu1.combineMenu(menu2);

        final String[] actual = combMenu.toString().split("\n");

        final List<String> expected = new ArrayList<>();
        expected.add("----------SPEISEKARTE - KOMBIKARTE----------");
        expected.add("");
        expected.add("A. Kategorie1");
        expected.add("   1. Gericht1 6,66€");
        expected.add("   2. Gericht2 9,99€");
        expected.add("   3. Gericht3 0,99€");
        expected.add("   4. Gericht4 99,00€");
        expected.add("");
        expected.add("B. Kategorie2");
        expected.add("   1. Gericht1 6,66€");
        expected.add("   2. Gericht2 9,99€");
        expected.add("   3. Gericht3 0,99€");
        expected.add("   4. Gericht4 99,00€");
        expected.add("");
        expected.add("C. Kategorie3");
        expected.add("   1. Gericht5 9,99€");
        expected.add("   2. Gericht6 46,99€");
        expected.add("   3. Gericht7 0,09€");
        expected.add("   4. Gericht8 99,00€");
        expected.add("");
        expected.add("D. Kategorie4");
        expected.add("   1. Gericht9 9,99€");
        expected.add("   2. Gericht10 46,99€");
        expected.add("   3. Gericht11 0,09€");
        expected.add("   4. Gericht12 99,00€");

        assertArrayEquals(expected.toArray(), actual);
    }

    /**
     * Kategorien teilweise ungleich
     * <p>
     * Test für {@link FullMenu#toString()}.
     */
    @Test public final void test_combineMenu3() {
        final String firstName  = "Speisekarte";
        final String secondName = "Kombikarte";

        final String[] categoryIds   = { "A", "B" };
        final String[] categoryNames = { "Kategorie1", "Kategorie2" };

        final FullMenu menu1 = new FullMenu(firstName);
        final FullMenu menu2 = new FullMenu(secondName);

        menu1.addCategory(categoryIds[0], categoryNames[0]);
        menu1.addCategory(categoryIds[1], categoryNames[1]);

        menu2.addCategory(categoryIds[0], categoryNames[1]);
        menu2.addCategory(categoryIds[1], categoryNames[0]);

        menu1.addDish(categoryIds[0], "Gericht1", 666, Diet.OMNIVORE);
        menu1.addDish(categoryIds[0], "Gericht2", 999, Diet.OMNIVORE);
        menu1.addDish(categoryIds[0], "Gericht3", 99, Diet.OMNIVORE);
        menu1.addDish(categoryIds[0], "Gericht4", 9900, Diet.OMNIVORE);

        menu1.addDish(categoryIds[1], "Gericht5", 999, Diet.OMNIVORE);
        menu1.addDish(categoryIds[1], "Gericht6", 4699, Diet.VEGAN);
        menu1.addDish(categoryIds[1], "Gericht7", 9, Diet.PESCETARIAN);
        menu1.addDish(categoryIds[1], "Gericht8", 9900, Diet.VEGETARIAN);

        menu2.addDish(categoryIds[0], "Gericht1", 666, Diet.OMNIVORE);
        menu2.addDish(categoryIds[0], "Gericht2", 999, Diet.OMNIVORE);
        menu2.addDish(categoryIds[0], "Gericht3", 99, Diet.OMNIVORE);
        menu2.addDish(categoryIds[0], "Gericht4", 9900, Diet.OMNIVORE);

        menu2.addDish(categoryIds[1], "Gericht9", 999, Diet.OMNIVORE);
        menu2.addDish(categoryIds[1], "Gericht10", 4699, Diet.VEGAN);
        menu2.addDish(categoryIds[1], "Gericht11", 9, Diet.PESCETARIAN);
        menu2.addDish(categoryIds[1], "Gericht12", 9900, Diet.VEGETARIAN);

        final FullMenu combMenu = menu1.combineMenu(menu2);

        final String[] actual = combMenu.toString().split("\n");

        final List<String> expected = new ArrayList<>();
        expected.add("----------SPEISEKARTE - KOMBIKARTE----------");

        assertArrayEquals(expected.toArray(), actual);
    }

    /**
     * Kategorien teilweise ungleich
     * <p>
     * Test für {@link FullMenu#toString()}.
     */
    @Test public final void test_combineMenu4() {
        final String firstName  = "Speisekarte";
        final String secondName = "Kombikarte";
        final String thirdName  = "Kinderkarte";

        final String[] categoryIds   = { "A", "B", "C" };
        final String[] categoryNames = { "Kategorie1", "Kategorie2", "Kategorie3" };

        final FullMenu menu1 = new FullMenu(firstName);
        final FullMenu menu2 = new FullMenu(secondName);
        final FullMenu menu3 = new FullMenu(thirdName);

        menu1.addCategory(categoryIds[0], categoryNames[0]);
        menu1.addCategory(categoryIds[1], categoryNames[1]);

        menu2.addCategory(categoryIds[0], categoryNames[0]);
        menu2.addCategory(categoryIds[1], categoryNames[1]);

        menu3.addCategory(categoryIds[0], categoryNames[0]);
        menu3.addCategory(categoryIds[1], categoryNames[1]);

        menu1.addDish(categoryIds[0], "Gericht1", 666, Diet.OMNIVORE);
        menu1.addDish(categoryIds[0], "Gericht2", 999, Diet.OMNIVORE);
        menu1.addDish(categoryIds[0], "Gericht3", 99, Diet.OMNIVORE);
        menu1.addDish(categoryIds[0], "Gericht4", 9900, Diet.OMNIVORE);

        menu1.addDish(categoryIds[1], "Gericht5", 999, Diet.OMNIVORE);
        menu1.addDish(categoryIds[1], "Gericht6", 4699, Diet.VEGAN);
        menu1.addDish(categoryIds[1], "Gericht7", 9, Diet.PESCETARIAN);
        menu1.addDish(categoryIds[1], "Gericht8", 9900, Diet.VEGETARIAN);

        menu2.addDish(categoryIds[0], "Gericht1", 666, Diet.OMNIVORE);
        menu2.addDish(categoryIds[0], "Gericht2", 999, Diet.OMNIVORE);
        menu2.addDish(categoryIds[0], "Gericht3", 99, Diet.OMNIVORE);
        menu2.addDish(categoryIds[0], "Gericht4", 9900, Diet.OMNIVORE);

        menu2.addDish(categoryIds[1], "Gericht5", 999, Diet.OMNIVORE);
        menu2.addDish(categoryIds[1], "Gericht6", 4699, Diet.VEGAN);
        menu2.addDish(categoryIds[1], "Gericht7", 9, Diet.PESCETARIAN);
        menu2.addDish(categoryIds[1], "Gericht8", 9900, Diet.VEGETARIAN);

        menu3.addDish(categoryIds[0], "Gericht1", 666, Diet.OMNIVORE);
        menu3.addDish(categoryIds[0], "Gericht2", 999, Diet.OMNIVORE);
        menu3.addDish(categoryIds[0], "Gericht3", 99, Diet.OMNIVORE);
        menu3.addDish(categoryIds[0], "Gericht4", 9900, Diet.OMNIVORE);

        menu3.addDish(categoryIds[1], "Gericht5", 999, Diet.OMNIVORE);
        menu3.addDish(categoryIds[1], "Gericht6", 4699, Diet.VEGAN);
        menu3.addDish(categoryIds[1], "Gericht7", 9, Diet.PESCETARIAN);
        menu3.addDish(categoryIds[1], "Gericht8", 9900, Diet.VEGETARIAN);

        final FullMenu combMenu = menu1.combineMenu(menu2);

        final String[] actual = combMenu.toString().split("\n");

        final List<String> expected = new ArrayList<>();
        expected.add("----------SPEISEKARTE - KOMBIKARTE----------");
        expected.add("");
        expected.add("A. Kategorie1");
        expected.add("   1. Gericht1 6,66€");
        expected.add("   2. Gericht2 9,99€");
        expected.add("   3. Gericht3 0,99€");
        expected.add("   4. Gericht4 99,00€");
        expected.add("   5. Gericht1 6,66€");
        expected.add("   6. Gericht2 9,99€");
        expected.add("   7. Gericht3 0,99€");
        expected.add("   8. Gericht4 99,00€");
        expected.add("");
        expected.add("B. Kategorie2");
        expected.add("   1. Gericht5 9,99€");
        expected.add("   2. Gericht6 46,99€");
        expected.add("   3. Gericht7 0,09€");
        expected.add("   4. Gericht8 99,00€");
        expected.add("   5. Gericht5 9,99€");
        expected.add("   6. Gericht6 46,99€");
        expected.add("   7. Gericht7 0,09€");
        expected.add("   8. Gericht8 99,00€");

        assertArrayEquals(expected.toArray(), actual);

        final FullMenu combMenu2 = combMenu.combineMenu(menu3);

        final String[] actual2 = combMenu2.toString().split("\n");

        final List<String> expected2 = new ArrayList<>();
        expected2.add("----------SPEISEKARTE - KOMBIKARTE - KINDERKARTE----------");
        expected2.add("");
        expected2.add("A. Kategorie1");
        expected2.add("   1. Gericht1 6,66€");
        expected2.add("   2. Gericht2 9,99€");
        expected2.add("   3. Gericht3 0,99€");
        expected2.add("   4. Gericht4 99,00€");
        expected2.add("   5. Gericht1 6,66€");
        expected2.add("   6. Gericht2 9,99€");
        expected2.add("   7. Gericht3 0,99€");
        expected2.add("   8. Gericht4 99,00€");
        expected2.add("   9. Gericht1 6,66€");
        expected2.add("   10. Gericht2 9,99€");
        expected2.add("   11. Gericht3 0,99€");
        expected2.add("   12. Gericht4 99,00€");
        expected2.add("");
        expected2.add("B. Kategorie2");
        expected2.add("   1. Gericht5 9,99€");
        expected2.add("   2. Gericht6 46,99€");
        expected2.add("   3. Gericht7 0,09€");
        expected2.add("   4. Gericht8 99,00€");
        expected2.add("   5. Gericht5 9,99€");
        expected2.add("   6. Gericht6 46,99€");
        expected2.add("   7. Gericht7 0,09€");
        expected2.add("   8. Gericht8 99,00€");
        expected2.add("   9. Gericht5 9,99€");
        expected2.add("   10. Gericht6 46,99€");
        expected2.add("   11. Gericht7 0,09€");
        expected2.add("   12. Gericht8 99,00€");

        assertArrayEquals(expected2.toArray(), actual2);
    }

    /**
     * Test für {@link FullMenu#toString()}.
     */
    @Test public final void test_intersectMenu() {
        final String firstName  = "Speisekarte";
        final String secondName = "Kombikarte";
        final String thirdName  = "Kinderkarte";

        final String[] categoryIds   = { "A", "B", "C" };
        final String[] categoryNames = { "Kategorie1", "Kategorie2", "Kategorie3" };

        final FullMenu menu1 = new FullMenu(firstName);
        final FullMenu menu2 = new FullMenu(secondName);
        final FullMenu menu3 = new FullMenu(thirdName);

        menu1.addCategory(categoryIds[0], categoryNames[0]);
        menu1.addCategory(categoryIds[1], categoryNames[1]);

        menu2.addCategory(categoryIds[1], categoryNames[1]);
        menu2.addCategory(categoryIds[2], categoryNames[2]);

        menu3.addCategory(categoryIds[1], categoryNames[1]);

        menu1.addDish(categoryIds[0], "Gericht1", 666, Diet.OMNIVORE);
        menu1.addDish(categoryIds[0], "Gericht2", 999, Diet.OMNIVORE);
        menu1.addDish(categoryIds[0], "Gericht3", 99, Diet.OMNIVORE);
        menu1.addDish(categoryIds[0], "Gericht4", 9900, Diet.OMNIVORE);

        menu1.addDish(categoryIds[1], "Gericht5", 999, Diet.OMNIVORE);
        menu1.addDish(categoryIds[1], "Gericht6", 4699, Diet.VEGAN);
        menu1.addDish(categoryIds[1], "Gericht7", 9, Diet.PESCETARIAN);
        menu1.addDish(categoryIds[1], "Gericht8", 9900, Diet.VEGETARIAN);

        menu2.addDish(categoryIds[1], "Gericht5", 999, Diet.OMNIVORE);
        menu2.addDish(categoryIds[1], "Gericht6", 4699, Diet.VEGAN);
        menu2.addDish(categoryIds[1], "Gericht7", 9, Diet.PESCETARIAN);
        menu2.addDish(categoryIds[1], "Gericht8", 9900, Diet.VEGETARIAN);

        menu2.addDish(categoryIds[2], "Gericht9", 999, Diet.OMNIVORE);
        menu2.addDish(categoryIds[2], "Gericht10", 4699, Diet.VEGAN);
        menu2.addDish(categoryIds[2], "Gericht11", 9, Diet.PESCETARIAN);
        menu2.addDish(categoryIds[2], "Gericht12", 9900, Diet.VEGETARIAN);

        menu3.addDish(categoryIds[1], "Gericht5", 999, Diet.OMNIVORE);
        menu3.addDish(categoryIds[1], "Gericht6", 4699, Diet.VEGAN);
        menu3.addDish(categoryIds[1], "Gericht7", 9, Diet.PESCETARIAN);
        menu3.addDish(categoryIds[1], "Gericht8", 9900, Diet.VEGETARIAN);

        final FullMenu interMenu = menu1.intersectMenu(menu2);

        final String[] actual = interMenu.toString().split("\n");

        final List<String> expected = new ArrayList<>();
        expected.add("----------SPEISEKARTE----------");
        expected.add("");
        expected.add("B. Kategorie2");
        expected.add("   1. 1_Gericht5 9,99€");
        expected.add("   2. 1_Gericht6 46,99€");
        expected.add("   3. 1_Gericht7 0,09€");
        expected.add("   4. 1_Gericht8 99,00€");
        expected.add("   5. 2_Gericht5 9,99€");
        expected.add("   6. 2_Gericht6 46,99€");
        expected.add("   7. 2_Gericht7 0,09€");
        expected.add("   8. 2_Gericht8 99,00€");

        assertArrayEquals(expected.toArray(), actual);

        final FullMenu interMenu2 = interMenu.intersectMenu(menu3);

        final String[] actual2 = interMenu2.toString().split("\n");

        final List<String> expected2 = new ArrayList<>();
        expected2.add("----------SPEISEKARTE----------");
        expected2.add("");
        expected2.add("B. Kategorie2");
        expected2.add("   1. 1_1_Gericht5 9,99€");
        expected2.add("   2. 1_1_Gericht6 46,99€");
        expected2.add("   3. 1_1_Gericht7 0,09€");
        expected2.add("   4. 1_1_Gericht8 99,00€");
        expected2.add("   5. 1_2_Gericht5 9,99€");
        expected2.add("   6. 1_2_Gericht6 46,99€");
        expected2.add("   7. 1_2_Gericht7 0,09€");
        expected2.add("   8. 1_2_Gericht8 99,00€");
        expected2.add("   9. 2_Gericht5 9,99€");
        expected2.add("   10. 2_Gericht6 46,99€");
        expected2.add("   11. 2_Gericht7 0,09€");
        expected2.add("   12. 2_Gericht8 99,00€");

        assertArrayEquals(expected2.toArray(), actual2);
    }

}
