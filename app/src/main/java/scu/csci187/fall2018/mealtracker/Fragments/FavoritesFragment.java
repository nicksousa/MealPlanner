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

import java.util.ArrayList;
import java.util.List;

import scu.csci187.fall2018.mealtracker.Classes.FavoritesRecyclerViewAdapter;
import scu.csci187.fall2018.mealtracker.R;


public class FavoritesFragment extends Fragment {
    private OnFragmentInteractionListener mListener;
    private RecyclerView rvFavorites;
    private List<String> meals, pics;

    public FavoritesFragment() {
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
        View view = inflater.inflate(R.layout.favorites_layout, container, false);

        rvFavorites = view.findViewById(R.id.rvFavorites);
        populateFavoritesListFromAPI();
        createAndAttachRVAdapter();

        return view;
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

    /*
        TODO: implement populateListDataFromAPI()
     */
    public void populateFavoritesListFromAPI() {
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

    public void createAndAttachRVAdapter() {
        FavoritesRecyclerViewAdapter favoritesAdapter = new FavoritesRecyclerViewAdapter(getContext(),
                meals, pics, this);
        rvFavorites.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvFavorites.setAdapter(favoritesAdapter);
    }

    // Create then display Meal Detail fragment using mealName
    public void showMealDetail(String mealName, String picURL) {
        MealDetailFragment newFragment = new MealDetailFragment();
        Bundle b = new Bundle();
        b.putString("mealName", mealName);
        b.putString("picURL", picURL);
        newFragment.setArguments(b);
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(getId(), newFragment);
        transaction.addToBackStack(null);
        transaction.commit();
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