package scu.csci187.fall2018.mealtracker.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import scu.csci187.fall2018.mealtracker.Classes.APIHandler;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import scu.csci187.fall2018.mealtracker.Classes.HomeRecyclerViewAdapter;
import scu.csci187.fall2018.mealtracker.Classes.Recipe;
import scu.csci187.fall2018.mealtracker.Classes.RecipeRecord;
import scu.csci187.fall2018.mealtracker.Classes.RecipeRecordComparator;
import scu.csci187.fall2018.mealtracker.R;


public class HomeFragment extends Fragment {
    //private OnFragmentInteractionListener mListener;

    private TextView todaysCalories, macroCarb, macroProtein, macroFat;
    private RecyclerView rvUpcoming, rvHistory;

    private List<String> upcomingMeals, upcomingDates, upcomingPics,
            historyMeals, historyDates, historyPics;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.homescreen_layout, container, false);
        bindViews(view);
        populateListDataFromDB();
        createAndAttachRVAdapters();

        return view;
    }

    private void bindViews(View view) {
        todaysCalories = view.findViewById(R.id.todaysCalories);
        macroCarb = view.findViewById(R.id.macroCarb);
        macroProtein = view.findViewById(R.id.macroProtein);
        macroFat = view.findViewById(R.id.macroFat);
        rvUpcoming = view.findViewById(R.id.rvUpcoming);
        rvHistory = view.findViewById(R.id.rvHistory);
    }

    public void populateListDataFromDB() {
        APIHandler apiHandler = new APIHandler();
        /*
            Call the DB accession file;
            Return list here as meals;
         */

        // Meals should be given DB values (bookmark links)
        ArrayList<String> bookmarkedMeals = new ArrayList<>();
        ArrayList<Recipe> recipes = new ArrayList<>();

        for (int i = 0; i < bookmarkedMeals.size(); ++i) {
            String API_IDs = "&app_id=b957081d&app_key=889e79d32df59ed1621b6247b075e26a";
            String currentMealLink = bookmarkedMeals.get(i);
            Recipe returnedRecipe = new Recipe( apiHandler.getJSONFromURL( currentMealLink + API_IDs) );
            recipes.add(returnedRecipe);
        }

        // Initialize lists that correspond to UI elements (Parallel)

        // Parallel set -> (Upcoming)
        upcomingMeals = new ArrayList<>();
        upcomingDates = new ArrayList<>();
        upcomingPics = new ArrayList<>();

        // Parallel set -> (History)
        historyMeals = new ArrayList<>();
        historyDates = new ArrayList<>();
        historyPics = new ArrayList<>();


        // Ensure ArrayLists are empty;
        upcomingMeals.clear();
        upcomingDates.clear();
        upcomingPics.clear();
        historyMeals.clear();
        historyDates.clear();
        historyPics.clear();

        ArrayList<RecipeRecord> recipeRecords = new ArrayList<>();

        // TODO fix DB call when we get the SQL Query functions.
        // Currently this line is incomplete because r.getDataFromDBAsString()
        // is a placeholder and not functional.
        for (Recipe r : recipes) {
            recipeRecords.add(new RecipeRecord(r.linkInAPI(), r.name(), r.getDateFromDBAsString(), r.imageUrl()));
        }


        // Sort the recipe records to make it easier to input them in order
        // into their respective ArrayLists.
        Collections.sort(recipeRecords, new RecipeRecordComparator());


        // Separating the recipe records into parallel ArrayLists is
        // important because the UI is currently built this way.
        // Note: The compareTo is untested and might be backwards
        // TODO when we have data check that this works
        for (RecipeRecord rr : recipeRecords) {
            if (rr.getDate().compareTo(new Date()) >= 0) {
                upcomingMeals.add(rr.getName());
                upcomingDates.add(rr.getDateString());
                upcomingPics.add(rr.getPicURL());
            }
        }
        for (RecipeRecord rr : recipeRecords) {
            if (rr.getDate().compareTo(new Date()) < 0) {
                historyMeals.add(rr.getName());
                historyDates.add(rr.getDateString());
                historyPics.add(rr.getPicURL());
            }
        }



    }

    public void createAndAttachRVAdapters() {
        HomeRecyclerViewAdapter upcomingAdapter = new HomeRecyclerViewAdapter(getContext(),
                                    upcomingMeals, upcomingDates, upcomingPics, this);
        rvUpcoming.setLayoutManager(new LinearLayoutManager(getActivity(),
                                 LinearLayoutManager.HORIZONTAL, false));
        rvUpcoming.setAdapter(upcomingAdapter);

        HomeRecyclerViewAdapter historyAdapter = new HomeRecyclerViewAdapter(getContext(),
                                    historyMeals, historyDates, historyPics, this);
        rvHistory.setLayoutManager(new LinearLayoutManager(getActivity(),
                                        LinearLayout.HORIZONTAL, false));
        rvHistory.setAdapter(historyAdapter);
    }

    // Create then display Meal Detail fragment using mealName
    public void showMealDetail(String mealName, String picURL) {
        MealDetailFragment newFragment = new MealDetailFragment();
        Bundle b = new Bundle();
        b.putString("mealName", mealName);
        b.putString("picURL", picURL);
        b.putString("recipeURL", "https://en.wikipedia.org/wiki/Pok%C3%A9mon:_Detective_Pikachu");
        newFragment.setArguments(b);
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(getId(), newFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

}
