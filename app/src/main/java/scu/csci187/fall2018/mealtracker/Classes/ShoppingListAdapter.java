package scu.csci187.fall2018.mealtracker.Classes;


import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import scu.csci187.fall2018.mealtracker.R;

public class ShoppingListAdapter extends ArrayAdapter<String> {

    private Context mContext;
    private ArrayList<String> meals;
    private UpdateShoppingList mCallback;

    private TextView tvMealName;
    private LinearLayout ingredientsContainer;
    private ArrayList<ArrayList<CheckBox>> data;

    public ShoppingListAdapter(Context context, ArrayList<String> meals) {
        super(context, R.layout.shopping_item, meals);
        this.mContext = context;
        this.meals = meals;
        this.data = new ArrayList<>();
    }

    public void setUpdateShoppingListListener(UpdateShoppingList listener) {
        this.mCallback = listener;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String mealname = meals.get(position);

        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.shopping_item, parent, false);

        }

        tvMealName = convertView.findViewById(R.id.shoppingMealName);
        ingredientsContainer = convertView.findViewById(R.id.shoppingItemIngredients);

        tvMealName.setText(mealname);

        Toast toast = Toast.makeText(getContext(), "Size of Meals " + meals.size(), Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.TOP, 0, 0);
        toast.show();

        for(String mealName : meals) {
            ArrayList<CheckBox> boxes = new ArrayList<>();
            ArrayList<String> ingredients = new ArrayList<>();

            /*
                TODO: lookup ingredients list, store in ArrayList ingredients
            */

            ingredients.add("1/2 oz chicken");
            ingredients.add("5 lbs salt");
            ingredients.add("1 bay leaf");
            ingredients.add("23 ibuprofen");
            ingredients.add("6 rabbit's foot");
            ingredients.add("1/2 can Red Bull");
            ingredients.add("2 spinach leaves");

            for(String item : ingredients) {
                final String itemName = item;

                CheckBox cb = new CheckBox(getContext());
                int y = View.generateViewId();
                cb.setText(Integer.toString(y));
                cb.setId(y);
                cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        ArrayList<Integer> indicesOfFullyCheckedMeals = getFullyCheckedMeals();
                        if(indicesOfFullyCheckedMeals.size() > 0) {
                            Toast toast = Toast.makeText(getContext(), "Remove Indices " + indicesOfFullyCheckedMeals.size(), Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.TOP, 0, 0);
                            toast.show();
                            removeCheckboxesForMeals(indicesOfFullyCheckedMeals);
                            mCallback.removeMealFromShoppingList(indicesOfFullyCheckedMeals);
                        }
                    }
                });
                boxes.add(cb);
                ingredientsContainer.addView(cb);
            }
            data.add(boxes);
        }


        return convertView;
    }

    private ArrayList<Integer> getFullyCheckedMeals() {
        ArrayList<Integer> result = new ArrayList<>();

        for(ArrayList<CheckBox> cbList : data) {
            if(allIngredientsMarked(cbList))
                result.add(data.indexOf(cbList));
        }
        return result;
    }

    private boolean allIngredientsMarked(ArrayList<CheckBox> cbList) {
        for(CheckBox cb : cbList) {
            if(!cb.isChecked())
                return false;
        }
        return true;
    }

    private void removeCheckboxesForMeals(ArrayList<Integer> indices) {
        ArrayList<CheckBox> boxes;


        for(Integer x : indices) {
            boxes = data.get(x.intValue());

            for(CheckBox cb : boxes) {
                Toast toast1 = Toast.makeText(getContext(), "Remove CB " + cb.getId(), Toast.LENGTH_SHORT);
                toast1.setGravity(Gravity.CENTER, 0, 0);
                toast1.show();
                int id = cb.getId();
                View temp = ingredientsContainer.findViewById(id);
                ingredientsContainer.removeView(temp);
            }
        }
    }

    public interface UpdateShoppingList {
        void removeMealFromShoppingList(ArrayList<Integer> indices);
    }
}
