package scu.csci187.fall2018.mealtracker.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;

import scu.csci187.fall2018.mealtracker.Activities.ViewRecipeActivity;
import scu.csci187.fall2018.mealtracker.Classes.APIHandler;
import scu.csci187.fall2018.mealtracker.Classes.ImageLoaderFromUrl;
import scu.csci187.fall2018.mealtracker.Classes.Ingredient;
import scu.csci187.fall2018.mealtracker.Classes.Ingredients;
import scu.csci187.fall2018.mealtracker.Classes.Recipe;
import scu.csci187.fall2018.mealtracker.R;


public class MealDetailFragment extends Fragment {
    private TextView tvMealName;
    private ImageView ivMealPic;
    private RatingBar mealRatingBar;
    private ListView lvIngredients;
    private Button buttonToRecipe;
    private Ingredients ingredients;
    private Recipe r;

    private String mealName, picURL, recipeURL, bookmarkURL;
    private ArrayList<String> ingredientsList;
    private int mealRating;


    public MealDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            bookmarkURL = getArguments().getString("bookmarkURL");
            ArrayList<String> bookmarks = new ArrayList<>();
            bookmarks.add(bookmarkURL);
            r = new APIHandler().getRecipesFromBookmarks(bookmarks).get(0);
            picURL = r.imageUrl();
            mealName = r.name();
            recipeURL = r.linkToInstructions();
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.mealdetail_layout, container, false);
        bindViews(view);
        attachUIListeners();

        ingredientsList = new ArrayList<>();

        if(!mealName.isEmpty())
            populateMealData();
        else
            getFragmentManager().popBackStackImmediate();   // return to previous fragment

        return view;
    }

    private void bindViews(View view) {
        tvMealName = view.findViewById(R.id.mealDetailName);
        ivMealPic = view.findViewById(R.id.mealDetailPic);
        mealRatingBar = view.findViewById(R.id.mealRatingBar);
        lvIngredients = view.findViewById(R.id.ingredientsList);
        buttonToRecipe = view.findViewById(R.id.buttonGoToRecipe);
    }

    private void attachUIListeners() {
        attachRecipeButtonListener();
        attachRatingBarListener();
    }

    private void attachRatingBarListener() {
        mealRatingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                updateUserMealRating((int)rating);
            }
        });
    }

    private void attachRecipeButtonListener() {
        buttonToRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToRecipe = new Intent(getContext(), ViewRecipeActivity.class);
                /*
                    TODO: Grab recipe URL via API using MealName
                 */
                goToRecipe.putExtra("recipeURL", recipeURL);
                startActivity(goToRecipe);
            }
        });
    }

    public void updateUserMealRating(int newRating) {
        /*
            TODO: Add meal rating to DB for User
         */
    }

    public void populateMealData() {
        tvMealName.setText(mealName);
        new ImageLoaderFromUrl(ivMealPic).execute(picURL);

        mealRatingBar.setRating(3);
        Ingredient ingredient;
        for (int i = 0; i < r.ingredients().length(); ++i) {
            ingredient = r.ingredients().getIngredientAtIndex(i);
            ingredientsList.add(ingredient.food());
        }


        ArrayAdapter<String> ingredientsAdapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_list_item_1, ingredientsList);
        lvIngredients.setAdapter(ingredientsAdapter);

        // populate pic, nutritional info, ingredients using mealName + API

        /*
            TODO: grab meal data from API, DB (rating) for display in MealDetail
            load ingredients strings into ingredientsList

            ArrayAdapter<String> ingredientsAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, ingredientsList);
            lvIngredients.setAdapter(ingredientsAdapter);

            GRAB RATING FROM DB
            mealRating =
            mealRatingBar.setRating(mealRating);
         */

    }
}
