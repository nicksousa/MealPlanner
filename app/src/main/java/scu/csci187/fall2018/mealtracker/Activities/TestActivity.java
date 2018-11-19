package scu.csci187.fall2018.mealtracker.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import scu.csci187.fall2018.mealtracker.Classes.*;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import scu.csci187.fall2018.mealtracker.R;

public class TestActivity extends AppCompatActivity {

    ListView lv;
    ArrayList<String> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        lv = findViewById(R.id.listView);

        // Test of QueryParam
        QueryParam qptest = new QueryParam();
        qptest.setQuery("chicken");
        qptest.setCalorieMax(900);
        qptest.setCalorieMin(200);
        qptest.setTimeMax(75);
        qptest.setTimeMin(30);
        qptest.setDiet("low-fat");
        //String search = qptest.assembleSearchURL();


        APIHandler apiHandler = new APIHandler();
        Query query;
        try {
            query = apiHandler.execute(qptest).get();
        } catch (ExecutionException e) {
           query = null;
        } catch (InterruptedException e) {
            query = null;
        }

        Recipe recipe = query.getRecipeAtIndex(0);


        Ingredients ingredients = recipe.ingredients();

        ArrayList<String> data = new ArrayList<>();
        data.add("---------Food stats----------");
        data.add("Calories = " + recipe.calories());
        data.add("Carbs = " + recipe.carbs());
        data.add("Cholesterol = " + recipe.cholesterol());
        data.add("Fat = " + recipe.fat());
        data.add("Fiber = " + recipe.fiber());
        data.add("Protein = " + recipe.protein());
        data.add("Sodium = " + recipe.sodium());
        data.add("Sugar = " + recipe.sugar());
        data.add("Time To Cook = " + recipe.timeToCook());
        data.add("-----------Recipe Metadata----------");
        data.add("SourceOfRecipeURL = " + recipe.sourceUrl());
        data.add("ShareLink = " + recipe.shareLink());
        data.add("BookmarkLink = " + recipe.linkInAPI());
        data.add("image url = " + recipe.imageUrl());
        data.add("----------Ingrdient Tests--------");
        data.add("Food = " + ingredients.getIngredientAtIndex(0).food());
        data.add("Measure = " + ingredients.getIngredientAtIndex(0).measure());
        data.add("Quantity = " + ingredients.getIngredientAtIndex(0).quantity());
        data.add("Text = " + ingredients.getIngredientAtIndex(0).text());
        data.add("Weight = " + ingredients.getIngredientAtIndex(0).weight());

        ArrayAdapter<String> lvAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, data);
        lv.setAdapter(lvAdapter);


    }
}
