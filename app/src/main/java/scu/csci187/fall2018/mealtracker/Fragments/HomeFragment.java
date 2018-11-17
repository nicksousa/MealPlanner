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

import java.util.ArrayList;
import java.util.List;

import scu.csci187.fall2018.mealtracker.Classes.HomeRecyclerViewAdapter;
import scu.csci187.fall2018.mealtracker.R;


public class HomeFragment extends Fragment {
    private OnFragmentInteractionListener mListener;

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
        todaysCalories = view.findViewById(R.id.todaysCalories);
        macroCarb = view.findViewById(R.id.macroCarb);
        macroProtein = view.findViewById(R.id.macroProtein);
        macroFat = view.findViewById(R.id.macroFat);

        rvUpcoming = view.findViewById(R.id.rvUpcoming);
        rvHistory = view.findViewById(R.id.rvHistory);

        populateListDataFromDB();
        createAndAttachRVAdapters();
        // create and attach adapters to Upcoming and History RecycleViews

        return view;
    }

    public void populateListDataFromDB() {
        upcomingMeals = new ArrayList<>();
        upcomingDates = new ArrayList<>();
        upcomingPics = new ArrayList<>();
        historyMeals = new ArrayList<>();
        historyDates = new ArrayList<>();
        historyPics = new ArrayList<>();

        // DB Calls to build List<string> meals/dates for upcoming + history
        upcomingMeals.add("ZZZZZZZZZZZZZZ");
        upcomingMeals.add("Meal 2");
        upcomingMeals.add("Meal 3");
        upcomingMeals.add("Meal 4");
        upcomingDates.add("11/1/1");
        upcomingDates.add("11/1/1");
        upcomingDates.add("11/1/1");
        upcomingDates.add("11/1/1");
        upcomingPics.add("https://food.fnr.sndimg.com/content/dam/images/food/fullset/2007/2/8/0/ig0805_soup.jpg.rend.hgtvcom.616.462.suffix/1396643717441.jpeg");
        upcomingPics.add("https://www.rareseeds.com/assets/1/14/DimRegular/Corn-True-Gold-CN133-LSS-000_2485.jpg");
        upcomingPics.add("https://food.fnr.sndimg.com/content/dam/images/food/fullset/2007/2/8/0/ig0805_soup.jpg.rend.hgtvcom.616.462.suffix/1396643717441.jpeg");
        upcomingPics.add("https://www.rareseeds.com/assets/1/14/DimRegular/Corn-True-Gold-CN133-LSS-000_2485.jpg");


        historyMeals.add("hist 1");
        historyMeals.add("hist 2");
        historyDates.add("0/0/0");
        historyDates.add("4/4/4/");
        historyPics.add("https://www.rareseeds.com/assets/1/14/DimRegular/Corn-True-Gold-CN133-LSS-000_2485.jpg");
        historyPics.add("https://food.fnr.sndimg.com/content/dam/images/food/fullset/2007/2/8/0/ig0805_soup.jpg.rend.hgtvcom.616.462.suffix/1396643717441.jpeg");
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
    public void showMealDetail(String mealName) {
        MealDetailFragment newFragment = new MealDetailFragment();
        Bundle b = new Bundle();
        b.putString("mealName", mealName);
        newFragment.setArguments(b);
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(getId(), newFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(String id);
    }
}
