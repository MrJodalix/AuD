package menu;

import java.util.TreeMap;
import java.util.TreeSet;
import java.util.SortedSet;
import java.util.SortedMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;
import java.util.HashSet;
import java.util.Map;

/**
 * Diese Klasse stellt eine digitale Speisekarte dar.
 * <br>
 * Eine Speisekarte besitzt einen Namen und eine Reihe von Gerichten, welche in Kategorien
 * unterteilt sind.
 * Jede Kategorie ist über eine ID und einen Namen eindeutig identifizierbar.
 * <br>
 * Die IDs der Kategorien in der Speisekarte werden durch einen validen String angegeben.
 * Ein valider String besteht stets aus einem Zeichen aus der Menge [a-zA-Z] gefolgt von einer
 * beliebigen Anzahl von Zeichen aus der Menge [a-zA-Z0-9_].
 *
 * @author aan, avh, mhe, mre, tti, Ilana Schmara, Joshua-Scott Schöttke (Gruppe 21)
 */
public class FullMenu {

    //Die eindeutigen Kategorie-Namen
    private final SortedSet<String> setOfCateName = new TreeSet<>();

    //String -> ID der Category, Value -> die Category
    private final SortedMap<String, Category> mapCategory = new TreeMap<>();
    
    //Name der Speisekarte
    private final String name;

    /**
     * Erzeugt eine leere Speisekarte mit dem übergebenen Namen.
     *
     * @param name Name der Speisekarte
     *
     * @pre name != null
     */
    public FullMenu(String name) {
        assert (name != null);

        this.name = name;
    }

    /**
     * Liefert den Namen dieser Speisekarte zurück.
     *
     * @return Name dieser Speisekarte
     */
    public String getName() {

        return name;
    }

    /**
     * Liefert die Kategorie mit der übergebenen ID zurück.
     *
     * Diese Methode arbeitet in O(f(N)), dabei ist N = Anzahl der vorhandenen Kategorien in der
     * Speisekarte und f(N) = log(N)
     *
     * @param categoryId ID der Kategorie
     *
     * @pre categoryId != null
     * @pre Die ID categoryID muss gültig sein
     *
     * @return Die Kategorie mit der ID oder null, wenn die Kategorie nicht vorhanden ist
     */
    public Category getCategory(String categoryId) {
        assert (categoryId != null);
        assert (isValidIdentifier(categoryId));

        return mapCategory.get(categoryId);
    }

    /**
     * Liefert die vorhandenen Kategorien zurück, die in der Speisekarte stehen.
     * <br>
     * Die Kategorien sind dabei gemäß ihrer IDs (alphabetisch aufsteigend entsprechend
     * {@link String#compareTo(String)}) sortiert.
     *
     * Diese Methode arbeitet in O(f(N)), dabei ist N = Anzahl der vorhandenen Kategorien in der
     * Speisekarte und f(N) = N
     *
     * @return Die vorhandenen Kategorien sortiert nach der ID
     */
    public List<Category> getCategories() {
        //O(N)
        Collection<Category> value = mapCategory.values();

        return new ArrayList<>(value);
    }

    /**
     * Liefert die Namen aller Kategorien dieser Speisekarte zurück.
     * <br>
     * Die Namen sind dabei alphabetisch aufsteigend gemäß {@link String#compareTo(String)}
     * sortiert.
     *
     * Diese Methode arbeitet in O(f(N)), dabei ist N = Anzahl der vorhandenen Kategorien in der
     * Speisekarte und f(N) = 1
     *
     * @return Die alphabetisch sortierten Kategorie-Namen
     */
    public List<String> getCategoryNames() {
        return new ArrayList<>(setOfCateName);
    }

    /**
     * Überprüft, ob die Kategorie mit dem übergebenen Namen in dieser Speisekarte enthalten ist.
     *
     * Diese Methode arbeitet in O(f(N)), dabei ist N = Anzahl der vorhandenen Kategorien in der
     * Speisekarte und f(N) = log(N)
     *
     * @param categoryName Name der Kategorie, welche überprüft werden soll
     *
     * @pre categoryName != null
     *
     * @return Angabe, ob die Kategorie enthalten ist
     */
    public boolean hasCategoryWithName(String categoryName) {
        assert (categoryName != null);
        return setOfCateName.contains(categoryName);
    }

    /**
     * Überprüft, ob die Kategorie mit der übergebenen ID in dieser Speisekarte enthalten ist.
     *
     * Diese Methode arbeitet in O(f(N)), dabei ist N = Anzahl der vorhandenen Kategorien in der
     * Speisekarte und f(N) = log(N)
     *
     * @param categoryId ID der Kategorie, welche überprüft werden soll
     *
     * @pre categoryId != null
     * @pre Die ID categoryId muss gültig sein
     *
     * @return Angabe, ob die Kategorie enthalten ist
     */
    public boolean hasCategoryWithId(String categoryId) {
        assert (categoryId != null);
        assert (isValidIdentifier(categoryId));

        return mapCategory.containsKey(categoryId);
    }

    /**
     * Überprüft, ob alle übergebenen Kategorien mit den Namen in dieser Speisekarte
     * enthalten sind.
     *
     * Diese Methode arbeitet in O(f(N)), dabei ist N = Anzahl der übergebenen Kategorien und
     * f(N) = N
     *
     * @param categoryNames Namen der Kategorien, welche überprüft werden sollen
     *
     * @pre categoryNames != null
     *
     * @return Angabe, ob die Kategorien enthalten sind
     */
    public boolean hasCategoriesWithNames(Collection<String> categoryNames) {
        assert (categoryNames != null);

        Set<String> copyCategoryNames = new HashSet<>(categoryNames);
        copyCategoryNames.removeAll(setOfCateName);

        return copyCategoryNames.isEmpty();
    }

    /**
     * Überprüft, ob alle übergebenen Kategorien mit den IDs in dieser Speisekarte enthalten sind.
     *
     * Diese Methode arbeitet in O(f(N)), dabei ist N = Anzahl der übergebenen Kategorien und
     * f(N) = N
     *
     * @param categoryIds IDs der Kategorien, welche überprüft werden sollen
     *
     * @pre categoryIds != null
     * @pre Alle IDs in categoryIds müssen gültige IDs sein
     *
     * @return Angabe, ob die Kategorien enthalten sind
     */
    public boolean hasCategoriesWithIds(Collection<String> categoryIds) {
        assert (categoryIds != null);
        assert (areValidIdentifiers(categoryIds));

        Set<String> copyCategoryIds = new HashSet<>(categoryIds);
        copyCategoryIds.removeAll(mapCategory.keySet());

        return copyCategoryIds.isEmpty();
    }

    /**
     * Überprüft, ob das Gericht mit der übergebenen ID in der Kategorie mit der übergebenen ID
     * vorhanden ist.
     *
     * Diese Methode arbeitet in O(f(N)), dabei ist N = Anzahl der vorhandenen Kategorien in der
     * Speisekarte und f(N) = log(N)
     *
     * @param categoryId ID der Kategorie, mit dem Gericht
     * @param dishId ID des Gerichtes, welches überprüft werden soll
     *
     * @pre categoryId != null
     *
     * @return Angabe, ob das Gericht vorhanden ist
     */
    public boolean hasDish(String categoryId, int dishId) {
        assert (categoryId != null);

        boolean has = false;

        Category copy = mapCategory.get(categoryId);

        if (copy != null) {
            has = copy.hasDish(dishId);
        }

        return has;
    }

    /**
     * Fügt dieser Speisekarte eine neue Kategorie hinzu, sodass die Kategorien stets nach
     * der ID sortiert sind.
     * <br>
     * Sofern eine Kategorie mit dem übergebenen Namen oder der übergebenen ID bereits
     * vorhanden ist, passiert nichts.
     *
     * Diese Methode arbeitet in O(f(N)), dabei ist N = Anzahl der vorhandenen Kategorien in der
     * Speisekarte und f(N) = log(N)
     *
     * @param categoryId ID der Kategorie, die hinzugefügt werden soll
     * @param categoryName Name der Kategorie, die hinzugefügt werden soll
     *
     * @pre categoryId != null
     * @pre categoryName != null
     * @pre Die ID categoryId muss gültig sein
     *
     * @return Angabe, ob eine Kategorie eingefügt werden konnte
     */
    public boolean addCategory(String categoryId, String categoryName) {
        assert (categoryId != null);
        assert (categoryName != null);
        assert (isValidIdentifier(categoryId));

        Category copyCategory = new Category(categoryName);

        if (!setOfCateName.contains(categoryName) && !mapCategory.containsKey(categoryId)) {
            this.setOfCateName.add(categoryName);
            this.mapCategory.put(categoryId, copyCategory);
        }

        return this.mapCategory.containsKey(categoryId)
                && this.setOfCateName.contains(categoryName);
    }

    /**
     * Entfernt die Kategorie mit der übergebenen ID aus dieser Speisekarte und liefert diese
     * zurück.
     *
     * Diese Methode arbeitet in O(f(N)), dabei ist N = Anzahl der vorhandenen Kategorien in der
     * Speisekarte und f(N) = log(N)
     *
     * @param categoryId ID der Kategorie, die aus dieser Speisekarte entfernt werden soll
     *
     * @pre categoryId != null
     * @pre Die ID categoryId muss gültig sein
     *
     * @return Entfernte Kategorie oder null, wenn die Kategorie nicht vorhanden ist
     */
    public Category removeCategory(String categoryId) {
        assert (categoryId != null);
        assert (isValidIdentifier(categoryId));

        setOfCateName.remove(this.mapCategory.get(categoryId).getName());

        return this.mapCategory.remove(categoryId);
    }

    /**
     * Erzeugt ein Gericht mit den übergebenen Angaben und fügt dieses in die Speisekarte ein.
     * Das Gericht soll der Kategorie mit der übergebenen ID hinzugefügt werden.
     * <br>
     * Sofern die Kategorie nicht in dieser Speisekarte vorhanden ist, kann das Gericht nicht
     * hinzugefügt werden.
     *
     * Diese Methode arbeitet in O(f(N)), dabei ist N = Anzahl der vorhandenen Kategorien in der
     * Speisekarte und f(N) = log(N)
     *
     * @param categoryId ID der Kategorie, in die das Gericht eingefügt werden soll
     * @param dishName Name des Gerichtes, welches zu dieser Speisekarte hinzugefügt werden soll
     * @param dishPrice Preis des Gerichtes
     * @param diet Ernährungsform der das Gericht zuzuordnen ist
     *
     * @pre categoryId != null
     * @pre dishName != null
     * @pre diet != null
     * @pre Die ID categoryId muss gültig sein
     * @pre Der Preis dishPrice muss &ge; 0 sein
     *
     * @return ID des Gerichtes, welches hinzugefügt wurde oder Null, wenn das Gericht nicht
     * hinzugefügt werden konnte
     */
    public int addDish(String categoryId, String dishName, int dishPrice, Diet diet) {
        assert (categoryId != null);
        assert (dishName != null);
        assert (diet != null);
        assert (isValidIdentifier(categoryId));
        assert (dishPrice >= 0);

        int add = 0;

        Category copy = mapCategory.get(categoryId);

        if (copy != null) {
            add = copy.addDish(new Dish(dishName, dishPrice, diet));
        }

        return add;

    }

    /**
     * Entfernt das Gericht mit der übergebenen ID aus der Kategorie mit der übergebenen ID und
     * liefert es zurück.
     *
     * Diese Methode arbeitet in O(f(N)), dabei ist N = Anzahl der vorhandenen Gerichte in der
     * Kategorie mit der übergebenen ID und f(N) = N
     *
     * @param categoryId ID der Kategorie, aus der das Gericht entfernt werden soll
     * @param dishId ID des Gerichtes, welches entfernt werden soll
     *
     * @pre categoryId != null
     * @pre Die ID categoryId muss gültig sein
     * @pre Es ist eine Kategorie mit der ID categoryId in dieser Speisekarte vorhanden
     *
     * @return Gelöschtes Gericht oder null, wenn das Gericht nicht in der Kategorie vorhanden ist
     */
    public Dish removeDish(String categoryId, int dishId) {
        assert (categoryId != null);
        assert (isValidIdentifier(categoryId));
        assert (mapCategory.containsKey(categoryId));

        return mapCategory.get(categoryId).removeDish(dishId);
    }

    /**
     * Überführt das Gericht mit der übergebenen ID von einer Kategorie in eine Andere.
     * Beide Kategorien mit den übergebenen IDs müssen für den Transfer in der Speisekarte
     * enthalten sein.
     *
     * Diese Methode arbeitet in O(f(N)), dabei ist N = Anzahl der vorhandenen Gerichte in der
     * alten Kategorie mit der übergebenen ID und f(N) = N
     *
     * @param oldCategory ID der Kategorie, aus welcher das Gericht entfernt werden soll
     * @param newCategory ID der Kategorie, in welche das Gericht eingefügt werden soll
     * @param dishId ID des Gerichtes, welches in eine andere Kategorie überführt werden soll
     *
     * @pre oldCategory != null
     * @pre newCategory != null
     * @pre Es ist eine Kategorie mit der ID oldCategory in dieser Speisekarte vorhanden
     * @pre Es ist eine Kategorie mit der ID newCategory in dieser Speisekarte vorhanden
     * @pre Das Gericht mit der ID dishID ist in der Kategorie mit der ID oldCategory enthalten
     */
    public void transferDish(String oldCategory, String newCategory, int dishId) {
        assert (oldCategory != null);
        assert (newCategory != null);
        assert (mapCategory.containsKey(oldCategory));
        assert (mapCategory.containsKey(newCategory));
        assert (mapCategory.get(oldCategory).hasDish(dishId));

        mapCategory.get(newCategory).addDish(mapCategory.get(oldCategory).removeDish(dishId));

    }

    /**
     * Liefert das erste Gericht dieser Speisekarte zurück, welches eindeutig der übergebenen
     * Ernährungsform zuzuordnen ist.
     *
     * Diese Methode arbeitet in O(f(N)), dabei ist N = Anzahl der vorhandenen Kategorien in der
     * Speisekarte und f(N) = N
     *
     * @param diet Ernährungsform
     *
     * @pre diet != null
     *
     * @return Gericht mit der Ernährungsform oder null, wenn kein passendes Gericht in dieser
     * Speisekarte vorhanden ist
     */
    public Dish getFirstDishOfDiet(Diet diet) {
        assert (diet != null);

        Dish dish = null;

        for (Category valueNames : mapCategory.values()) {
            if (dish == null) {
                dish = valueNames.getFirstDishOfDiet(diet);
            }
        }

        return dish;
    }


    /**
     * Liefert alle Gerichte dieser Speisekarte zurück, welche mindestens der übergebenen
     * Ernährungsform zuzuordnen sind.
     * <br>
     * Die Gerichte sind aufsteigend nach der Kategorie sortiert, zu der sie gehören.
     * Innerhalb einer Kategorie sind die Gerichte jeweils aufsteigend gemäß der Einschränkung der
     * Ernährungsform sortiert. Für die jeweilige Ernährungsform sind die Gerichte aufsteigend
     * nach ihrer ID sortiert.
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

        for (Category valueNames : mapCategory.values()) {
            contains.addAll(valueNames.getDishesWithAtLeastDiet(diet));
        }

        return contains;
    }

    /**
     * Liefert alle Gerichte dieser Speisekarte in der angegebenen Preisspanne.
     * Dabei kann neben validen Centbeträgen auch null angegeben werden, was dann eine
     * endlose Preisgrenze definiert.
     * <br>
     * Das Ergebnis ist aufsteigend nach der ID der Kategorien sortiert. Innerhalb einer Kategorie
     * sind die Gerichte ebenfalls aufsteigend nach ihrer ID sortiert.
     *
     * @param minPrice minimaler Preis der zu filternden Gerichte in Cent (inklusive)
     * @param maxPrice maximaler Preis der zu filternden Gerichte in Cent (exklusive)
     *
     * @return Alle Gerichte dieser Karte zwischen der angegebenen Preisspanne
     */
    public List<Dish> getDishesWithPriceBetween(Integer minPrice, Integer maxPrice) {
        
        List<Dish> contains = new ArrayList<>();

        Collection<Category> values = mapCategory.values();

        for (Category valueNames : values) {
            contains.addAll(valueNames.getDishesWithPriceBetween(minPrice, maxPrice));
        }

        return contains;
    }

    /**
     * Erstellt eine neue Speisekarte mit den Gerichten und Kategorien der übergebenen und
     * dieser Speisekarte.
     * Der Name der neuen Speisekarte setzt sich aus den beiden bisherigen Namen zusammen,
     * welche durch " - " getrennt werden.
     * <br>
     * Es gilt dabei folgendes zu beachten:
     * <ul>
     *     <li>Es dürfen keine doppelten Kategorien in der neuen Speisekarte vorhanden sein</li>
     *     <li>Für die Gleichheit von Kategorien ist die Definition von
     *     {@link FullMenu#hasCategoryWithId(String)} und
     *     {@link FullMenu#hasCategoryWithName(String)} zu beachten</li>
     *     <li>Kategorien, welche sich in beiden Speisekarten befinden werden zusammengefasst
     *     </li>
     *     <li>Die Gerichte der übergebenen Speisekarte werden hinter denen dieser Speisekarte
     *     eingefügt
     *     </li>
     *     <li>Gerichte mit gleichen Eigenschaften in beiden Speisekarte werden alle in die neue
     *     Speisekarte eingefügt</li>
     * </ul>
     *
     * @param menu Speisekarte, welche mit dieser Speisekarte zusammengeführt werden soll.
     *
     * @pre menu != null
     *
     * @return Neue zusammengeführte Speisekarte.
     */
    public FullMenu combineMenu(FullMenu menu) {
        assert (menu != null);
        
        FullMenu combMenu = new FullMenu(this.name + " - " + menu.name);

        Set<Map.Entry<String, Category>> iteratedThisSet = this.mapCategory.entrySet();
        Set<Map.Entry<String, Category>> iteratedMenuSet = menu.mapCategory.entrySet();
        //alle Kategorien dieser Speisekarte


        for (Map.Entry<String, Category> thisSet : iteratedThisSet) {
            //Überprüfung auf vorhandene ID in dem übergebenen Menü
            if (menu.hasCategoryWithId(thisSet.getKey())) {
                //Überprüfung ob die Namen an dem Key gleich sind.
                if (menu.hasCategoryWithName(thisSet.getValue().getName())
                        && menu.getCategory(thisSet.getKey()).getName()
                        .equals(thisSet.getValue().getName())) {
                    //Fügt die Category hinzu
                    combMenu.addCategory(thisSet.getKey(), thisSet.getValue().getName());
                    //Fügt die Liste hinzu
                    for (Dish dish : thisSet.getValue().getDishes()) {
                        combMenu.mapCategory.get(thisSet.getKey()).addDish(dish);
                    }
                }
                //Überprüfung ob der Name der Category aus this nicht vorhanden ist
            } else if (!menu.hasCategoryWithName(thisSet.getValue().getName())) {
                combMenu.addCategory(thisSet.getKey(), thisSet.getValue().getName());
                for (Dish dish : thisSet.getValue().getDishes()) {
                    combMenu.mapCategory.get(thisSet.getKey()).addDish(dish);
                }
            }
        }
        
        //alle Kategorien der übergebenen Speisekarte
        for (Map.Entry<String, Category> menuSet : iteratedMenuSet) {
            //für Kategorien, die noch nicht in der neuen Karte enthalten sind von übergebener Karte
            if (this.hasCategoryWithId(menuSet.getKey())) {
                //überprüft ob der this den Namen von der übergebenen hat und ob sie gleich sind
                if (this.hasCategoryWithName(menuSet.getValue().getName())
                        && this.getCategory(menuSet.getKey()).getName()
                        .equals(menuSet.getValue().getName())) {
                    for (Dish dish : menuSet.getValue().getDishes()) {
                        combMenu.mapCategory.get(menuSet.getKey()).addDish(dish);
                    }
                }
                //überprüft ob this den Namen von dem übergebenen Menü nicht hat
            } else if (!this.hasCategoryWithName(menuSet.getValue().getName())) {

                combMenu.addCategory(menuSet.getKey(), menuSet.getValue().getName());

                for (Dish dish : menuSet.getValue().getDishes()) {
                    combMenu.mapCategory.get(menuSet.getKey()).addDish(dish);
                }
            }
        }
        return combMenu;
    }

    /**
     * Erstellt eine neue Speisekarte, die alle Kategorien enthält,
     * welche sowohl in der aktuellen als auch in der übergebenen Speisekarte vorhanden sind.
     * <br>
     * Die Gerichte beider Speisekarten werden für jede Kategorie zusammengefasst. Dabei erhalten
     * die Gerichte der aktuellen Speisekarte "1_" als Präfix für den Namen. Die Gerichte der
     * übergebenen Speisekarte erhalten "2_" als Präfix und werden für die jeweilige Kategorie
     * angehängt.
     *
     * @param menu Speisekarte, welche mit dieser Speisekarte zusammengeführt werden soll
     *
     * @pre menu != null
     *
     * @return Neue Speisekarte mit den Kategorien, die in beiden Speisekarten vorhanden sind
     */
    public FullMenu intersectMenu(FullMenu menu) {
        assert (menu != null);

        FullMenu interMenu = new FullMenu(this.name);

        Set<Map.Entry<String, Category>> iteratedThisSet = this.mapCategory.entrySet();

        for (Map.Entry<String, Category> thisSet : iteratedThisSet) {
            //überprüft auf Namen und vorhandene ID
            if (menu.hasCategoryWithName(thisSet.getValue().getName())
                    && menu.hasCategoryWithId(thisSet.getKey())) {

                interMenu.addCategory(thisSet.getKey(), thisSet.getValue().getName());

                //for schleifen um den Prefix hinzuzufügen
                for (Dish dish : thisSet.getValue().getDishes()) {
                    interMenu.getCategory(thisSet.getKey())
                            .addDish(new Dish("1_" + dish.name(), dish.price(), dish.diet()));
                }
                for (Dish dish : menu.mapCategory.get(thisSet.getKey()).getDishes()) {
                    interMenu.getCategory(thisSet.getKey())
                            .addDish(new Dish("2_" + dish.name(), dish.price(), dish.diet()));
                }
            }
        }

        return interMenu;
    }

    /**
     * Liefert eine Zeichenkette zurück, welche zur Visualisierung dieser Speisekarte dient.
     * <br>
     * Die String-Repräsentation der Speisekarte ist dabei folgendermaßen aufgebaut:
     * <ul>
     *     <li>In der ersten Zeile steht der Name der Speisekarte in Großbuchstaben, welcher links
     *     und rechts durch jeweils zehn Bindestriche abgegrenzt wird</li>
     *     <li>Darunter werden nach einer Leerzeile alle Kategorien untereinander aufgelistet</li>
     *     <li>Die Überschrift einer Kategorie wird dabei durch ihre ID gefolgt von einem Punkt,
     *     einem Leerzeichen angegeben</li>
     *     <li>Danach folgt die Ausgabe der Kategorie gemäß {@link Category#toString()}</li>
     *     <li>Bevor die nächste Kategorie angegeben wird, folgt eine Trennung der Kategorien durch
     *     eine Leerzeile</li>
     * </ul>
     *
     * @return Zeichenkette, welche diese Speisekarte darstellt
     */
    @Override
    public String toString() {
        StringBuilder menu =
                new StringBuilder("----------" + name.toUpperCase() + "----------\n");

        Set<Map.Entry<String, Category>> iteratedThisSet = this.mapCategory.entrySet();

        for (Map.Entry<String, Category> thisSet : iteratedThisSet) {
            menu.append("\n").append(thisSet.getKey()).append(". ")
                    .append(mapCategory.get(thisSet.getKey()));

        }
        return menu.toString();
    }

    /**
     * Überprüft. ob die übergebene Zeichenkette eine gültige ID für eine Kategorie ist.
     * <br>
     * Ein valider Bezeichner besteht aus einem Zeichen aus der
     * Menge [a-zA-Z] gefolgt von einer beliebigen Anzahl von Zeichen aus der Menge [a-zA-Z0-9_].
     *
     * @param str Zeichenkette, die überprüft werden soll
     *
     * @pre str != null
     *
     * @return Angabe, ob die Zeichenkette ein gültiger Bezeichner ist
     */
    public static boolean isValidIdentifier(String str) {
        assert (str != null);
        return str.matches("^[a-zA-Z]\\w*");
    }

    /**
     * Überprüft, ob alle übergebenen Zeichenketten gültige IDs für die Kategorien darstellen.
     * <br>
     * Ein valider Bezeichner besteht aus einem Zeichen aus der
     * Menge [a-zA-Z] gefolgt von einer beliebigen Anzahl von Zeichen aus der Menge [a-zA-Z0-9_].
     *
     * @param strs Zeichenketten, die überprüft werden sollen
     *
     * @pre strs != null
     *
     * @return Angabe, ob alle Zeichenketten gültiger Bezeichner sind
     */
    public static boolean areValidIdentifiers(Collection<String> strs) {
        assert (strs != null);

        boolean contain = true;

        for (String str : strs) {
            if (contain) {
                contain = isValidIdentifier(str);
            }
        }
        return contain;
    }
}
