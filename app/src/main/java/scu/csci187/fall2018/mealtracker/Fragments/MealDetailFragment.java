package scu.csci187.fall2018.mealtracker.Fragments;

import android.content.Context;
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
import android.widget.Toast;

import java.util.ArrayList;

import scu.csci187.fall2018.mealtracker.Activities.ViewRecipeActivity;
import scu.csci187.fall2018.mealtracker.Classes.ImageLoaderFromUrl;
import scu.csci187.fall2018.mealtracker.R;


public class MealDetailFragment extends Fragment {
    private TextView tvMealName;
    private ImageView ivMealPic, ivFavorite;
    private RatingBar mealRatingBar;
    private ListView lvIngredients;
    private Button buttonToRecipe, buttonMadeThis;

    private String mealName, picURL, recipeURL;
    private boolean showMadeButton = false;
    private ArrayList<String> ingredientsList;
    private int mealRating;
    private int index = -1;
    private boolean mealIsFavorited;

    private MadeMealListener madeMealListener;

    public MealDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mealName = getArguments().getString("mealName");
            picURL = getArguments().getString("picURL");
            recipeURL = getArguments().getString("recipeURL");
            index = getArguments().containsKey("index") ? getArguments().getInt("index") : -1;
            showMadeButton = getArguments().containsKey("madeThis") ? getArguments().getBoolean("madeThis") : false;
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.mealdetail_layout, container, false);
        bindViews(view);
        attachUIListeners();
        setupRatingBarAndFavorite();

        ingredientsList = new ArrayList<>();

        if(!mealName.isEmpty()) {
            populateMealData();
            if(showMadeButton)
                buttonMadeThis.setVisibility(View.VISIBLE);
        }
        else
            getFragmentManager().popBackStackImmediate();   // return to previous fragment

        return view;
    }

    // Ensures that Activity has implemented FiltersFragmentListener
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof MealDetailFragment.MadeMealListener) {
            madeMealListener = (MealDetailFragment.MadeMealListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement MadeMealListener");
        }
    }

    private void bindViews(View view) {
        tvMealName = view.findViewById(R.id.mealDetailName);
        ivMealPic = view.findViewById(R.id.mealDetailPic);
        ivFavorite = view.findViewById(R.id.buttonMealDetailFavorite);
        mealRatingBar = view.findViewById(R.id.mealRatingBar);
        lvIngredients = view.findViewById(R.id.ingredientsList);
        buttonToRecipe = view.findViewById(R.id.buttonGoToRecipe);
        buttonMadeThis = view.findViewById(R.id.buttonMadeThis);
    }

    private void attachUIListeners() {
        attachRecipeButtonListener();
        attachRatingBarListener();
        attachFavoritesListener();
        attachMadeButtonListener();
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

    private void attachRatingBarListener() {
        mealRatingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                updateUserMealRatingInDb((int)rating);
            }
        });
    }

    private void attachFavoritesListener() {
        ivFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mealIsFavorited) {
                    mealIsFavorited = false;
                    ivFavorite.setImageResource(R.drawable.ic_favorite_no);
                    updateMealFavoriteInDb(mealIsFavorited);
                } else {
                    mealIsFavorited = true;
                    ivFavorite.setImageResource(R.drawable.ic_favorite);
                    updateMealFavoriteInDb(mealIsFavorited);
                }
            }
        });
    }

    private void attachMadeButtonListener() {
        buttonMadeThis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(index == -1)
                    Toast.makeText(getContext(), "ERROR - INDEX: -1, bundle index null", Toast.LENGTH_SHORT).show();
                else {
                    madeMealListener.madeMealUpdateHistory(index);
                }
            }
        });
    }

    private void setupRatingBarAndFavorite() {
        /*
            TODO: Query DB for is meal favorited, meal rating
         */
        mealIsFavorited = true; // query result here
        if(mealIsFavorited)
            ivFavorite.setImageResource(R.drawable.ic_favorite);
        else
            ivFavorite.setImageResource(R.drawable.ic_favorite_no);
        mealRating = 2; // query result here
        mealRatingBar.setRating(mealRating);
    }

    private void updateUserMealRatingInDb(int newRating) {
        /*
            TODO: Write meal rating to DB for User
         */
    }
    private void updateMealFavoriteInDb(boolean isFavorited) {
        /*
            TODO: Write meal favorite to DB for User
         */
    }

    public void populateMealData() {
        tvMealName.setText(mealName);
        new ImageLoaderFromUrl(ivMealPic).execute(picURL);

        mealRatingBar.setRating(3);
        ingredientsList.add("1/2 oz chicken");
        ingredientsList.add("5 lbs salt");
        ingredientsList.add("1 bay leaf");
        ingredientsList.add("23 ibuprofen");
        ingredientsList.add("6 rabbit's foot");
        ingredientsList.add("1/2 can Red Bull");
        ingredientsList.add("2 spinach leaves");


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

    public void setMadeMealListener(MadeMealListener listener) {
        this.madeMealListener = listener;
    }

    public interface MadeMealListener {
        public void madeMealUpdateHistory(int index);
    }
}
