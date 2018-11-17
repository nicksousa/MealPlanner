package scu.csci187.fall2018.mealtracker.Classes;
import java.util.ArrayList;

public class QueryParam {

    private String diet;
    private ArrayList<String> healthLabels;
    private int calorieMax;
    private int calorieMin;
    private int timeMax;
    private int timeMin;
    private String query;

    public QueryParam (){
        // No Initialization. Must do manually through getters and setters
    }
    public void setQuery (String q) {
        this.query = q;
    }
    public String getDiet() {
        return diet;
    }

    public void setDiet(String diet) {
        this.diet = diet;
    }

    public ArrayList<String> getHealthLabels() {
        return healthLabels;
    }

    public void setHealthLabels(String healthLabel) {
        this.healthLabels.add(healthLabel);
    }

    public int getCalorieMax() {
        return calorieMax;
    }

    public void setCalorieMax(int calorieMax) {
        this.calorieMax = calorieMax;
    }

    public int getCalorieMin() {
        return calorieMin;
    }

    public void setCalorieMin(int calorieMin) {
        this.calorieMin = calorieMin;
    }

    public int getTimeMax() {
        return timeMax;
    }

    public void setTimeMax(int timeMax) {
        this.timeMax = timeMax;
    }

    public int getTimeMin() {
        return timeMin;
    }

    public void setTimeMin(int timeMin) {
        this.timeMin = timeMin;
    }

    public String assembleSearchURL () {
        String result = "https://api.edamam.com/search?";

        // Set diet params
        result = result + "diet=" + this.diet + "&";

        // Set Calorie range
        result = result + "calories=" + this.calorieMin + "-" + this.calorieMax + "&";

        // Set time range
        result = result + "time=" + this.timeMin + "-" + this.timeMax + "&";

        // Set query param
        result = "q=" + this.query + "&";

        // Add in health labels
        for (int i = 0; i < this.healthLabels.size(); ++i) {
            String andSign = "&";
            if (i == 0) {
                andSign = "";
            }
            result = result + andSign + "health=" + this.healthLabels.get(i);
        }
        return result;
    }
}
