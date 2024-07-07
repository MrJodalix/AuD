package menu;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Diese Klasse stellt eine Kategorie einer Speisekarte dar.
 * Eine Kategorie besitzt einen Namen und kann eine Vielzahl von Gerichten beinhalten.
 *
 * @author aan, avh, mhe, mre, tti, Joshua-Scott Schöttke, Ilana Schmara (Gruppe 21)
 */
public class Category {

    //Liste für jeden Ernährungstyp, die alle zulässigen Gerichte abspeichert
    private final List<Dish> arrayDishList = new ArrayList<>();

    //Map, die seperate Listen für jede Ernährungsform enthält
    private final Map<Diet, List<Dish>> sepListMap = new LinkedHashMap<>();

    //Name der Category
    private final String name;

    
    /**
     * Erzeugt eine Kategorie mit dem übergebenen Namen.
     *
     * @param name Name der Kategorie
     *
     * @pre name != null
     */
    public Category(String name) {
        assert (name != null);

        this.name = name;
    }

    /**
     * Liefert den Namen der Kategorie.
     *
     * @return Name der Kategorie
     */
    public String getName() {
        return name;
    }

    /**
     * Liefert das Gericht mit der angegebenen ID zurück.
     *
     * Diese Methode arbeitet in O(f(N)), dabei ist N = Anzahl der vorhandenen Gerichte in der
     * Kategorie und f(N) = 1
     *
     * @param dishId ID des Gerichtes, welches zurückgegeben werden soll
     *
     * @pre dishId &gt; 0
     *
     * @return Das Gericht oder null, wenn kein Gericht mit der ID vorhanden ist
     */
    public Dish getDish(int dishId) {
        assert (dishId > 0);

        Dish dish = null;
        
        if (!(dishId > arrayDishList.size())) {
            dish = arrayDishList.get(dishId - 1);
        }
        
        return dish;
    }

    /**
     * Liefert eine Liste an Gerichten.
     * Das Ergebnis ist dabei aufsteigend nach der ID der Gerichte in der Kategorie sortiert.
     *
     * Diese Methode arbeitet in O(f(N)), dabei ist N = Anzahl der vorhandenen Gerichte in der
     * Kategorie und f(N) = 1
     *
     * @return Liste an Gerichten
     */
    public List<Dish> getDishes() {
        return new ArrayList<>(arrayDishList);
    }

    /**
     * Überprüft, ob das Gericht mit der ID in dieser Kategorie enthalten ist.
     *
     * Diese Methode arbeitet in O(f(N)), dabei ist N = Anzahl der vorhandenen Gerichte in der
     * Kategorie und f(N) = 1
     *
     * @param dishId ID des Gerichtes, welches überprüft werden soll
     *
     * @pre dishId &gt; 0
     *
     * @return Angabe, ob das Gericht enthalten ist
     */
    public boolean hasDish(int dishId) {
        assert (dishId > 0);

        return getDish(dishId) != null;
    }

    /**
     * Fügt das übergebene Gericht dieser Kategorie hinzu.
     * Das erste Gericht einer Kategorie erhält dabei die ID "1",
     * wobei die ID aller weiteren Gerichte entsprechend inkrementiert wird.
     * Gerichte einer Kategorie können gleiche Eigenschaften besitzen, sodass die Gerichte bis auf
     * die ID identisch sein können.
     *
     * Diese Methode arbeitet in O(f(N)), dabei ist N = Anzahl der vorhandenen Gerichte in der
     * Kategorie und f(N) = amortisiert 1
     *
     * @param dish Gericht, welches hinzugefügt werden soll
     *
     * @pre dish != null
     *
     * @return ID des Gerichtes, welches hinzugefügt wurde
     */
    public int addDish(Dish dish) {
        assert (dish != null);

        List<Dish> copyDiet;

        //Wenn die Map noch keine Listen für Diätformen enthält
        if (!sepListMap.containsKey(dish.diet())) {
            Diet[] diets = Diet.values();
            for (Diet dietValue : diets) {
                List<Dish> dietList = new ArrayList<>();
                sepListMap.put(dietValue, dietList);
            }
        }

        arrayDishList.add(dish);

        //auch für die Map das Gericht in die passende Liste tun
        copyDiet = sepListMap.get(dish.diet());
        copyDiet.add(dish);
        sepListMap.put(dish.diet(), copyDiet);

        return arrayDishList.size();
    }

    /**
     * Entfernt das Gericht mit der übergebenen ID aus dieser Kategorie.
     * Die ID der nachfolgenden Gerichte wird entsprechend angepasst.
     *
     * Diese Methode arbeitet in O(f(N)), dabei ist N = Anzahl der vorhandenen Gerichte in der
     * Kategorie und f(N) = N
     *
     * @param dishId ID des Gerichtes, welches entfernt werden soll
     *
     * @pre dishId &gt; 0
     *
     * @return Gericht, welches entfernt wurde oder null, wenn das Gericht nicht in der Kategorie
     *         vorhanden ist
     */
    public Dish removeDish(int dishId) {
        assert (dishId > 0);

        List<Dish> copyList;

        Dish removeDish = null;
        Dish dish;

        int countId = - 1;

        if (hasDish(dishId)) {
            removeDish = arrayDishList.get(dishId - 1);
            //Vorkommen von gleicher Ernaehrungsform vor der dishId
            for (int i = 0; i < dishId; i++) {
                dish = arrayDishList.get(i);
                if (dish.diet() == removeDish.diet()) {
                    countId++;
                }
            }
            arrayDishList.remove(dishId - 1);
            copyList = sepListMap.get(removeDish.diet());
            copyList.remove(countId);
            sepListMap.put(removeDish.diet(), copyList);
        }
        return removeDish;
    }

    /**
     * Liefert das erste Gericht dieser Speisekarte zurück, welches eindeutig der übergebenen
     * Ernährungsform zuzuordnen ist.
     *
     * Diese Methode arbeitet in O(f(N)), dabei ist N = Anzahl der vorhandenen Gerichte in der
     * Kategorie und f(N) = 1
     *
     * @param diet Ernährungsform
     *
     * @pre diet != null
     *
     * @return Gericht mit der Ernährungsform oder null, wenn kein passendes Gericht
     *         in dieser Speisekarte vorhanden ist
     */
    public Dish getFirstDishOfDiet(Diet diet) {
        assert (diet != null);

        Dish dish = null;
        
        if (sepListMap.containsKey(diet) && !sepListMap.get(diet).isEmpty()) {
            dish = sepListMap.get(diet).get(0);
        }
        return dish;
    }

    /**
     * Liefert alle Gerichte dieser Speisekarte zurück, welche mindestens der übergebenen
     * Ernährungsform zuzuordnen sind.
     * <br>
     * Das Ergebnis ist aufsteigend gemäß der Einschränkung der Ernährungsform sortiert:
     * Vegan =&gt; Vegetarisch =&gt; Pescetarisch =&gt; Omnivore<br>
     * Bei gleicher Ernährungsform werden sie nach der ID sortiert.
     *
     * Diese Methode arbeitet in O(f(N)), dabei ist N = Anzahl der vorhandenen Gerichte in der
     * Kategorie und f(N) = N
     *
     * @param diet Ernährungsform
     *
     * @pre diet != null
     *
     * @return Liste mit den Gerichten, die mindestens der Ernährungsform zuzuordnen sind
     */
    public List<Dish> getDishesWithAtLeastDiet(Diet diet) {
        assert (diet != null);

        List<Dish> contains = new ArrayList<>();

        boolean add = true;

        if (sepListMap.containsKey(diet)) {
            Diet[] diets = Diet.values();
            for (Diet dietValue : diets) {
                if (add) {
                    contains.addAll(sepListMap.get(dietValue));
                    if (dietValue == diet) {
                        add = false;
                    }
                }
            }
        }
        return contains;
    }

    /**
     * Ermittelt alle Gerichte in der angegebenen Preisspanne.
     * Dabei kann neben validen Centbeträgen auch null angegeben werden, was dann eine
     * endlose Grenze definiert.
     * <br>
     * Das Ergebnis ist aufsteigend nach der ID der Gerichte in der Kategorie sortiert.
     *
     * Diese Methode arbeitet in O(f(N)), dabei ist N = Anzahl der vorhandenen Gerichte in der
     * Kategorie und f(N) = N
     *
     * @param minPrice minimaler Preis der zu filternden Gerichte in Cent (inklusive)
     * @param maxPrice maximaler Preis der zu filternden Gerichte in Cent (exklusive)
     *
     * @return Alle Gerichte zwischen der angegebenen Preisspanne
     */
    public List<Dish> getDishesWithPriceBetween(Integer minPrice, Integer maxPrice) {

        List<Dish> copyList = new ArrayList<>();

        if (minPrice == null && maxPrice == null) {
            copyList = new ArrayList<>(arrayDishList);
        } else {
            
            for (Dish dish : arrayDishList) {
                
                if (minPrice == null) {
                    if (dish.price() < maxPrice) {
                        Dish dishCopy = new Dish(dish.name(), dish.price(), dish.diet());
                        copyList.add(dishCopy);
                    }
                } else if (maxPrice == null) {
                    if (dish.price() >= minPrice) {
                        Dish dishCopy = new Dish(dish.name(), dish.price(), dish.diet());
                        copyList.add(dishCopy);
                    }
                } else {
                    if (dish.price() >= minPrice && dish.price() < maxPrice) {
                        Dish dishCopy = new Dish(dish.name(), dish.price(), dish.diet());
                        copyList.add(dishCopy);
                    }
                }
            }
        }
        return copyList;
    }

    /**
     * Liefert eine Zeichenkette zurück, welche zur Visualisierung dieser Kategorie dient.
     * <br>
     * <ul>
     *     <li>Die Kategorie wird durch ihren Namen angegeben</li>
     *     <li>Anschließend werden darunter alle Gerichte der Kategorie aufgelistet
     *     <ul>
     *         <li>Alle Gerichte sollen um jeweils drei Leerzeichen eingerückt sein und sind
     *         gemäß ihrer ID sortiert</li>
     *         <li>Die Auflistung beginnt für jedes Gericht mit der Angabe der ID gefolgt
     *         von einem Punkt und einem Leerzeichen</li>
     *         <li>Ein Gericht wird gemäß der {@link Dish#toString()} Methode repräsentiert</li>
     *         <li>Sofern eine Kategorie keine Gerichte besitzt, soll eine Leerzeile
     *             geschrieben werden</li>
     *     </ul>
     *     </li>
     * </ul>
     *
     * @return Zeichenkette, welche diese Kategorie darstellt.
     */
    @Override
    public String toString() {
        
        StringBuilder cate = new StringBuilder(name + "\n");
        
        if (!arrayDishList.isEmpty()) {
            
            int i = 1;
            for (Dish dish : arrayDishList) {
                cate.append("   ")
                        .append(i)
                        .append(". ")
                        .append(dish)
                        .append("\n");
                i++;
            }
        }
        return cate.toString();
    }
}
