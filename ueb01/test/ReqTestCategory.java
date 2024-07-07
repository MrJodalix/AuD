import org.junit.Test;

import menu.Category;
import menu.Diet;
import menu.Dish;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class ReqTestCategory {

    /**
     * Test für {@link Category#removeDish(int)}.
     * Nutzt dafür {@link Category#addDish(Dish)}.
     */
    @Test
    public final void test_removeDishTwoCopiesAtFirst() {
        final String name = "Vorspeisen";
        final String name2 = "Nachspeisen";

        final Category category = new Category(name);
        final Category category2 = new Category(name2);

        final Dish[] twoCopies = {
                new Dish("Erbsensuppe", 550, Diet.VEGAN),
                new Dish("Erbsensuppe", 550, Diet.VEGAN),
                new Dish("FleischSalat", 699, Diet.OMNIVORE),
                new Dish("Pute", 850, Diet.OMNIVORE),
                new Dish("Gulaschsuppe", 550, Diet.OMNIVORE),
                new Dish("Gans", 550, Diet.OMNIVORE),
        };

        final Dish[] deleteFirst = {
                new Dish("Erbsensuppe", 550, Diet.VEGAN),
                new Dish("FleischSalat", 699, Diet.OMNIVORE),
                new Dish("Pute", 850, Diet.OMNIVORE),
                new Dish("Gulaschsuppe", 550, Diet.OMNIVORE),
                new Dish("Gans", 550, Diet.OMNIVORE),
        };

        final Dish[] deleteSecond = {
                new Dish("Erbsensuppe", 550, Diet.VEGAN),
                new Dish("FleischSalat", 699, Diet.OMNIVORE),
                new Dish("Pute", 850, Diet.OMNIVORE),
                new Dish("Gulaschsuppe", 550, Diet.OMNIVORE),
                new Dish("Gans", 550, Diet.OMNIVORE),
        };

        assertTrue("must be empty after creation", category.getDishes().isEmpty());

        category.addDish(twoCopies[0]);
        category.addDish(twoCopies[1]);
        category.addDish(twoCopies[2]);
        category.addDish(twoCopies[3]);
        category.addDish(twoCopies[4]);
        category.addDish(twoCopies[5]);

        category2.addDish(twoCopies[0]);
        category2.addDish(twoCopies[1]);
        category2.addDish(twoCopies[2]);
        category2.addDish(twoCopies[3]);
        category2.addDish(twoCopies[4]);
        category2.addDish(twoCopies[5]);

        category.removeDish(1);

        assertNotSame(category.getFirstDishOfDiet(Diet.VEGAN), category2.getFirstDishOfDiet(Diet.VEGAN));

        assertArrayEquals("erfolgreich ein Gericht gelöscht", deleteFirst, category.getDishes().toArray());

        category.removeDish(1);
        category.removeDish(1);
        category.removeDish(1);
        category.removeDish(1);
        category.removeDish(1);

        category.addDish(twoCopies[0]);
        category.addDish(twoCopies[1]);
        category.addDish(twoCopies[2]);
        category.addDish(twoCopies[3]);
        category.addDish(twoCopies[4]);
        category.addDish(twoCopies[5]);

        category.removeDish(2);

        assertEquals(category.getFirstDishOfDiet(Diet.VEGAN), category2.getFirstDishOfDiet(Diet.VEGAN));

        assertArrayEquals("erfolgreich ein Gericht gelöscht", deleteSecond, category.getDishes().toArray());
    }

    /**
     * Test für {@link Category#removeDish(int)}.
     * Nutzt dafür {@link Category#addDish(Dish)}.
     */
    @Test
    public final void test_removeDishTwoCopiesAtEnd() {
        final String name = "Vorspeisen";

        final Category category = new Category(name);
        final Category category2 = new Category(name);

        final Dish[] twoCopies = {
                new Dish("FleischSalat", 699, Diet.OMNIVORE),
                new Dish("Erbsensuppe", 550, Diet.VEGAN),
                new Dish("Pute", 850, Diet.PESCETARIAN),
                new Dish("Gulaschsuppe", 550, Diet.VEGETARIAN),
                new Dish("Erbsensuppe", 550, Diet.VEGAN),
                new Dish("Erbsensuppe", 550, Diet.VEGAN),
                new Dish("Gans", 550, Diet.OMNIVORE),
        };

        assertTrue("must be empty after creation", category.getDishes().isEmpty());

        category.addDish(twoCopies[0]);
        category.addDish(twoCopies[1]);
        category.addDish(twoCopies[2]);
        category.addDish(twoCopies[3]);
        category.addDish(twoCopies[4]);
        category.addDish(twoCopies[5]);
        category.addDish(twoCopies[6]);

        category2.addDish(twoCopies[0]);
        category2.addDish(twoCopies[1]);
        category2.addDish(twoCopies[2]);
        category2.addDish(twoCopies[3]);
        category2.addDish(twoCopies[4]);
        category2.addDish(twoCopies[5]);
        category2.addDish(twoCopies[6]);

        category.removeDish(6);

        assertEquals("erfolgreich ein Gericht gelöscht", category2.getFirstDishOfDiet(Diet.VEGAN), category.getFirstDishOfDiet(Diet.VEGAN));

        category.removeDish(1);
        category.removeDish(1);
        category.removeDish(1);
        category.removeDish(1);
        category.removeDish(1);
        category.removeDish(1);

        category.addDish(twoCopies[0]);
        category.addDish(twoCopies[1]);
        category.addDish(twoCopies[2]);
        category.addDish(twoCopies[3]);
        category.addDish(twoCopies[4]);
        category.addDish(twoCopies[5]);
        category.addDish(twoCopies[6]);

        category.removeDish(5);

        assertEquals("erfolgreich ein Gericht gelöscht", category2.getFirstDishOfDiet(Diet.VEGAN), category.getFirstDishOfDiet(Diet.VEGAN));

        category.removeDish(1);
        category.removeDish(1);
        category.removeDish(1);
        category.removeDish(1);
        category.removeDish(1);
        category.removeDish(1);

        category.addDish(twoCopies[0]);
        category.addDish(twoCopies[1]);
        category.addDish(twoCopies[2]);
        category.addDish(twoCopies[3]);
        category.addDish(twoCopies[4]);
        category.addDish(twoCopies[5]);
        category.addDish(twoCopies[6]);

        category.removeDish(2);

        assertNotSame("erfolgreich ein Gericht gelöscht", category2.getFirstDishOfDiet(Diet.VEGAN), category.getFirstDishOfDiet(Diet.VEGAN));
    }
    /**
     * Test für {@link Category#removeDish(int)}.
     * Nutzt dafür {@link Category#addDish(Dish)}.
     */
    @Test
    public final void test_removeDishTwoCopiesMiddle() {
        final String name = "Vorspeisen";
        final String name2 = "Mittagstisch";

        final Category category = new Category(name);
        final Category category2 = new Category(name2);

        final Dish[] twoCopies = {
                new Dish("Pute", 850, Diet.OMNIVORE),
                new Dish("Pute", 850, Diet.OMNIVORE),
                new Dish("FleischSalat", 699, Diet.OMNIVORE),
                new Dish("Gulaschsuppe", 550, Diet.OMNIVORE),
                new Dish("Pute", 850, Diet.OMNIVORE),
                new Dish("Gans", 550, Diet.OMNIVORE),
        };

        final Dish[] deleteFirst = {
                new Dish("Pute", 850, Diet.OMNIVORE),
                new Dish("FleischSalat", 699, Diet.OMNIVORE),
                new Dish("Gulaschsuppe", 550, Diet.OMNIVORE),
                new Dish("Pute", 850, Diet.OMNIVORE),
                new Dish("Gans", 550, Diet.OMNIVORE),
        };

        final Dish[] deleteSecond = {
                new Dish("Pute", 850, Diet.OMNIVORE),
                new Dish("FleischSalat", 699, Diet.OMNIVORE),
                new Dish("Gulaschsuppe", 550, Diet.OMNIVORE),
                new Dish("Pute", 850, Diet.OMNIVORE),
                new Dish("Gans", 550, Diet.OMNIVORE),
        };

        final Dish[] deleteThird = {
                new Dish("Pute", 850, Diet.OMNIVORE),
                new Dish("Pute", 850, Diet.OMNIVORE),
                new Dish("FleischSalat", 699, Diet.OMNIVORE),
                new Dish("Gulaschsuppe", 550, Diet.OMNIVORE),
                new Dish("Gans", 550, Diet.OMNIVORE),
        };

        assertTrue("must be empty after creation", category.getDishes().isEmpty());

        category.addDish(twoCopies[0]);
        category.addDish(twoCopies[1]);
        category.addDish(twoCopies[2]);
        category.addDish(twoCopies[3]);
        category.addDish(twoCopies[4]);
        category.addDish(twoCopies[5]);

        category2.addDish(twoCopies[1]);
        category2.addDish(twoCopies[2]);
        category2.addDish(twoCopies[3]);
        category2.addDish(twoCopies[4]);
        category2.addDish(twoCopies[5]);

        category.removeDish(1);

        assertEquals("erfolgreich kein Gericht gelöscht", category2.getFirstDishOfDiet(Diet.OMNIVORE), category.getFirstDishOfDiet(Diet.OMNIVORE));

        category.removeDish(1);
        category.removeDish(1);
        category.removeDish(1);
        category.removeDish(1);
        category.removeDish(1);

        category.addDish(twoCopies[0]);
        category.addDish(twoCopies[1]);
        category.addDish(twoCopies[2]);
        category.addDish(twoCopies[3]);
        category.addDish(twoCopies[4]);
        category.addDish(twoCopies[5]);

        category.removeDish(2);

        assertArrayEquals("erfolgreich kein Gericht gelöscht", deleteSecond, category.getDishes().toArray());

        category.removeDish(1);
        category.removeDish(1);
        category.removeDish(1);
        category.removeDish(1);
        category.removeDish(1);

        category.addDish(twoCopies[0]);
        category.addDish(twoCopies[1]);
        category.addDish(twoCopies[2]);
        category.addDish(twoCopies[3]);
        category.addDish(twoCopies[4]);
        category.addDish(twoCopies[5]);

        category.removeDish(5);

        assertArrayEquals("erfolgreich kein Gericht gelöscht", deleteThird, category.getDishes().toArray());
    }


    /**
     * Test für {@link Category#getFirstDishOfDiet(Diet)}.
     * Einmal ein vorhandenes und ein nicht vorhandenes Gericht
     */
    @Test
    public final void test_getFirstDishOfDiet() {
        final String name = "Vorspeisen";
        final String name2 = "Mittagsspeisen";
        final String name3 = "Abendessen";

        final Category category = new Category(name);
        final Category newCategory = new Category(name2);
        final Category nullCategory = new Category(name3);

        final Dish[] threeDishes = {
                new Dish("Tomatensuppe", 550, Diet.VEGAN),
                new Dish("Zwiebelsuppe", 550, Diet.VEGAN),
                new Dish("Erbsensuppe", 550, Diet.VEGAN),
                new Dish("Salat", 699, Diet.VEGETARIAN),
                new Dish("Antipasti", 850, Diet.VEGETARIAN),
        };

        final Dish[] oneDish = {
                new Dish("Salat", 699, Diet.VEGETARIAN),
        };

        category.addDish(threeDishes[0]);
        category.addDish(threeDishes[1]);
        category.addDish(threeDishes[2]);
        category.addDish(threeDishes[3]);
        category.addDish(threeDishes[4]);

        List<Dish> copyList = new ArrayList<>(category.getDishes());
        assertArrayEquals("does not contain all dishes in right order", threeDishes, category.getDishes().toArray());

        newCategory.addDish(category.getFirstDishOfDiet(Diet.VEGETARIAN));

        assertArrayEquals("does contain only Tomatensuppe", oneDish, newCategory.getDishes().toArray());

        category.getFirstDishOfDiet(Diet.PESCETARIAN);

        assertNull("dish with id 1 exists bevor add", nullCategory.getFirstDishOfDiet(Diet.PESCETARIAN));


    }


    /**
     * Test für {@link Category#getDishesWithAtLeastDiet(Diet)}.
     * Nur Vegan
     */
    @Test
    public final void test_getDishesWithAtLeastDiet1(){
        final String name = "Vorspeisen";
        final String name2 = "Mittagsspeisen";

        final Category category = new Category(name);
        final Category newCategory = new Category(name2);

        final Dish[] threeDishes = {
                new Dish("Salat", 699, Diet.VEGETARIAN),
                new Dish("Tomatensuppe", 550, Diet.VEGAN),
                new Dish("Antipasti", 850, Diet.VEGETARIAN),
                new Dish("Zwiebelsuppe", 550, Diet.VEGAN),
                new Dish("Erbsensuppe", 550, Diet.VEGAN),
        };

        final Dish[] oneDish = {
                new Dish("Tomatensuppe", 550, Diet.VEGAN),
                new Dish("Zwiebelsuppe", 550, Diet.VEGAN),
                new Dish("Erbsensuppe", 550, Diet.VEGAN),
        };

        category.addDish(threeDishes[0]);
        category.addDish(threeDishes[1]);
        category.addDish(threeDishes[2]);
        category.addDish(threeDishes[3]);
        category.addDish(threeDishes[4]);

        assertArrayEquals("does not contain all dishes in right order", threeDishes, category.getDishes().toArray());

        newCategory.addDish(category.getDishesWithAtLeastDiet(Diet.VEGAN).get(0));
        newCategory.addDish(category.getDishesWithAtLeastDiet(Diet.VEGAN).get(1));
        newCategory.addDish(category.getDishesWithAtLeastDiet(Diet.VEGAN).get(2));

        assertArrayEquals("does contain all Vegan", oneDish, newCategory.getDishes().toArray());
    }


    /**
     * Test für {@link Category#getDishesWithAtLeastDiet(Diet)}.
     * Ab Vegetarisch
     */
    @Test
    public final void test_getDishesWithAtLeastDiet2(){
        final String name = "Vorspeisen";
        final String name2 = "Mittagsspeisen";

        final Category category = new Category(name);
        final Category newCategory = new Category(name2);

        final Dish[] threeDishes = {
                new Dish("Salat", 699, Diet.VEGETARIAN),
                new Dish("Tomatensuppe", 550, Diet.VEGAN),
                new Dish("Rumpsteack", 2299, Diet.OMNIVORE),
                new Dish("Antipasti", 850, Diet.VEGETARIAN),
                new Dish("Zwiebelsuppe", 550, Diet.VEGAN),
                new Dish("Lachsfilet", 1650, Diet.PESCETARIAN),
                new Dish("Erbsensuppe", 550, Diet.VEGAN),
        };

        final Dish[] oneDish = {
                new Dish("Tomatensuppe", 550, Diet.VEGAN),
                new Dish("Zwiebelsuppe", 550, Diet.VEGAN),
                new Dish("Erbsensuppe", 550, Diet.VEGAN),
                new Dish("Salat", 699, Diet.VEGETARIAN),
                new Dish("Antipasti", 850, Diet.VEGETARIAN),
        };

        category.addDish(threeDishes[0]);
        category.addDish(threeDishes[1]);
        category.addDish(threeDishes[2]);
        category.addDish(threeDishes[3]);
        category.addDish(threeDishes[4]);
        category.addDish(threeDishes[5]);
        category.addDish(threeDishes[6]);

        assertArrayEquals("does not contain all dishes in right order", threeDishes, category.getDishes().toArray());

        newCategory.addDish(category.getDishesWithAtLeastDiet(Diet.VEGETARIAN).get(0));
        newCategory.addDish(category.getDishesWithAtLeastDiet(Diet.VEGETARIAN).get(1));
        newCategory.addDish(category.getDishesWithAtLeastDiet(Diet.VEGETARIAN).get(2));
        newCategory.addDish(category.getDishesWithAtLeastDiet(Diet.VEGETARIAN).get(3));
        newCategory.addDish(category.getDishesWithAtLeastDiet(Diet.VEGETARIAN).get(4));

        assertArrayEquals("does contain all Vegetarie", oneDish, newCategory.getDishes().toArray());
    }


    /**
     * Test für {@link Category#getDishesWithAtLeastDiet(Diet)}.
     * Ab Pescetarisch
     */
    @Test
    public final void test_getDishesWithAtLeastDiet3(){
        final String name = "Vorspeisen";
        final String name2 = "Mittagsspeisen";

        final Category category = new Category(name);
        final Category newCategory = new Category(name2);

        final Dish[] threeDishes = {
                new Dish("Salat", 699, Diet.VEGETARIAN),
                new Dish("Tomatensuppe", 550, Diet.VEGAN),
                new Dish("Rumpsteack", 2299, Diet.OMNIVORE),
                new Dish("Antipasti", 850, Diet.VEGETARIAN),
                new Dish("Zwiebelsuppe", 550, Diet.VEGAN),
                new Dish("Lachsfilet", 1650, Diet.PESCETARIAN),
                new Dish("Erbsensuppe", 550, Diet.VEGAN),
        };

        final Dish[] oneDish = {
                new Dish("Tomatensuppe", 550, Diet.VEGAN),
                new Dish("Zwiebelsuppe", 550, Diet.VEGAN),
                new Dish("Erbsensuppe", 550, Diet.VEGAN),
                new Dish("Salat", 699, Diet.VEGETARIAN),
                new Dish("Antipasti", 850, Diet.VEGETARIAN),
                new Dish("Lachsfilet", 1650, Diet.PESCETARIAN),
        };

        category.addDish(threeDishes[0]);
        category.addDish(threeDishes[1]);
        category.addDish(threeDishes[2]);
        category.addDish(threeDishes[3]);
        category.addDish(threeDishes[4]);
        category.addDish(threeDishes[5]);
        category.addDish(threeDishes[6]);

        assertArrayEquals("does not contain all dishes in right order", threeDishes, category.getDishes().toArray());

        newCategory.addDish(category.getDishesWithAtLeastDiet(Diet.PESCETARIAN).get(0));
        newCategory.addDish(category.getDishesWithAtLeastDiet(Diet.PESCETARIAN).get(1));
        newCategory.addDish(category.getDishesWithAtLeastDiet(Diet.PESCETARIAN).get(2));
        newCategory.addDish(category.getDishesWithAtLeastDiet(Diet.PESCETARIAN).get(3));
        newCategory.addDish(category.getDishesWithAtLeastDiet(Diet.PESCETARIAN).get(4));
        newCategory.addDish(category.getDishesWithAtLeastDiet(Diet.PESCETARIAN).get(5));

        assertArrayEquals("does contain all Vegan", oneDish, newCategory.getDishes().toArray());
    }

    /**
     * Test für {@link Category#getDishesWithAtLeastDiet(Diet)}.
     * Ab Omnivorisch
     */
    @Test
    public final void test_getDishesWithAtLeastDiet4(){
        final String name = "Vorspeisen";
        final String name2 = "Mittagsspeisen";

        final Category category = new Category(name);
        final Category newCategory = new Category(name2);

        final Dish[] threeDishes = {
                new Dish("Salat", 699, Diet.VEGETARIAN),
                new Dish("Tomatensuppe", 550, Diet.VEGAN),
                new Dish("Rumpsteack", 2299, Diet.OMNIVORE),
                new Dish("Antipasti", 850, Diet.VEGETARIAN),
                new Dish("Zwiebelsuppe", 550, Diet.VEGAN),
                new Dish("Lachsfilet", 1650, Diet.PESCETARIAN),
                new Dish("Erbsensuppe", 550, Diet.VEGAN),
        };

        final Dish[] oneDish = {
                new Dish("Tomatensuppe", 550, Diet.VEGAN),
                new Dish("Zwiebelsuppe", 550, Diet.VEGAN),
                new Dish("Erbsensuppe", 550, Diet.VEGAN),
                new Dish("Salat", 699, Diet.VEGETARIAN),
                new Dish("Antipasti", 850, Diet.VEGETARIAN),
                new Dish("Lachsfilet", 1650, Diet.PESCETARIAN),
                new Dish("Rumpsteack", 2299, Diet.OMNIVORE),
        };

        category.addDish(threeDishes[0]);
        category.addDish(threeDishes[1]);
        category.addDish(threeDishes[2]);
        category.addDish(threeDishes[3]);
        category.addDish(threeDishes[4]);
        category.addDish(threeDishes[5]);
        category.addDish(threeDishes[6]);

        assertArrayEquals("does not contain all dishes in right order", threeDishes, category.getDishes().toArray());

        newCategory.addDish(category.getDishesWithAtLeastDiet(Diet.OMNIVORE).get(0));
        newCategory.addDish(category.getDishesWithAtLeastDiet(Diet.OMNIVORE).get(1));
        newCategory.addDish(category.getDishesWithAtLeastDiet(Diet.OMNIVORE).get(2));
        newCategory.addDish(category.getDishesWithAtLeastDiet(Diet.OMNIVORE).get(3));
        newCategory.addDish(category.getDishesWithAtLeastDiet(Diet.OMNIVORE).get(4));
        newCategory.addDish(category.getDishesWithAtLeastDiet(Diet.OMNIVORE).get(5));
        newCategory.addDish(category.getDishesWithAtLeastDiet(Diet.OMNIVORE).get(6));

        assertArrayEquals("does contain all Vegan", oneDish, newCategory.getDishes().toArray());
    }

    /**
     * Test für {@link Category#getDishesWithPriceBetween(Integer, Integer)}.
     * Ab Omnivorisch
     */
    @Test
    public final void test_getDishesWithPriceBetween(){
        final String name = "Vorspeisen";
        final String name2 = "Mittagsspeisen";
        final String name3 = "Abendessen";
        final String name4 = "Mitternachtsmahl";

        final Category category = new Category(name);
        final Category newCategory = new Category(name2);
        final Category afternoonCategory = new Category(name3);
        final Category midnightCategory = new Category(name4);


        final Dish[] threeDishes = {
                new Dish("Salat", 699, Diet.VEGETARIAN),
                new Dish("Tomatensuppe", 550, Diet.VEGAN),
                new Dish("Rumpsteack", 2299, Diet.OMNIVORE),
                new Dish("Antipasti", 850, Diet.VEGETARIAN),
                new Dish("Zwiebelsuppe", 580, Diet.VEGAN),
                new Dish("Lachsfilet", 1650, Diet.PESCETARIAN),
                new Dish("Erbsensuppe", 450, Diet.VEGAN),
        };

        final Dish[] oneDish = {
                new Dish("Salat", 699, Diet.VEGETARIAN),
                new Dish("Tomatensuppe", 550, Diet.VEGAN),
                new Dish("Antipasti", 850, Diet.VEGETARIAN),
                new Dish("Zwiebelsuppe", 580, Diet.VEGAN),
        };

        final Dish[] afternoon = {
                new Dish("Salat", 699, Diet.VEGETARIAN),
                new Dish("Tomatensuppe", 550, Diet.VEGAN),
                new Dish("Antipasti", 850, Diet.VEGETARIAN),
                new Dish("Zwiebelsuppe", 580, Diet.VEGAN),
                new Dish("Erbsensuppe", 450, Diet.VEGAN),
        };

        final Dish[] midnight = {
                new Dish("Salat", 699, Diet.VEGETARIAN),
                new Dish("Rumpsteack", 2299, Diet.OMNIVORE),
                new Dish("Antipasti", 850, Diet.VEGETARIAN),
                new Dish("Zwiebelsuppe", 580, Diet.VEGAN),
                new Dish("Lachsfilet", 1650, Diet.PESCETARIAN),
        };

        category.addDish(threeDishes[0]);
        category.addDish(threeDishes[1]);
        category.addDish(threeDishes[2]);
        category.addDish(threeDishes[3]);
        category.addDish(threeDishes[4]);
        category.addDish(threeDishes[5]);
        category.addDish(threeDishes[6]);

        assertArrayEquals("does not contain all dishes in right order", threeDishes, category.getDishes().toArray());

        newCategory.addDish(category.getDishesWithPriceBetween(550,1650).get(0));
        newCategory.addDish(category.getDishesWithPriceBetween(550,1650).get(1));
        newCategory.addDish(category.getDishesWithPriceBetween(550,1650).get(2));
        newCategory.addDish(category.getDishesWithPriceBetween(550,1650).get(3));

        assertArrayEquals("does contain all Vegan", oneDish, newCategory.getDishes().toArray());

        afternoonCategory.addDish(category.getDishesWithPriceBetween(null,1650).get(0));
        afternoonCategory.addDish(category.getDishesWithPriceBetween(null,1650).get(1));
        afternoonCategory.addDish(category.getDishesWithPriceBetween(null,1650).get(2));
        afternoonCategory.addDish(category.getDishesWithPriceBetween(null,1650).get(3));
        afternoonCategory.addDish(category.getDishesWithPriceBetween(null,1650).get(4));

        assertArrayEquals("does contain all Vegan", oneDish, newCategory.getDishes().toArray());

        midnightCategory.addDish(category.getDishesWithPriceBetween(580,null).get(0));
        midnightCategory.addDish(category.getDishesWithPriceBetween(580,null).get(1));
        midnightCategory.addDish(category.getDishesWithPriceBetween(580,null).get(2));
        midnightCategory.addDish(category.getDishesWithPriceBetween(580,null).get(3));
        midnightCategory.addDish(category.getDishesWithPriceBetween(580,null).get(4));

        assertArrayEquals("does contain all Vegan", oneDish, newCategory.getDishes().toArray());
    }

    /**
     * Test für addDiet(Dish).
     * Ab Omnivorisch
     */
    @Test
    public final void test_addDiet(){
        final String name = "Vorspeisen";
        final String name2 = "Mittagsspeisen";

        final Category category = new Category(name);
        final Category newCategory = new Category(name2);

        final Dish[] threeDishes = {
                new Dish("Salat", 699, Diet.VEGETARIAN),
                new Dish("Tomatensuppe", 550, Diet.VEGAN),
                new Dish("Rumpsteack", 2299, Diet.OMNIVORE),
                new Dish("Antipasti", 850, Diet.VEGETARIAN),
                new Dish("Zwiebelsuppe", 580, Diet.VEGAN),
                new Dish("Lachsfilet", 1650, Diet.PESCETARIAN),
                new Dish("Erbsensuppe", 450, Diet.VEGAN),
        };

        final Dish[] oneDish = {
                new Dish("Salat", 699, Diet.VEGETARIAN),
                new Dish("Tomatensuppe", 550, Diet.VEGAN),
                new Dish("Antipasti", 850, Diet.VEGETARIAN),
                new Dish("Zwiebelsuppe", 580, Diet.VEGAN),
        };

        category.addDish(threeDishes[0]);
        category.addDish(threeDishes[1]);
        category.addDish(threeDishes[2]);
        category.addDish(threeDishes[3]);
        category.addDish(threeDishes[4]);
        category.addDish(threeDishes[5]);
        category.addDish(threeDishes[6]);

        assertArrayEquals("does not contain all dishes in right order", threeDishes, category.getDishes().toArray());

        newCategory.addDish(category.getDishesWithPriceBetween(550,1650).get(0));
        newCategory.addDish(category.getDishesWithPriceBetween(550,1650).get(1));
        newCategory.addDish(category.getDishesWithPriceBetween(550,1650).get(2));
        newCategory.addDish(category.getDishesWithPriceBetween(550,1650).get(3));

        assertArrayEquals("does contain all Vegan", oneDish, newCategory.getDishes().toArray());
    }
}
