import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;


import org.junit.Test;

import menu.Category;
import menu.Diet;
import menu.Dish;

/**
 * Grundlegende Tests für die Operationen einer Kategorie
 *
 * @author aan, avh, mhe, mre, tti
 */
public class TestCategory {


    /**
     * Test für den Konstruktor {@link Category#Category(String)}.
     * Nutzt dafür {@link Category#getName()}.
     */
    @Test
    public final void test_Constructor() {
        final String name = "Vorspeisen";

        final Category category = new Category(name);

        assertNotNull("category is null", category);
        assertEquals("category name is wrong", name, category.getName());
    }


    /**
     * Test für {@link Category#getDish(int)}.
     * Nutzt dafür {@link Category#addDish(Dish)}.
     */
    @Test
    public final void test_getDish() {
        final String name = "Vorspeisen";
        final int expectedDishId = 1;

        final Category category = new Category(name);
        assertNull("dish with id 1 exists bevor add", category.getDish(expectedDishId));

        final Dish dish = new Dish("Salat", 699, Diet.VEGETARIAN);
        final int dishId = category.addDish(dish);
        assertEquals("dishId is not 1", expectedDishId, dishId);

        assertEquals("returned dish is not equal to added dish", dish, category.getDish(dishId));

        assertNull("more then one dish added", category.getDish(expectedDishId + 1));
    }


    /**
     * Test für {@link Category#getDishes()}.
     * Nutzt dafür {@link Category#addDish(Dish)}.
     */
    @Test
    public final void test_getDishes() {
        final String name = "Vorspeisen";

        final Category category = new Category(name);

        final Dish[] dishes = {
                new Dish("Salat", 699, Diet.VEGETARIAN),
                new Dish("Antipasti", 850, Diet.VEGETARIAN),
                new Dish("Tomatensuppe", 550, Diet.VEGAN),
        };

        assertTrue("must be empty after creation", category.getDishes().isEmpty());


        category.addDish(dishes[0]);
        category.addDish(dishes[1]);
        category.addDish(dishes[2]);

        assertArrayEquals("does not contain all dishes in right order", dishes, category.getDishes().toArray());
    }


    /**
     * Test für {@link Category#addDish(Dish)}.
     * Einmal ein vorhandenes und ein nicht vorhandenes Gericht
     */
    @Test
    public final void test_addDish() {
        final String name = "Vorspeisen";

        final Category category = new Category(name);

        final Dish dish1 = new Dish("Salat", 699, Diet.VEGETARIAN);
        final int dishId1 = category.addDish(dish1);

        final Dish dish2 = new Dish("Antipasti", 850, Diet.VEGETARIAN);
        final int dishId2 = category.addDish(dish2);

        final Dish dish3 = new Dish("Tomatensuppe", 550, Diet.VEGAN);
        final int dishId3 = category.addDish(dish3);

        final int dishId4 = category.addDish(dish1);

        assertEquals("dishId must start with 1", 1, dishId1);
        assertEquals("dishId must increment (0)", 2, dishId2);
        assertEquals("dishId must increment (1)", 3, dishId3);
        assertEquals("dish must be able to be added twice", 4, dishId4);
    }


    /**
     * Test für {@link Category#toString()}.
     */
    @Test
    public final void test_toString(){
        final String nameExample = "Kategorie1";

        final Category categoryExample = new Category(nameExample);

        final Dish[] dishesExample = {
                new Dish("Gericht1", 999, Diet.OMNIVORE),
                new Dish("Gericht2", 999, Diet.VEGAN),
                new Dish("Gericht3", 99, Diet.PESCETARIAN),
        };

        for (Dish dish : dishesExample) {
            categoryExample.addDish(dish);
        }

        final String expectedExample = """
        Kategorie1
           1. Gericht1 9,99€
           2. Gericht2 9,99€
           3. Gericht3 0,99€
        """;

        assertEquals("example from assignment", expectedExample, categoryExample.toString());


        final String nameEmpty = "leere Kategorie";

        final Category categoryEmpty = new Category(nameEmpty);

        assertEquals("empty category", nameEmpty + "\n", categoryEmpty.toString());
    }
}
