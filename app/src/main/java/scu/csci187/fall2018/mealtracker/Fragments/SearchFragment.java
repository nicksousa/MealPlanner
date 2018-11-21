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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import java.util.ArrayList;
import java.util.List;

import scu.csci187.fall2018.mealtracker.Classes.PreferencesTranslator;
import scu.csci187.fall2018.mealtracker.Classes.QueryParam;
import scu.csci187.fall2018.mealtracker.Classes.SearchRecyclerViewAdapter;
import scu.csci187.fall2018.mealtracker.Classes.UserPreferences;
import scu.csci187.fall2018.mealtracker.R;


public class SearchFragment extends Fragment {

    private RecyclerView rvSearch;
    private EditText searchText;
    private Button buttonSearch;
    private ImageButton buttonFilters;
    private UserPreferences userPrefs;
    private UserPreferences inputtedFilters;

    private SearchFragmentListener mCallback;
    private List<String> meals, pics;

    private String myInputString = "";

    public SearchFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            myInputString = savedInstanceState.getString("inputString");
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.search_layout, container, false);
        bindViews(view);
        addButtonListeners();

        return view;
    }

    // Ensures that Activity has implemented FiltersFragmentListener
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof SearchFragment.SearchFragmentListener) {
            mCallback = (SearchFragment.SearchFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement FiltersFragmentListener");
        }

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallback = null;
    }

    private void bindViews(View view) {
        rvSearch = view.findViewById(R.id.rvSearch);
        searchText = view.findViewById(R.id.inputSearchTerms);
        buttonFilters = view.findViewById(R.id.buttonSearchFilters);
        buttonSearch = view.findViewById(R.id.buttonSearchGo);
    }

    private void addButtonListeners() {
        buttonFilters.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFiltersFragment();
            }
        });

        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                executeSearch();
            }
        });
    }

    private void openFiltersFragment() {
        myInputString = searchText.getText().toString();
        mCallback.goToFilters(myInputString);
    }

    /*
        TODO: implement executeSearch()
     */
    private void executeSearch() {
        String searchString;

        searchString = searchText.getText().toString();

        // Get filters
        // Construct Query
        QueryParam qp = new QueryParam();


        // TODO implement once I have the necessary methods.
        // All methods called by storageHandler are placeholders.
        // storageHandler deosn't exist. Read above todo.
        qp.setQuery(searchString);
        PreferencesTranslator prefsTranslator = new PreferencesTranslator();

        prefsTranslator.setDietInQueryParam(userPrefs, qp);
        prefsTranslator.setHealthLabelsInQueryParam(userPrefs, qp);





        populateSearchListFromAPI();
        createAndAttachRVAdapter();
    }

    /*
        TODO: implement populateListDataFromAPI()
     */
    private void populateSearchListFromAPI() {
        meals = new ArrayList<>();
        pics = new ArrayList<>();


        // DB Calls to build List<string> meals/pics for search
        meals.add("Meal 1");
        meals.add("Meal 2");
        meals.add("Meal 3");
        meals.add("Meal 4");
        pics.add("https://food.fnr.sndimg.com/content/dam/images/food/fullset/2007/2/8/0/ig0805_soup.jpg.rend.hgtvcom.616.462.suffix/1396643717441.jpeg");
        pics.add("https://www.rareseeds.com/assets/1/14/DimRegular/Corn-True-Gold-CN133-LSS-000_2485.jpg");
        pics.add("https://food.fnr.sndimg.com/content/dam/images/food/fullset/2007/2/8/0/ig0805_soup.jpg.rend.hgtvcom.616.462.suffix/1396643717441.jpeg");
        pics.add("https://www.rareseeds.com/assets/1/14/DimRegular/Corn-True-Gold-CN133-LSS-000_2485.jpg");
    }

    private void createAndAttachRVAdapter() {
        SearchRecyclerViewAdapter searchAdapter = new SearchRecyclerViewAdapter(getContext(),
                meals, pics, this);
        rvSearch.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvSearch.setAdapter(searchAdapter);
    }

    public void applyFiltersToSearch(String inputText, UserPreferences filters) {
        inputtedFilters = filters;
        searchText.setText(inputText);
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

    public interface SearchFragmentListener {
        void goToFilters(String inputString);
    }
    public void receivePreferences(UserPreferences userPrefs) {
        this.userPrefs = userPrefs;
    }
}