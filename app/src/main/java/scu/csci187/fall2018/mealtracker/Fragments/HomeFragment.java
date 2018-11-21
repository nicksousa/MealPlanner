package scu.csci187.fall2018.mealtracker.Fragments;

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

import org.json.JSONObject;

import scu.csci187.fall2018.mealtracker.Classes.APIHandler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

import scu.csci187.fall2018.mealtracker.Classes.HomeRecyclerViewAdapter;
import scu.csci187.fall2018.mealtracker.Classes.QueryParam;
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
        /*
            Call the DB accession file;
            Return list here as meals;
         */

        // Meals should be given DB values (bookmark links)
        ArrayList<String> bookmarkedMeals = new ArrayList<>();
        ArrayList<Recipe> recipes = new ArrayList<>();

        bookmarkedMeals.add("http://www.edamam.com/ontologies/edamam.owl#recipe_3da1169eb633a5e4607890ebf7dee89f");
        bookmarkedMeals.add("http://www.edamam.com/ontologies/edamam.owl#recipe_d81795fb677ba4f12ab1a104e10aac98");


        QueryParam qp = new QueryParam();
        for (int i = 0; i < bookmarkedMeals.size(); ++i) {
            String currentMealLink = bookmarkedMeals.get(i);
            String formattedLink = qp.getFormattedBookmarkURL(currentMealLink);
            JSONObject json;
            //  TODO Make an alias for execute so its not as ugly with the get() hanging off the end. Not intuitive.
            try {
                json = new APIHandler().execute( formattedLink ).get();
            } catch (ExecutionException e) {
                e.printStackTrace();
                json = new JSONObject();
            } catch (InterruptedException e) {
                e.printStackTrace();
                json = new JSONObject();

            }


            Recipe returnedRecipe = new Recipe(json);
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


        ArrayList<RecipeRecord> recipeRecords = new ArrayList<>();

        // TODO fix DB call (hardcoded date) when we get the SQL Query functions.
        // Currently this line is incomplete because r.getDataFromDBAsString()
        // is a placeholder and not functional.
        for (Recipe r : recipes) {
            recipeRecords.add(new RecipeRecord(r.linkInAPI(), r.name(),"12/1/2018", r.imageUrl()));
        }


        // Sort the recipe records to make it easier to input them in order
        // into their respective ArrayLists.
        Collections.sort(recipeRecords, new RecipeRecordComparator());


        // Separating the recipe records into parallel ArrayLists is
        // important because the UI is currently built this way.
        // Note: The compareTo is untested and might be backwards
        // TODO when we have data check that this works
        for (RecipeRecord rr : recipeRecords) {
            System.out.println("    1 Date Values. new first and then mine");
            System.out.println(new Date().toString());
            System.out.println(rr.getDate().toString());
            if (rr.getDate().compareTo(new Date()) >= 0) {

                upcomingMeals.add(rr.getName());
                upcomingDates.add(rr.getDateString());
                upcomingPics.add(rr.getPicURL());
            }
        }
        for (RecipeRecord rr : recipeRecords) {
            System.out.println("    2 Date Values. new first and then mine");
            System.out.println(new Date().toString());
            System.out.println(rr.getDate().toString());
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