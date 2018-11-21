package scu.csci187.fall2018.mealtracker.Classes;

/*
import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import scu.csci187.fall2018.mealtracker.R;

public class ShoppingListAdapter extends ArrayAdapter<String> { //implements View.OnClickListener {

    private ArrayList<String> data;
    Context mContext;
    TextView shoppingMealName;
    ListView shoppingIngredientsList;


    public ShoppingListAdapter(@NonNull Context context, ArrayList<String> list) {
        super(context, 0, list);
        this.mContext = context;
        this.data = list;
    }

    static class ViewHolder {
        public TextView tvMealName;
        public ListView lvIngredientsList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.shopping_item, parent, false);
            holder.tvMealName = convertView.findViewById(R.id.shoppingMealName);
            holder.lvIngredientsList = convertView.findViewById(R.id.shoppingMealIngredientsList);
            convertView.setTag(holder);
        }
        else
            holder = (ViewHolder) convertView.getTag();

        holder.tvMealName.setText(data.get(position));


        ArrayList<String> ingredients = new ArrayList<>();
            /*
                TODO: lookup ingredients list, store in arraylist
             *
            ingredients.add("1/2 oz chicken");
            ingredients.add("5 lbs salt");
            ingredients.add("1 bay leaf");
            ingredients.add("23 ibuprofen");
            ingredients.add("6 rabbit's foot");
            ingredients.add("1/2 can Red Bull");
            ingredients.add("2 spinach leaves");
            ArrayAdapter<String> ingredientsAdapter = new ArrayAdapter(mContext, 0, ingredients);
            holder.lvIngredientsList.setAdapter(ingredientsAdapter);


        return convertView;
    }
}
*/

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import scu.csci187.fall2018.mealtracker.R;

public class ShoppingListAdapter extends ArrayAdapter<String> {

    private Context mContext;
    private ArrayList<String> meals;
    private TextView tvMealName, shoppingText;
    private ListView lvIngredients;

    public ShoppingListAdapter(Context context, ArrayList<String> meals) {
        super(context, R.layout.shopping_item, meals);
        this.mContext = context;
        this.meals = meals;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String mealname = meals.get(position);

        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.shopping_item, parent, false);
        }
        tvMealName = convertView.findViewById(R.id.shoppingMealName);
      //  lvIngredients = convertView.findViewById(R.id.shoppingMealIngredientsList);
        shoppingText = convertView.findViewById(R.id.shoppingText);

        tvMealName.setText(mealname);
        /*
                TODO: lookup ingredients list, store in arraylist
        */
        ArrayList<String> ingredients = new ArrayList<>();
        ingredients.add("1/2 oz chicken");
        ingredients.add("5 lbs salt");
        ingredients.add("1 bay leaf");
        ingredients.add("23 ibuprofen");
        ingredients.add("6 rabbit's foot");
        ingredients.add("1/2 can Red Bull");
        ingredients.add("2 spinach leaves");

        String ingredientsList = "";
        for(String x : ingredients) {
            ingredientsList += x + System.getProperty("line.separator");
        }
        shoppingText.setText(ingredientsList);

        return convertView;
    }

}
