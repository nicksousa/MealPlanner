package scu.csci187.fall2018.mealtracker.Fragments;

import android.content.Context;
import android.media.Rating;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import scu.csci187.fall2018.mealtracker.Classes.HomeRecyclerViewAdapter;
import scu.csci187.fall2018.mealtracker.R;


public class MealDetailFragment extends Fragment {
    private OnFragmentInteractionListener mListener;

    private TextView tvMealName;
    private RatingBar mealRatingBar;
    private ListView lvIngredients;
    private Button buttonToInstructions;

    private String mealName, picURL, instructionsURL;
    private ArrayList<String> ingredientsList;
    private int mealRating;


    public MealDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mealName = getArguments().getString("mealName");
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.mealdetail_layout, container, false);
        tvMealName = view.findViewById(R.id.mealDetailName);
        mealRatingBar = view.findViewById(R.id.mealRatingBar);
        lvIngredients = view.findViewById(R.id.ingredientsList);

        ingredientsList = new ArrayList<>();

        if(!mealName.isEmpty())
            populateMealData();
        else
            getFragmentManager().popBackStackImmediate();   // return to previous fragment

        return view;
    }

    public void populateMealData() {
        tvMealName.setText(mealName);
        mealRatingBar.setRating(3);
        ingredientsList.add("1/2 oz chicken");
        ingredientsList.add("5 lbs salt");
        ArrayAdapter<String> ingredientsAdapter = new ArrayAdapter<String>(getActivity(),
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
