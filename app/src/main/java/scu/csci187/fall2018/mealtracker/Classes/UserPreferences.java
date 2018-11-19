package scu.csci187.fall2018.mealtracker.Classes;

enum Diet {
    NONE (-1), LOWCARB(0), LOWFAT(1), HIGHPROTEIN(2), HIGHFIBER(3), LOWSODIUM(4);

    private int val;
    Diet(int val) { this.val = val;}
    public int getVal() { return val; }
}

public class UserPreferences {

    private int calorieLow;
    private int calorieHigh;
    private int maxTimeInMinutes;
    private int dietLabel;
    private boolean vegetarian;
    private boolean vegan;
    private boolean pescatarian;
    private boolean kosher;
    private boolean gluten;
    private boolean paleo;
    private boolean shellfish;
    private boolean dairy;
    private boolean treenut;
    private boolean peanut;
    private boolean egg;

    public UserPreferences(int calorieLow, int calorieHigh, int maxTimeInMinutes, int dietLabel,
                           boolean vegetarian, boolean vegan, boolean pescatarian,
                           boolean kosher, boolean gluten, boolean paleo, boolean shellfish,
                           boolean dairy, boolean treenut, boolean peanut, boolean egg) {
        this.calorieLow = calorieLow;
        this.calorieHigh = calorieHigh;
        this.maxTimeInMinutes = maxTimeInMinutes;
        this.dietLabel = dietLabel;
        this.vegetarian = vegetarian;
        this.vegan = vegan;
        this.pescatarian = pescatarian;
        this.kosher = kosher;
        this.gluten = gluten;
        this.paleo = paleo;
        this.shellfish = shellfish;
        this.dairy = dairy;
        this.treenut = treenut;
        this.peanut = peanut;
        this.egg = egg;
    }

}
