package scu.csci187.fall2018.mealtracker.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import scu.csci187.fall2018.mealtracker.R;

public class ShoppingListFragment extends ListFragment {

    LinearLayout ingredientsLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ingredientsLayout = new LinearLayout(getContext());


        return inflater.inflate(R.layout.shoppinglist_layout, container, false);
    }


    /**
     * Create a new instance of DetailsFragment, initialized to
     * show the text at 'index'.
     */
    public static ShoppingListFragment newInstance(int index) {
        ShoppingListFragment f = new ShoppingListFragment();

        // Supply index input as an argument.
        Bundle args = new Bundle();
        args.putInt("index", index);
        f.setArguments(args);
        return f;
    }
}
