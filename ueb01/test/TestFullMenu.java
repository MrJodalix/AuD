import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.ArrayList;

import org.junit.Test;

import menu.Category;
import menu.Diet;
import menu.Dish;
import menu.FullMenu;

public class TestFullMenu {


    /**
     * Hilfsmethode für Testfälle.
     * Prüft, ob zwei übergebene Kategorien gleich sind, ist kein Referenzvergleich.
     * Setzt voraus, dass {@link Category#getName()} und {@link Category#getDishes()} funktioniert.
     *
     * @param message Nachricht für den AssertionError
     * @param expected erwartete Kategorie
     * @param actual tatsächliche Kategorie
     */
    private static void assertCategoryEquals(String message, Category expected, Category actual) {
        if (expected == null) {
            assertNull(message, actual);
            return;
        }

        assertNotNull(message, actual);

        assertEquals(message + ": different name", expected.getName(), actual.getName());
        assertArrayEquals(message + ": different dishes", expected.getDishes().toArray(), actual.getDishes().toArray());
    }

    /**
     * Test für den Konstruktor {@link menu.FullMenu#FullMenu(String)}.
     * Nutzt dafür {@link menu.FullMenu#getName()}.
     */
    @Test
    public final void test_Constructor() {
        final String name = "Speisekarte";

        final FullMenu menu = new FullMenu(name);

        assertNotNull("menu is null", menu);
        assertEquals("menu name is wrong", name, menu.getName());
    }

    /**
     * Test für {@link FullMenu#getCategory(String)}.
     * Nutzt {@link FullMenu#addCategory(String, String)}.
     */
    @Test
    public final void test_getCategory() {
        final String name = "Vorspeisen";
        final String id = "A";

        final FullMenu menu = new FullMenu("Speisekarte");
        assertNull("must return null if no fitting category exists", menu.getCategory("B"));
        assertNull("category with id exists bevor add", menu.getCategory(id));

        final boolean addSuccess = menu.addCategory(id, name);
        assertTrue("adding failed", addSuccess);

        final Category expectedCategory = new Category(name);
        final Category actualCategory = menu.getCategory(id);
        assertCategoryEquals("returned category is not equals to added category", expectedCategory, actualCategory);

        final Dish dish = new Dish("Suppe", 150, Diet.VEGETARIAN);
        actualCategory.addDish(dish);
        assertArrayEquals("got copy of category", new Dish[] { dish }, menu.getCategory(id).getDishes().toArray());
    }

    /**
     * Test für {@link FullMenu#getCategories()}.
     * Nutzt {@link FullMenu#addCategory(String, String)}.
     */
    @Test
    public final void test_getCategories() {
        final FullMenu menu = new FullMenu("Speisekarte");

        assertTrue("must be empty after creation", menu.getCategories().isEmpty());

        final String[] ids = { "A", "B", "C" };
        final String[] names = { "Vorspeisen", "Hauptspeisen", "Getränke"};
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

        final int index = 0;
        final Dish dish = new Dish("Suppe", 150, Diet.VEGETARIAN);
        categories[index].addDish(dish);
        assertArrayEquals("got copy of category", new Dish[] { dish }, menu.getCategory(ids[index]).getDishes().toArray());


        assertArrayEquals("not the right categories in the right order", categories, menu.getCategories().toArray());
    }


    /**
     * Test für {@link FullMenu#addCategory(String, String)}.
     * Nutzt {@link FullMenu#hasCategoryWithId(String)}, {@link FullMenu#hasCategoryWithName(String)} und {@link FullMenu#getCategory(String)}.
     */
    @Test
    public final void test_addCategory() {
        final FullMenu menu = new FullMenu("Speisekarte");

        final String[] ids = { "A", "b" };
        final String[] names = { "Vorspeisen", "Hauptspeisen",};

        boolean addSuccess = false;

        for (int i = 0; i < ids.length; i++) {
            addSuccess = menu.addCategory(ids[i], names[i]);
            assertTrue("adding failed (" + i + ")", addSuccess);
            assertTrue("category with id does not exist (" + i + ")", menu.hasCategoryWithId(ids[i]));
            assertTrue("category with name does not exist (" + i + ")", menu.hasCategoryWithName(names[i]));
            assertTrue("category must have no dishes after creation(" + i + ")", menu.getCategory(ids[i]).getDishes().isEmpty());
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
     * Test für {@link FullMenu#addDish(String, String, int, Diet)}.
     * Nutzt {@link FullMenu#addCategory(String, String)}, {@link FullMenu#hasDish(String, int)}.
     */
    @Test
    public final void test_addDish() {
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
    @Test
    public final void test_toString() {
        final String name = "Speisekarte";

        final String[] categoryIds = {"A", "B", "C"};
        final String[] categoryNames = {"Kategorie1", "Kategorie2", "Kategorie3"};

        final FullMenu menu = new FullMenu(name);

        for(int i = 0; i < categoryIds.length; i++) {
            menu.addCategory(categoryIds[i], categoryNames[i]);
        }

        menu.addDish(categoryIds[0], "Gericht1", 999, Diet.OMNIVORE);
        menu.addDish(categoryIds[0], "Gericht2", 999, Diet.VEGAN);
        menu.addDish(categoryIds[0], "Gericht3", 99, Diet.PESCETARIAN);

        menu.addDish(categoryIds[2], "Gericht1", 1499, Diet.OMNIVORE);
        menu.addDish(categoryIds[2], "Gericht2", 1999, Diet.VEGAN);
        menu.addDish(categoryIds[2], "Gericht3", 999, Diet.PESCETARIAN);

        final String[] actual = menu.toString().split("\n");

        final List<String> expected = new ArrayList<>();
        expected.add("----------SPEISEKARTE----------");
        expected.add("");
        expected.add("A. Kategorie1");
        expected.add("   1. Gericht1 9,99€");
        expected.add("   2. Gericht2 9,99€");
        expected.add("   3. Gericht3 0,99€");
        expected.add("");
        expected.add("B. Kategorie2");
        expected.add("");
        expected.add("C. Kategorie3");
        expected.add("   1. Gericht1 14,99€");
        expected.add("   2. Gericht2 19,99€");
        expected.add("   3. Gericht3 9,99€");

        assertArrayEquals(expected.toArray(), actual);
    }
}
