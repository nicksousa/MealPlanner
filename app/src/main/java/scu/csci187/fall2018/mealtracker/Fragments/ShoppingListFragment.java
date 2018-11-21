package scu.csci187.fall2018.mealtracker.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.ArrayList;

import scu.csci187.fall2018.mealtracker.Classes.ShoppingListAdapter;
import scu.csci187.fall2018.mealtracker.R;

public class ShoppingListFragment extends Fragment {

    LinearLayout ingredientsLayout;
    ListView lvShoppingIngredients;

    public ShoppingListFragment() {
        // required empty constructor
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
        View view = inflater.inflate(R.layout.shoppinglist_layout, container, false);
        lvShoppingIngredients = view.findViewById(R.id.shoppingMealList);

        //ingredientsLayout = new LinearLayout(getContext());

        // get shopping list  (list of meal names) from local storage
        ArrayList<String> mealList = new ArrayList<>();
        mealList.add("Borscht");
        mealList.add("Shakshuka");

        ShoppingListAdapter shoppingAdapter = new ShoppingListAdapter(getContext(), mealList);

       lvShoppingIngredients.setAdapter(shoppingAdapter);



        return view;
    }

}
